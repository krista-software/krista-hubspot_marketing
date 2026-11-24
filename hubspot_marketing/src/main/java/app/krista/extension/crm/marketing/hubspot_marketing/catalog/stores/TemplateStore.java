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

import app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Template;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.storeimpl.TemplateStoreImpl;
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
public class TemplateStore implements EntityStore<Template> {

    private static final Logger LOG = LogManager.getLogger(TemplateStore.class.getName());

    private final TemplateStoreImpl templateStoreImpl;


    @Inject
    public TemplateStore(TemplateStoreImpl templateStore) {
        this.templateStoreImpl = templateStore;
    }

    @Override
    public Template create(Map<String, Object> map) throws IOException {
        LOG.info(ErrorMessages.UNSUPPORTED_METHOD);
        return new Template();
    }

    @Override
    public Template get(String id) throws IOException {
        StringValidator.isNulOrEmptyThrowException(id, "Id");
        try {
            return templateStoreImpl.getTemplateById(id);
        } catch (JSONException cause) {
            throw new IllegalStateException(ErrorMessages.errorGettingTemplateById(id, cause.getMessage()));
        }
    }

    @Override
    public Template update(Template template) {
        LOG.info(ErrorMessages.UNSUPPORTED_METHOD);
        return new Template();
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
    public List<Template> search(List<SearchCondition> list, long l, int i) throws IOException {
        return Collections.emptyList();
    }

    @Override
    public List<Template> search(SearchQuery searchQuery, long pageIndex, int pageSize) throws IOException {
        pageIndex = pageIndex - 1;
        try {
            return templateStoreImpl.getAllTemplates(searchQuery, pageIndex, pageSize);
        } catch (JSONException cause) {
            throw new IllegalStateException("Error occurred wile searching templates " + cause);
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
