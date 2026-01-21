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

package app.krista.extension.crm.marketing.hubspot_marketing.catalog.stores;

import app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Subscription;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.storeimpl.SubscriptionStoreImpl;
import app.krista.extension.crm.marketing.hubspot_marketing.util.ErrorMessages;
import app.krista.extension.crm.marketing.hubspot_marketing.util.StringValidator;
import app.krista.extension.executor.SearchCondition;
import app.krista.extension.executor.SearchQuery;
import app.krista.extension.util.EntityStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class SubscriptionStore implements EntityStore<Subscription> {

    private static final Logger LOG = LogManager.getLogger(SubscriptionStore.class.getName());

    private final SubscriptionStoreImpl subcriptionStore;

    @Inject
    public SubscriptionStore(SubscriptionStoreImpl subcriptionStore1) {
        this.subcriptionStore = subcriptionStore1;
    }

    @Override
    public Subscription create(Map<String, Object> map) throws IOException {
        LOG.info(ErrorMessages.UNSUPPORTED_METHOD);
        return new Subscription();
    }

    @Override
    public Subscription get(String id) throws IOException {
        StringValidator.isNulOrEmptyThrowException(id, "Id");
        try {
            return subcriptionStore.getSubscriptionById(id);
        } catch (Exception cause) {
            throw new IllegalStateException(ErrorMessages.errorGettingSubscriptionById(id, cause.getMessage()), cause);
        }
    }

    @Override
    public Subscription update(Subscription subscription) {
        LOG.info(ErrorMessages.UNSUPPORTED_METHOD);
        return new Subscription();
    }

    @Override
    public void delete(String s) {
        LOG.info(ErrorMessages.UNSUPPORTED_METHOD);
    }

    @Override
    public boolean contains(String s) {
        return false;
    }

    @Override
    public List<Subscription> search(List<SearchCondition> list, long l, int i) throws IOException {
        return Collections.emptyList();
    }

    @Override
    public List<Subscription> search(SearchQuery searchQuery, long pageIndex, int pageSize) throws IOException {
        pageIndex = pageIndex - 1;
        try {
            return subcriptionStore.getAllSubscription(searchQuery, pageIndex, pageSize);
        } catch (Exception cause) {
            LOG.error("Error occurred while searching subscriptions :{} {}", cause.getMessage(), cause);
            throw new RuntimeException("Error occurred while searching subscriptions " + cause);
        }
    }

    @Override
    public long count(List<SearchCondition> list) {
        return 0;
    }

    @Override
    public List<String> lookup(Map<String, Object> map, List<String> list) {
        return Collections.emptyList();
    }

}
