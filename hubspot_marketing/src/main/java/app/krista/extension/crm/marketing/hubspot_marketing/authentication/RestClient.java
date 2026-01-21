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

import app.krista.extension.crm.marketing.hubspot_marketing.util.Constants;
import app.krista.extension.crm.marketing.hubspot_marketing.util.ErrorMessages;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.logging.Logger;

public class RestClient {
    private static final Logger logger = Logger.getLogger(RestClient.class.getName());
    private final Client client;
    private final String accessToken;

    public RestClient(String accessToken) {
        this.accessToken = accessToken;
        client = ClientBuilder.newClient();
    }

    /**
     * Handles the response from an HTTP request using JAX-RS Response.
     *
     * @param response The JAX-RS Response object from the HTTP request.
     * @return A JSONObject parsed from the response body.
     * @throws JSONException If there are issues parsing the JSON response.
     */

    public static JSONObject handleResponse(Response response) throws JSONException {
        String responseString = response.readEntity(String.class);

        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            return new JSONObject(responseString);
        } else {
            handleUnsuccessfulCases(response, responseString);
        }
        return null;
    }

    private static void handleUnsuccessfulCases(Response response, String responseString) {
        int statusCode = response.getStatus();
        if (statusCode == Response.Status.UNAUTHORIZED.getStatusCode()) {
            logger.warning(ErrorMessages.UNAUTHORIZED_ACCESS);
        } else if (statusCode == Response.Status.NOT_FOUND.getStatusCode()) {
            logger.warning(ErrorMessages.RESOURCE_NOT_FOUND);
        } else {
            logger.warning(ErrorMessages.apiRequestFailed(responseString));
        }
    }

    private static WebTarget addQParamsToWebTarget(Map<String, Object> queryParams, WebTarget target) {
        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            target = target.queryParam(entry.getKey(), entry.getValue());
        }
        return target;
    }

    /**
     * Performs an HTTP GET request and returns the response as a JSONObject.
     *
     * @param url         The URL to perform the GET request.
     * @param queryParams A map of query parameters to include in the request.
     * @return A JSONObject representing the response from the GET request.
     * @throws JSONException If there are issues parsing the JSON response.
     */

    public JSONObject get(String url, Map<String, Object> queryParams) throws JSONException {
        return get(url, queryParams, true);
    }

    private Invocation.Builder buildRequest(String httpMethod, String endPoint, Map<String, Object> queryParams,
                                            boolean isBearerTokenNeeded) {
        if (httpMethod.equals(HttpMethod.PATCH) || httpMethod.equals(HttpMethod.POST)) {
            client.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
        }
        WebTarget target = client.target(Constants.BASE_URL).path(endPoint);
        if (httpMethod.equals(HttpMethod.GET)) {
            target = addQParamsToWebTarget(queryParams, target);
        }
        Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);
        if (isBearerTokenNeeded) {
            builder.header(Constants.AUTHORIZATION, Constants.BEARER + accessToken);
        }
        builder.header(Constants.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        return builder;
    }

    public JSONObject get(String url, Map<String, Object> queryParams, boolean isBearerTokenNeeded) throws JSONException {
        Invocation.Builder request = buildRequest(HttpMethod.GET, url, queryParams, isBearerTokenNeeded);
        return handleResponse(request.get());
    }

    /**
     * Performs an HTTP DELETE request and returns the response as a Response object.
     *
     * @param url The URL to perform the DELETE request.
     * @return A Response object representing the response from the DELETE request.
     */
    public Response delete(String url) {
        Invocation.Builder builder = buildRequest(HttpMethod.DELETE, url, null, true);
        return builder.delete();
    }

    /**
     * Performs an HTTP POST request and returns the response as a JSONObject.
     *
     * @param url  The URL to perform the POST request.
     * @param body The request body for the POST request.
     * @return A JSONObject representing the response from the POST request.
     * @throws JSONException If there are issues parsing the JSON response.
     */

    public JSONObject post(String url, String body) throws JSONException {
        Invocation.Builder request = buildRequest(HttpMethod.POST, url, null, true);
        return handleResponse(request.post(Entity.entity(body, MediaType.APPLICATION_JSON)));
    }

}