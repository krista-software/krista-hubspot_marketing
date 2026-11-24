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

package app.krista.extension.crm.marketing.hubspot_marketing.impl.stores;

import app.krista.extension.crm.marketing.hubspot_marketing.HubspotAttributes;
import app.krista.extension.executor.Invoker;
import app.krista.extension.request.RoutingInfo;
import app.krista.extension.request.protos.http.HttpProtocol;
import app.krista.extensions.util.KeyValueStore;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Service
public class HubspotAttributesStore {

    private final KeyValueStore store;
    private final Invoker invoker;

    @Inject
    public HubspotAttributesStore(KeyValueStore store, Invoker invoker) {
        this.store = store;
        this.invoker = invoker;
    }

    /**
     * Loads HubspotAttributes from the store based on the given authentication context ID.
     *
     * @param authContextId The authentication context ID to retrieve attributes for.
     * @return A HubspotAttributes object containing configuration details.
     */
    public HubspotAttributes load(String authContextId) {
        String jsonString = String.valueOf(store.get(authContextId));
        Map<String, Object> attributes = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Iterator<?> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                Object value = jsonObject.get(key);
                attributes.put(key, value);
            }
        } catch (JSONException cause) {
            throw new IllegalStateException(cause.getMessage());
        }
        return new HubspotAttributes((String) attributes.get(HubspotAttributes.CLIENT_ID),
                (String) attributes.get(HubspotAttributes.CLIENT_SECRET), (String) attributes.get(HubspotAttributes.EMAIL),
                invoker.getRoutingInfo().getRoutingURL(HttpProtocol.PROTOCOL_NAME, RoutingInfo.Type.APPLIANCE));
    }

    /**
     * Saves HubspotAttributes to the store and returns the associated authentication context ID.
     *
     * @param hubspotAttributes The HubspotAttributes to save.
     * @return The authentication context ID associated with the saved attributes.
     */
    public String save(HubspotAttributes hubspotAttributes) {
        String authContextId = UUID.randomUUID().toString();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(HubspotAttributes.CLIENT_ID, hubspotAttributes.getClientId());
            jsonObject.put(HubspotAttributes.CLIENT_SECRET, hubspotAttributes.getClientSecret());
            jsonObject.put(HubspotAttributes.EMAIL, hubspotAttributes.getEmailId());
            store.put(authContextId, jsonObject.toString());
        } catch (JSONException cause) {
            throw new IllegalStateException(cause.getMessage());
        }
        return authContextId;
    }

    /**
     * Removes given authContext id from KeyValueStore
     *
     * @param authContextId authContext id
     */
    public void remove(String authContextId) {
        store.remove(authContextId);
    }

}
