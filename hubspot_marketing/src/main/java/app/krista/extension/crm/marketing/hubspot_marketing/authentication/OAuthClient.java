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

package app.krista.extension.crm.marketing.hubspot_marketing.authentication;


import app.krista.extension.crm.marketing.hubspot_marketing.HubspotAttributes;
import app.krista.extension.crm.marketing.hubspot_marketing.util.Constants;
import org.codehaus.jettison.json.JSONException;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

import static com.github.scribejava.core.model.OAuthConstants.*;

public class OAuthClient {

    private final HubspotAttributes hubspotAttributes;

    public OAuthClient(HubspotAttributes hubspotAttributes) {
        this.hubspotAttributes = hubspotAttributes;
    }

    /**
     * Generates the OAuth authorization URL for HubSpot integration.
     *
     * @param state A unique state identifier for security.
     * @return The OAuth authorization URL.
     */
    public String getOAuthUrl(String state) {
        WebTarget webTarget = ClientBuilder.newClient()
                .register(HttpAuthenticationFeature.basic(hubspotAttributes.getClientId(), hubspotAttributes.getClientSecret()))
                .target(Constants.OAUTH_URL);
        WebTarget authorize = webTarget.path("authorize");
        authorize = authorize.queryParam(CLIENT_ID, hubspotAttributes.getClientId());
        authorize = authorize.queryParam(Constants.REDIRECT_URI, hubspotAttributes.getRedirectUrl());
        authorize = authorize.queryParam(SCOPE, Constants.SCOPE);
        authorize = authorize.queryParam(STATE, state);
        return authorize.getUri().toString();
    }

    /**
     * Retrieves an OAuth access token using an authorization code.
     *
     * @param token The authorization code obtained from the initial OAuth request.
     * @return An AccessToken object representing the obtained access token.
     * @throws JSONException If there are issues processing the JSON response.
     */

    public AccessToken getOAuthAccessToken(String token, String grantType) throws JSONException {
        String code = grantType.equalsIgnoreCase(AUTHORIZATION_CODE) ? CODE : REFRESH_TOKEN;
        return getAccessToken(new Form()
                .param(GRANT_TYPE, grantType)
                .param(code, token)
                .param(CLIENT_ID, hubspotAttributes.getClientId())
                .param(CLIENT_SECRET, hubspotAttributes.getClientSecret())
                .param(REDIRECT_URI, hubspotAttributes.getRedirectUrl()));
    }

    /**
     * Internal method to obtain an OAuth access token using a specified request body.
     *
     * @param body The request body containing grant type and client information.
     * @return An AccessToken object representing the obtained access token.
     * @throws JSONException If there are issues processing the JSON response.
     */

    private AccessToken getAccessToken(Form body) throws JSONException {
        WebTarget webTarget = ClientBuilder.newClient()
                .target(Constants.TOKEN_URL);
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(body, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        return AccessToken.create(Objects.requireNonNull(RestClient.handleResponse(response)));
    }


}
