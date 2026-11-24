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

import app.krista.extension.authorization.MustAuthenticateException;
import app.krista.extension.authorization.MustAuthorizeException;
import app.krista.extension.authorization.RequestAuthenticator;
import app.krista.extension.crm.marketing.hubspot_marketing.authentication.OAuthClient;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.stores.HubspotAttributesStore;
import app.krista.extension.crm.marketing.hubspot_marketing.util.Constants;
import app.krista.extension.request.ProtoRequest;
import app.krista.extension.request.ProtoResponse;
import app.krista.extension.request.protos.http.HttpRequest;
import app.krista.model.field.NamedField;
import app.krista.model.field.NamedValuedField;

import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.*;

import static com.github.scribejava.core.model.OAuthConstants.STATE;

public class HubspotRequestAuthenticator implements RequestAuthenticator {

    private final HubspotAttributesStore hubspotAttributesStore;
    private final HubspotAttributes hubspotAttributes;


    public HubspotRequestAuthenticator(HubspotAttributesStore hubspotAttributesStore, HubspotAttributes hubspotAttributes) {
        this.hubspotAttributesStore = hubspotAttributesStore;
        this.hubspotAttributes = hubspotAttributes;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public Set<String> getSupportedProtocols() {
        return Collections.emptySet();
    }

    @Override
    public String getAuthenticatedAccountId(ProtoRequest protoRequest) {
        try {
            HttpRequest httpRequest = (HttpRequest) protoRequest;
            if ("/callback".equals(httpRequest.getUri().getPath())) {
                httpRequest.bufferBody();
                MultivaluedMap<String, String> queryParameters = httpRequest.getQueryParameters();
                if (queryParameters == null || queryParameters.get(STATE) == null || queryParameters.get(STATE).getFirst()
                        == null) {
                    return null;
                }
                String state = String.valueOf(queryParameters.get(STATE).getFirst());
                String[] parts = state.split(Constants.HASH);
                return parts[0];
            } else {
                return null;
            }
        } catch (RuntimeException | IOException cause) {
            return null;
        }
    }

    @Override
    public boolean setServiceAuthorization(String s) {
        return false;
    }

    @Override
    public Map<String, NamedField> getAttributeFields() {
        return Collections.emptyMap();
    }

    @Override
    public ProtoResponse getMustAuthenticateResponse(MustAuthenticateException e, ProtoRequest protoRequest) {
        return null;
    }

    @Override
    public AuthorizationResponse getMustAuthenticateResponse(MustAuthenticateException e) {
        return null;
    }

    @Override
    public ProtoResponse getMustAuthorizeResponse(MustAuthorizeException e, ProtoRequest protoRequest) {
        return null;
    }

    @Override
    public AuthorizationResponse getMustAuthorizeResponse(MustAuthorizeException cause) {
        String userId = (String) cause.getDetails().getFirst().getValue();
        Optional<NamedValuedField> authContextIdField = cause.getDetails().stream().filter(f -> Objects.equals(f.getName(),
                "authContextId")).findFirst();
        HubspotAttributes effectiveHubspotAttributes;
        String state;
        if (authContextIdField.isPresent()) {
            String authContextId = (String) authContextIdField.get().getValue();
            effectiveHubspotAttributes = hubspotAttributesStore.load(authContextId);
            state = userId + "#" + authContextId;
        } else {
            effectiveHubspotAttributes = hubspotAttributes;
            state = userId;
        }
        OAuthClient oAuthClient = new OAuthClient(effectiveHubspotAttributes);
        return new AuthorizationResponse(oAuthClient.getOAuthUrl(state), List.of());
    }
}
