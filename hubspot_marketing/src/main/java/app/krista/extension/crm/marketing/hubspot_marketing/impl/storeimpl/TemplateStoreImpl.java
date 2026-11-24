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
import app.krista.extension.crm.marketing.hubspot_marketing.service.Template;
import app.krista.extension.crm.marketing.hubspot_marketing.util.Constants;
import app.krista.extension.executor.SearchCondition;
import app.krista.extension.executor.SearchQuery;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TemplateStoreImpl implements Template {

    private static final HashMap<String, String> templateTypeMap = new HashMap<>();
    private static final HashMap<String, String> categoryTypeMap = new HashMap<>();

    static {
         categoryTypeMap.put("0","Unmapped");
         categoryTypeMap.put("1","Landing Pages");
         categoryTypeMap.put("2","Email");
         categoryTypeMap.put("3","Blog Post");
         categoryTypeMap.put("4","Site Page");
    }

    static {
        templateTypeMap.put("2", "Email template");
        templateTypeMap.put("4", "Page template");
        templateTypeMap.put("11", "Error template");
        templateTypeMap.put("12", "Subscription preferences template");
        templateTypeMap.put("13", "Backup unsubscribe page template");
        templateTypeMap.put("14", "Subscriptions update confirmation template");
        templateTypeMap.put("19", "Password prompt page template");
        templateTypeMap.put("27", "Search results template");
        templateTypeMap.put("29", "Membership login template");
        templateTypeMap.put("30", "Membership registration template");
        templateTypeMap.put("31", "Membership reset password confirmation template");
        templateTypeMap.put("32", "Membership reset password request template");
    }

    private final RestClientProvider restClient;


    @Inject
    public TemplateStoreImpl(RestClientProvider restClient) {
        this.restClient = restClient;
    }

    public static String getTemplateType(String key) {
        String templateType = templateTypeMap.get(key);
        return templateType == null || templateType.isEmpty() ? "Custom" : templateType;
    }

    public static String getCategoryType(String key) {
        return categoryTypeMap.get(key);
    }

    @Override
    public List<app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Template> getAllTemplates(SearchQuery searchQuery, long pageIndex, int pageSize) throws JSONException {
        if (shouldUseSimpleSearch(searchQuery)) {
            JSONObject jsonObject = restClient.getRestClient().get(Constants.TEMPLATE_URL, new HashMap<>());
            return fromJsonArrayToTemplate(jsonObject);
        }
        throw new IllegalArgumentException("Error occurred while retrieving all templates");
    }

    public List<app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Template> fromJsonArrayToTemplate(JSONObject jsonObject) throws JSONException {
        List<app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Template> templates = new ArrayList<>();
        JSONArray jsonArrayTemplates = jsonObject.getJSONArray(Constants.OBJECT);

        for (int i = 0; i < jsonArrayTemplates.length(); i++) {
            JSONObject templatesJson = jsonArrayTemplates.getJSONObject(i);
            app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Template template = fromJsonToTemplate(templatesJson);
            templates.add(template);
        }
        return templates;
    }

    public app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Template fromJsonToTemplate(JSONObject templatesJson) throws JSONException {
        app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Template template = new app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Template();
        template.id = templatesJson.has(Constants.ID) ? templatesJson.getString(Constants.ID) : null;
        template.fileName = templatesJson.has(Constants.FILE_NAME) ? templatesJson.getString(Constants.FILE_NAME) : null;
        template.folderName = templatesJson.has(Constants.FOLDER_NAME) ? templatesJson.getString(Constants.FOLDER_NAME) : null;
        template.templatePath = templatesJson.has(Constants.PATH) ? templatesJson.getString(Constants.PATH) : null;
        template.templateType = templatesJson.has(Constants.TEMPLATE_TYPE) ? getTemplateType(templatesJson.getString(Constants.TEMPLATE_TYPE)) : null;
        template.category = templatesJson.has(Constants.CATEGORY_ID) ? getCategoryType(templatesJson.getString(Constants.CATEGORY_ID)) : null;
        return template;
    }

    @Override
    public app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Template getTemplateById(String id) throws JSONException {
        JSONObject templateJson = restClient.getRestClient().get(Constants.TEMPLATE_URL + Constants.SLASH + id, new HashMap<>());
        return fromJsonToTemplate(templateJson);
    }

    private boolean shouldUseSimpleSearch(SearchQuery searchQuery) {
        List<SearchCondition> searchConditions = searchQuery.getSearchConditions();
        return searchConditions == null || searchConditions.size() < 2;
    }

}
