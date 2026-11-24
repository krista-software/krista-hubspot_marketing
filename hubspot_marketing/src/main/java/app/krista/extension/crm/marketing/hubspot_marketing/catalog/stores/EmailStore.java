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

import app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Email;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.storeimpl.EmailStoreImpl;
import app.krista.extension.crm.marketing.hubspot_marketing.util.ErrorMessages;
import app.krista.extension.crm.marketing.hubspot_marketing.util.StringValidator;
import app.krista.extension.executor.SearchCondition;
import app.krista.extension.executor.SearchQuery;
import app.krista.extension.util.EntityStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class EmailStore implements EntityStore<Email> {

    private static final Logger LOG = LogManager.getLogger(EmailStore.class.getName());
    private final EmailStoreImpl emailStoreImpl;

    @Inject
    public EmailStore(EmailStoreImpl emailStore) {
        this.emailStoreImpl = emailStore;
    }

    @Override
    public Email create(Map<String, Object> map) throws IOException {
        try {
            return emailStoreImpl.createEmail(map);
        } catch (JSONException cause) {
            throw new IllegalStateException(ErrorMessages.errorCreatingEmail(cause.getMessage()));
        }
    }

    @Override
    public Email get(String id) throws IOException {
        StringValidator.isNulOrEmptyThrowException(id, "Id");
        try {
            return emailStoreImpl.getEmailById(id);
        } catch (JSONException cause) {
            throw new IllegalStateException(ErrorMessages.errorGettingEmailById(id, cause.getMessage()));
        }

    }

    @Override
    public Email update(Email email) {
        LOG.info(ErrorMessages.UNSUPPORTED_METHOD);
        return new Email();
    }

    @Override
    public void delete(String id) {
        try {
            emailStoreImpl.deleteEmail(id);
        } catch (JSONException cause) {
            throw new IllegalStateException(ErrorMessages.errorDeletingEmail(id, cause.getMessage()));
        }
    }

    @Override
    public boolean contains(String id) {
        LOG.info(ErrorMessages.UNSUPPORTED_METHOD);
        return false;
    }

    @Override
    public List<Email> search(List<SearchCondition> list, long l, int i) throws IOException {
        return Collections.emptyList();
    }

    @Override
    public List<Email> search(SearchQuery searchQuery, long pageIndex, int pageSize) throws IOException {
        pageIndex = pageIndex - 1;
        try {
            return emailStoreImpl.getEmails(searchQuery, pageIndex, pageSize);
        } catch (JSONException cause) {
            throw new IllegalStateException("Error occurred while searching emails " + cause);
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
