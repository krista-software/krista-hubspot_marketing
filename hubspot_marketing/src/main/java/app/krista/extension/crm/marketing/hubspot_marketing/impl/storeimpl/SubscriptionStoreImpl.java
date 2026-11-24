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

package app.krista.extension.crm.marketing.hubspot_marketing.impl.storeimpl;

import app.krista.extension.crm.marketing.hubspot_marketing.impl.connector.RestClientProvider;
import app.krista.extension.crm.marketing.hubspot_marketing.service.Subscription;
import app.krista.extension.crm.marketing.hubspot_marketing.util.Constants;
import app.krista.extension.executor.SearchCondition;
import app.krista.extension.executor.SearchQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SubscriptionStoreImpl implements Subscription {

    private static final Logger LOG = LogManager.getLogger(SubscriptionStoreImpl.class.getName());
    private final RestClientProvider restClient;


    @Inject
    public SubscriptionStoreImpl(RestClientProvider restClient) {
        this.restClient = restClient;
    }


    @Override
    public List<app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Subscription> getAllSubscription(SearchQuery searchQuery, long pageIndex, int pageSize) {
        if (shouldUseSimpleSearch(searchQuery)) {
            JSONObject subscriptionJson;
            try {
                subscriptionJson = restClient.getRestClient().get(Constants.SUBSCRIPTION_URL, new HashMap<>());
                return jsonArrayToSubscription(subscriptionJson);
            } catch (JSONException cause) {
                LOG.error("Error occurred while retrieving all subscriptions: {}", cause.getMessage());
                throw new RuntimeException("Failed to retrieve subscriptions", cause);
            }
        }
        throw new IllegalArgumentException("Error occurred while retrieving all subscription");
    }

    @Override
    public app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Subscription getSubscriptionById(String id) {
        if (id == null || id.trim().isEmpty()) {
            LOG.warn("Subscription ID is null or empty");
            return new app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Subscription();
        }

        List<app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Subscription> subscriptions;
        try {
            subscriptions = jsonArrayToSubscription(restClient.getRestClient().get(Constants.SUBSCRIPTION_URL, new HashMap<>()));
        } catch (JSONException cause) {
            LOG.error("Error occurred while retrieving subscription by ID {}: {}", id, cause.getMessage());
            throw new RuntimeException("Failed to retrieve subscription by ID: " + id, cause);
        }

        for (app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Subscription subscription : subscriptions) {
            if (subscription.id != null && subscription.id.equals(id)) {
                return subscription;
            }
        }
        LOG.info("Subscription with ID {} not found", id);
        return new app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Subscription();
    }


    public List<app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Subscription> jsonArrayToSubscription(JSONObject jsonObject) {
        List<app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Subscription> subscriptions = new ArrayList<>();
        try {
            JSONArray subscriptionDefinitions = jsonObject.getJSONArray(Constants.SUBSCRIPTION_DEFINITIONS);
            for (int i = 0; i < subscriptionDefinitions.length(); i++) {
                JSONObject subscriptionJson = subscriptionDefinitions.getJSONObject(i);
                app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Subscription subscription = fromJsonToSubscription(subscriptionJson);
                subscriptions.add(subscription);
            }
        } catch (Exception cause) {
            LOG.error("Error occurred while converting JSON Array response to Subscriptions: {}", cause.getMessage());
        }
        return subscriptions;
    }

    private app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Subscription fromJsonToSubscription(JSONObject subscriptionJson) throws JSONException {
        app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Subscription subscription = new app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Subscription();
        subscription.id = subscriptionJson.has(Constants.ID) ? subscriptionJson.getString(Constants.ID) : null;
        subscription.name = subscriptionJson.has(Constants.NAME) ? subscriptionJson.getString(Constants.NAME) : null;
        subscription.purpose = subscriptionJson.has(Constants.PURPOSE) ? subscriptionJson.getString(Constants.PURPOSE) : null;
        subscription.description = subscriptionJson.has(Constants.DESCRIPTION) ? subscriptionJson.getString(Constants.DESCRIPTION) : null;
        subscription.createdAt = subscriptionJson.has(Constants.CREATE_AT) ? subscriptionJson.getString(Constants.CREATE_AT) : null;
        return subscription;
    }

    private boolean shouldUseSimpleSearch(SearchQuery searchQuery) {
        List<SearchCondition> searchConditions = searchQuery.getSearchConditions();
        return searchConditions == null || searchConditions.size() < 2;
    }

}
