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

package app.krista.extension.crm.marketing.hubspot_marketing.service;

import app.krista.extension.executor.SearchQuery;
import org.codehaus.jettison.json.JSONException;

import java.util.List;

public interface Template {

    List<app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Template> getAllTemplates(SearchQuery searchQuery, long pageIndex, int pageSize) throws JSONException;

    app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Template getTemplateById(String id) throws JSONException;

}
