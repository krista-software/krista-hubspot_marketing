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

package app.krista.extension.crm.marketing.hubspot_marketing.api;


import app.krista.extension.crm.marketing.hubspot_marketing.HubspotAttributes;
import app.krista.extension.crm.marketing.hubspot_marketing.authentication.AccessToken;
import app.krista.extension.crm.marketing.hubspot_marketing.authentication.OAuthClient;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.connector.RestClientProvider;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.connector.RestClientProviderFactory;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.stores.HubspotAttributesStore;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.stores.RefreshTokenStore;
import app.krista.extension.crm.marketing.hubspot_marketing.util.Constants;
import app.krista.extension.crm.marketing.hubspot_marketing.util.ErrorMessages;
import app.krista.ksdk.authentication.AuthorizationListener;
import app.krista.ksdk.context.AuthorizationContext;
import app.krista.ksdk.context.RequestContext;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.Collections;
import java.util.Objects;

import static com.github.scribejava.core.model.OAuthConstants.*;

@Path("/")
public final class HubspotResource {
    private final RestClientProviderFactory providerFactory;
    private final HubspotAttributesStore hubspotAttributesStore;
    private final RefreshTokenStore refreshTokenStore;
    private final AuthorizationContext context;
    private final AuthorizationListener authorizationListener;
    private final RequestContext requestContext;
    private final HubspotAttributes hubspotAttributes;

    @Inject
    public HubspotResource(RestClientProviderFactory providerFactory, HubspotAttributesStore hubspotAttributesStore,
                           RefreshTokenStore refreshTokenStore, AuthorizationContext context, AuthorizationListener
                                   authorizationListener, RequestContext requestContext, HubspotAttributes hubspotAttributes) {
        this.providerFactory = providerFactory;
        this.hubspotAttributesStore = hubspotAttributesStore;
        this.refreshTokenStore = refreshTokenStore;
        this.context = context;
        this.authorizationListener = authorizationListener;
        this.requestContext = requestContext;
        this.hubspotAttributes = hubspotAttributes;
    }

    private static boolean isValidState(String[] parts) {
        return !(parts[0].isBlank() || parts.length > 3);
    }

    @GET
    @Path("callback")
    public String getCallBack(@QueryParam(CODE) String code, @QueryParam(STATE) String state) throws JSONException {
        Objects.requireNonNull(code);
        String[] parts = state.split(Constants.HASH);
        validateState(parts);

        String key = parts[0] + Constants.HASH + parts[1];
        String authContextId = parts.length == 3 ? parts[2] : null;

        AccessToken authTokenResponse = extractAccessToken(code, authContextId);
        String refreshTokenOwnerEmail = null;

        if (!requestContext.invokeAsUser()) {
            refreshTokenOwnerEmail = validateClientAndOwnerEmail(authTokenResponse.refreshToken);
        }

        refreshTokenStore.put(key, authTokenResponse.refreshToken);

        if (!isAuthorizedUser(parts, key, refreshTokenOwnerEmail)) {
            refreshTokenStore.remove(key);
            return createUnauthorizedMessage(parts[0], refreshTokenOwnerEmail);
        }

        return createSuccessMessage();
    }

    private void validateState(String[] parts) {
        if (!isValidState(parts)) {
            throw new BadRequestException(ErrorMessages.INVALID_STATE_PARAMETERS);
        }
    }

    private boolean isAuthorizedUser(String[] parts, String key, String refreshTokenOwnerEmail) {
        return key.startsWith("wsContact") || parts[0].equalsIgnoreCase(refreshTokenOwnerEmail);
    }


    private String createUnauthorizedMessage(String configuredEmail, String authenticatedEmail) {
        return ErrorMessages.unauthorizedUser(configuredEmail, authenticatedEmail);
    }

    private AccessToken extractAccessToken(String code, String authContextId) throws JSONException {
        OAuthClient oAuthClient;
        if (authContextId == null) {
            oAuthClient = new OAuthClient(hubspotAttributes);
        } else {
            HubspotAttributes effectiveBitbucketAttributes = hubspotAttributesStore.load(authContextId);
            oAuthClient = new OAuthClient(effectiveBitbucketAttributes);
        }
        return oAuthClient.getOAuthAccessToken(code, AUTHORIZATION_CODE);
    }

    private String createSuccessMessage() {
        if (context.isAuthenticated()) {
            authorizationListener.authorized();
            return "User authenticated successfully.";
        }
        return "User authenticated successfully. Save the changes.";
    }


    private String validateClientAndOwnerEmail(String refreshToken) {
        try {
            RestClientProvider restClientProvider = providerFactory.create();
            JSONObject jsonObject = restClientProvider.getDefaultRestClientService().get(Constants.REFRESH_TOKEN_OWNER_URL + refreshToken, Collections.emptyMap(), false);
            return jsonObject.has(Constants.USER) ? jsonObject.getString(Constants.USER) : "";
        } catch (JSONException cause) {
            throw new IllegalStateException(ErrorMessages.errorValidatingRefreshToken(cause.getMessage()), cause);
        }
    }

}
