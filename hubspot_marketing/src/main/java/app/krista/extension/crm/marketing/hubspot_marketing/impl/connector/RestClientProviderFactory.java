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


import app.krista.extension.crm.marketing.hubspot_marketing.HubspotAttributes;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.stores.HubspotAttributesStore;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.stores.RefreshTokenStore;
import app.krista.ksdk.context.AuthorizationContext;
import app.krista.ksdk.context.RequestContext;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.io.IOException;

@Service
public class RestClientProviderFactory {

    private final RefreshTokenStore refreshTokenStore;
    private final AuthorizationContext authorizationContext;
    private final RequestContext requestContext;
    private final HubspotAttributesStore hubspotAttributeStore;
    private final RestClientProvider defaultClientProvider;

    @Inject
    public RestClientProviderFactory(RefreshTokenStore refreshTokenStore, HubspotAttributesStore hubspotAttributesStore,
                                     HubspotAttributes hubspotAttributes, RequestContext requestContext,
                                     AuthorizationContext authorizationContext) {
        this(refreshTokenStore, hubspotAttributesStore, requestContext, authorizationContext,
                new RestClientProvider(hubspotAttributes, refreshTokenStore, authorizationContext, requestContext,
                        null));
    }

    public RestClientProviderFactory(RefreshTokenStore refreshTokenStore, HubspotAttributesStore hubspotAttributesStore,
                                     RequestContext requestContext, AuthorizationContext authorizationContext,
                                     RestClientProvider defaultClientProvider) {
        this.refreshTokenStore = refreshTokenStore;
        this.hubspotAttributeStore = hubspotAttributesStore;
        this.requestContext = requestContext;
        this.authorizationContext = authorizationContext;
        this.defaultClientProvider = defaultClientProvider;
    }

    /**
     * Creates a RestClientProvider instance with the provided HubspotAttributes and related dependencies.
     * <p>
     * This method is used to create a RestClientProvider, which is responsible for managing REST client instances.
     *
     * @param hubspotAttributes The HubspotAttributes containing necessary configuration details.
     * @return A RestClientProvider instance initialized with the provided attributes and dependencies.
     * @throws IOException If there are issues saving the HubspotAttributes.
     */
    public RestClientProvider create(HubspotAttributes hubspotAttributes) throws IOException {
        String authContextId = hubspotAttributeStore.save(hubspotAttributes);
        return new RestClientProvider(hubspotAttributes, refreshTokenStore, authorizationContext, requestContext, authContextId);
    }

    public RestClientProvider create() {
        return defaultClientProvider;
    }
}
