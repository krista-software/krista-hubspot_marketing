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

import app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Template;
import app.krista.extension.crm.marketing.hubspot_marketing.catalog.stores.TemplateStore;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.storeimpl.TemplateStoreImpl;
import app.krista.extension.executor.SearchQuery;
import org.codehaus.jettison.json.JSONException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TemplateStoreTest {

    TemplateStoreImpl templateStoreImpl = mock(TemplateStoreImpl.class);

    TemplateStore templateStore = mock(TemplateStore.class);

    SearchQuery mockSearchQuery = mock(SearchQuery.class);


    @Test
    public void test_retrieve_all_templates_with_simple_search() throws IOException, JSONException {
        long pageIndex = 1;
        int pageSize = 10;
        List<Template> expectedTemplates = new ArrayList<>();
        when(templateStoreImpl.getAllTemplates(mockSearchQuery, pageIndex, pageSize)).thenReturn(expectedTemplates);

        List<Template> actualTemplates = templateStore.search(mockSearchQuery, pageIndex, pageSize);

        assertEquals(expectedTemplates, actualTemplates);
    }


}