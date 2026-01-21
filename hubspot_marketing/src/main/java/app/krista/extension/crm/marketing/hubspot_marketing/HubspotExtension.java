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

package app.krista.extension.crm.marketing.hubspot_marketing;

import app.krista.extension.authorization.RequestAuthenticator;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.connector.RestClientProvider;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.connector.RestClientProviderFactory;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.stores.HubspotAttributesStore;
import app.krista.extension.crm.marketing.hubspot_marketing.util.Constants;
import app.krista.extension.crm.marketing.hubspot_marketing.util.ErrorMessages;
import app.krista.extension.executor.Invoker;
import app.krista.extension.impl.anno.*;
import app.krista.extension.request.RoutingInfo;
import app.krista.extension.request.protos.http.HttpProtocol;
import org.codehaus.jettison.json.JSONException;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Field.Text(value = Constants.CLIENT_ID)
@Field.Text(value = Constants.CLIENT_SECRET, isSecured = true)
@Field.Desc(name = Constants.EMAIL, type = "Email")
@Extension(version = "2.0.5", jaxrsId = "hsmarketing", name = "Hubspot Marketing New")
@StaticResource(path = "docs", file = "docs")
@Java(version = Java.Version.JAVA_21)
public class HubspotExtension {

    protected static final String JAXRS_ID = "hsmarketing";
    private final Invoker invoker;
    private final HubspotRequestAuthenticator requestAuthenticator;
    private final RestClientProviderFactory clientProviderFactory;
    private final HubspotAttributes hubspotAttributes;

    @Inject
    public HubspotExtension(@Named("self") Invoker invoker, HubspotAttributes hubspotAttributes, RestClientProviderFactory
            clientProviderFactory, HubspotAttributesStore hubspotAttributesStore) {
        this(invoker, hubspotAttributes, clientProviderFactory, new HubspotRequestAuthenticator(hubspotAttributesStore,
                hubspotAttributes));
    }

    public HubspotExtension(Invoker invokerObj, HubspotAttributes hubspotAttributes, RestClientProviderFactory
            clientProviderFactory, HubspotRequestAuthenticator requestAuthenticator) {
        this.hubspotAttributes = hubspotAttributes;
        this.clientProviderFactory = clientProviderFactory;
        this.requestAuthenticator = requestAuthenticator;
        this.invoker = invokerObj;
    }


    @InvokerRequest(InvokerRequest.Type.AUTHENTICATOR)
    public RequestAuthenticator getRequestAuthenticator() {
        return requestAuthenticator;
    }

    @InvokerRequest(InvokerRequest.Type.CUSTOM_TABS)
    public Map<String, String> getCustomTabs() {
        return Map.of("Documentation", "static/docs");
    }

    @InvokerRequest(InvokerRequest.Type.VALIDATE_ATTRIBUTES)
    public void validateAttributes(Map<String, Object> connectionAttributes) throws IOException {
        HubspotAttributes localAttributes = HubspotAttributes.create(connectionAttributes,
                invoker.getRoutingInfo().getRoutingURL(HttpProtocol.PROTOCOL_NAME, RoutingInfo.Type.APPLIANCE));
        if (localAttributes.getClientId() != null && !localAttributes.getClientId().isBlank()) {
            validateAndTestConnection(clientProviderFactory.create(localAttributes));
        }
    }

    @InvokerRequest(InvokerRequest.Type.TEST_CONNECTION)
    public void testConnection() throws IOException {
        validateAndTestConnection(clientProviderFactory.create(hubspotAttributes));
    }

    private void validateAndTestConnection(RestClientProvider restClientProvider) {
        try {
            restClientProvider.getRestClientForAdmin()
                    .get(Constants.EMAIL_URL, Collections.singletonMap("limit", "1"));
        } catch (JSONException cause) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_CLIENT_CREDENTIALS);
        }
    }

    @InvokerRequest(InvokerRequest.Type.INVOKER_UPDATED)
    public void attributesUpdated(Map<String, Object> oldAttributes, Map<String, Object> newAttributes) {
        hubspotAttributes.update(newAttributes);
    }


}
