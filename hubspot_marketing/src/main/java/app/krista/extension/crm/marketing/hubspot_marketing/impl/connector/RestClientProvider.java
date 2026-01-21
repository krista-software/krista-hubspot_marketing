/*
 * Hubspot Marketing Extension for Krista
 * Copyright (C) 2025 Krista Software
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>. 
 */

package app.krista.extension.crm.marketing.hubspot_marketing.impl.connector;

import app.krista.extension.authorization.MustAuthorizeException;
import app.krista.extension.crm.marketing.hubspot_marketing.HubspotAttributes;
import app.krista.extension.crm.marketing.hubspot_marketing.authentication.AccessToken;
import app.krista.extension.crm.marketing.hubspot_marketing.authentication.OAuthClient;
import app.krista.extension.crm.marketing.hubspot_marketing.authentication.RestClient;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.stores.RefreshTokenStore;
import app.krista.extension.crm.marketing.hubspot_marketing.util.Constants;
import app.krista.ksdk.context.AuthorizationContext;
import app.krista.ksdk.context.RequestContext;
import app.krista.model.field.NamedValuedField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.github.scribejava.core.model.OAuthConstants.REFRESH_TOKEN;

@Service
public class RestClientProvider {
    private static final Logger LOG = LogManager.getLogger(RestClientProvider.class.getName());
    private final OAuthClient oAuthClient;
    private final RefreshTokenStore refreshTokenStore;
    private final RequestContext requestContext;
    private final AuthorizationContext authorizationContext;
    private final String authContextId;
    private final HubspotAttributes hubspotAttributes;

    @Inject
    public RestClientProvider(HubspotAttributes hubspotAttributes, RefreshTokenStore refreshTokenStore,
                              AuthorizationContext authorizationContext, RequestContext requestContext) {
        this(hubspotAttributes, refreshTokenStore, authorizationContext, requestContext, null);
    }

    public RestClientProvider(HubspotAttributes bitbucketAttributes, RefreshTokenStore refreshTokenStore,
                              AuthorizationContext authorizationContext, RequestContext requestContext, String authContextId) {
        this.hubspotAttributes = bitbucketAttributes;
        this.oAuthClient = new OAuthClient(hubspotAttributes);
        this.refreshTokenStore = refreshTokenStore;
        this.authorizationContext = authorizationContext;
        this.requestContext = requestContext;
        this.authContextId = authContextId;
    }


    /**
     * Gets a RestClient instance for making API requests.
     *
     * @param useClientCredentials If true, use the client ID for authentication; otherwise, use the user's account ID.
     * @return A RestClient instance for making API requests.
     * @throws MustAuthorizeException If a refresh token is missing for the user.
     */
    public RestClient getRestClient(boolean useClientCredentials) {
        String userId = getUserId(useClientCredentials);
        String refreshToken = refreshTokenStore.get(userId);
        if (refreshToken == null) {
            throw createMustAuthorizationException(userId, false);
        }
        return getRestClientService(userId, refreshToken);
    }

    /**
     * Gets the user or client ID for authentication purposes.
     *
     * @param calledFromValidateAttributes Indicates if called from validateAttributes.
     * @return The user or client ID for authentication.
     * @throws IllegalStateException If the account ID or client ID is not available.
     */
    private String getUserId(boolean calledFromValidateAttributes) {
        String userId = calledFromValidateAttributes
                ? hubspotAttributes.getEmailId()
                : authorizationContext.getAuthorizedAccount().getAccountId();
        if (userId == null) {
            throw new IllegalStateException("Failed to get account");
        }
        return userId + Constants.HASH + hubspotAttributes.getClientId() + hubspotAttributes.getClientSecret();
    }

    /**
     * Creates a MustAuthorizeException with details for authorization-related errors.
     *
     * @param userId           The user or client ID associated with the error.up
     * @param reAuthentication Indicates if this is a re-authentication attempt.
     * @return A MustAuthorizeException with appropriate error details.
     */
    private MustAuthorizeException createMustAuthorizationException(String userId, boolean reAuthentication) {
        LOG.info(reAuthentication ? "Got error for Authentication. So sending for re authentication" :
                "Initial user authentication process has been requested ");
        List<NamedValuedField> details = new ArrayList<>();
        NamedValuedField userIdField = new NamedValuedField("userId", "Text", userId, new HashMap<>(),
                new HashMap<>());
        details.add(userIdField);
        if (authContextId != null) {
            NamedValuedField contextIdField = new NamedValuedField("authContextId", "Text", authContextId,
                    new HashMap<>(), new HashMap<>());
            details.add(contextIdField);
        }
        return new MustAuthorizeException(reAuthentication ? "Refresh Token Expired. Please reauthorize yourself" :
                "Authorize yourself and click 'Validate Attributes' before saving changes to proceed further.", details);
    }

    /**
     * Retrieves a RestClient instance using an access token obtained via refresh token.
     *
     * @param userId       The user or client ID.
     * @param refreshToken The refresh token for obtaining a new access token.
     * @return A RestClient instance with a valid access token.
     * @throws MustAuthorizeException If there is an issue refreshing the access token.
     */
    private RestClient getRestClientService(String userId, String refreshToken) {
        try {
            AccessToken accessToken = oAuthClient.getOAuthAccessToken(refreshToken, REFRESH_TOKEN);
            return new RestClient(accessToken.accessKeyToken);
        } catch (JSONException cause) {
            throw createMustAuthorizationException(userId, true);
        }
    }

    /**
     * Gets a RestClient instance for making API requests.
     * <p>
     * If asynchronous requests are enabled or the request context does not invoke as a user,
     * it returns a RestClient based on the client ID. Otherwise, it uses the user's account ID.
     *
     * @return A RestClient instance for making API requests.
     * @throws MustAuthorizeException If a refresh token is missing for the user.
     */
    public RestClient getRestClient() {
        return getRestClient(!requestContext.invokeAsUser());
    }


    public RestClient getDefaultRestClientService() {
        return new RestClient(null);
    }

    /**
     * Gets a RestClient instance for making API requests with admin privileges.
     *
     * @return A RestClient instance for making API requests with admin privileges.
     */
    public RestClient getRestClientForAdmin() {
        return getRestClient(true);
    }

}
