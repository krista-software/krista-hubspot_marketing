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

package app.krista.extension.crm.marketing.hubspot_marketing.stores;

import app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Subscription;
import app.krista.extension.crm.marketing.hubspot_marketing.catalog.stores.SubscriptionStore;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.connector.RestClientProvider;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.storeimpl.SubscriptionStoreImpl;
import app.krista.extension.executor.SearchQuery;
import org.codehaus.jettison.json.JSONException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SubscriptionStoreTest {


    // Can retrieve a subscription by ID
    @Test
    public void test_retrieve_subscription_by_id() throws IOException, JSONException {

        String id = "123";
        Subscription expectedSubscription = new Subscription(id, "Test Subscription", "Test Description", "Test Purpose", "2022-01-01");
        SubscriptionStoreImpl subcriptionStoreMock = mock(SubscriptionStoreImpl.class);
        when(subcriptionStoreMock.getSubscriptionById(id)).thenReturn(expectedSubscription);
        RestClientProvider restClientMock = mock(RestClientProvider.class);
        SubscriptionStore subscriptionStore = new SubscriptionStore(subcriptionStoreMock);

        Subscription actualSubscription = subscriptionStore.get(id);

        assertEquals(expectedSubscription, actualSubscription);
    }

    @Test
    public void test_retrieve_all_subscriptions() throws IOException, JSONException {
        SearchQuery mockSearchQuery = mock(SearchQuery.class);
        List<Subscription> expectedSubscriptions = new ArrayList<>();
        expectedSubscriptions.add(new Subscription("1", "Subscription 1", "Description 1", "Purpose 1", "2022-01-01"));
        expectedSubscriptions.add(new Subscription("2", "Subscription 2", "Description 2", "Purpose 2", "2022-01-02"));
        SubscriptionStoreImpl subcriptionStoreMock = mock(SubscriptionStoreImpl.class);
        when(subcriptionStoreMock.getAllSubscription(any(SearchQuery.class), anyLong(), anyInt())).thenReturn(expectedSubscriptions);
        RestClientProvider restClientMock = mock(RestClientProvider.class);
        SubscriptionStore subscriptionStore = new SubscriptionStore(subcriptionStoreMock);

        List<Subscription> actualSubscriptions = subscriptionStore.search(mockSearchQuery, 1, 10);

        assertEquals(expectedSubscriptions, actualSubscriptions);
    }

    @Test
    public void test_json_parsing_errors() throws JSONException {
        SearchQuery mockSearchQuery = mock(SearchQuery.class);
        SubscriptionStoreImpl subcriptionStoreMock = mock(SubscriptionStoreImpl.class);
        when(subcriptionStoreMock.getAllSubscription(any(SearchQuery.class), anyLong(), anyInt())).thenThrow(new RuntimeException());
        RestClientProvider restClientMock = mock(RestClientProvider.class);
        SubscriptionStore subscriptionStore = new SubscriptionStore(subcriptionStoreMock);
        assertThrows(RuntimeException.class, () -> subscriptionStore.search(mockSearchQuery, 1, 10));
    }

}