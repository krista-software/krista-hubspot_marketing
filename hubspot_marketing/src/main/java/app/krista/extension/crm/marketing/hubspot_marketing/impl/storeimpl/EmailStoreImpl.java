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
import app.krista.extension.crm.marketing.hubspot_marketing.service.Email;
import app.krista.extension.crm.marketing.hubspot_marketing.util.Constants;
import app.krista.extension.executor.SearchQuery;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailStoreImpl implements Email {

    public static final Map<String, String> orderByMap = new HashMap<>();
    private static final Logger LOG = LogManager.getLogger(EmailStoreImpl.class.getName());
    public static final Map<String, String> orderByOperandMap = new HashMap<>();

    static {
        orderByMap.put("Id", Constants.ID);
        orderByMap.put("Name", Constants.NAME);
        orderByMap.put("Subject", Constants.SUBJECT);
        orderByMap.put("Created At", Constants.CREATED);
    }

    private final RestClientProvider restClient;

    @Inject
    public EmailStoreImpl(RestClientProvider restClient) {
        this.restClient = restClient;
    }

    public static String orderByMapUnloader(String key) {
        return orderByMap.get(key);
    }

    public static JSONObject createJsonBody(Map<String, Object> map) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Email email = new app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Email(map);

        jsonObject.put(Constants.NAME, email.name);
        jsonObject.put(Constants.SUBJECT, email.subject);
        jsonObject.put(Constants.FROM_NAME, email.fromName);
        jsonObject.put(Constants.REPLY_TO, email.replyTo);
        jsonObject.put(Constants.TEMPLATE_PATH, email.templatePath);
        jsonObject.put(Constants.SUBSCRIPTION, email.subscriptionId);
        jsonObject.put(Constants.CREATED, email.createdAt);
        return jsonObject;
    }

    public static boolean validateEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    public void getOrderByOperand() {
        orderByOperandMap.put("ASC", "");
        orderByOperandMap.put("DESC", "-");
    }

    public String mapUnlaoder(String key) {
        getOrderByOperand();
        return orderByOperandMap.get(key);
    }

    @Override
    public List<app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Email> getEmails(SearchQuery searchQuery, long pageIndex, int pageSize) throws JSONException {

        if (!searchQuery.getQueryClauses().isEmpty()) {
            String fieldName = searchQuery.getQueryClauses().getFirst().getFieldName();
            String operand = (String) searchQuery.getQueryClauses().getFirst().getOperand();
            String realOperand = mapUnlaoder(operand);

            if (fieldName != null || operand != null || searchQuery.getQueryClauses().getFirst().getOperator() != null) {
                JSONObject jsonEmail = restClient.getRestClient().get(Constants.EMAIL_URL,
                        Map.of("orderBy", realOperand + orderByMapUnloader(fieldName)));
                return fromJsonArrayToEmail(jsonEmail);

            }
        }
        JSONObject jsonEmail = restClient.getRestClient().get(Constants.EMAIL_URL, Map.of("limit", 100, "offSet", pageSize));
        return fromJsonArrayToEmail(jsonEmail);
    }

    public List<app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Email> fromJsonArrayToEmail(JSONObject jsonEmail) throws JSONException {
        List<app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Email> emails = new ArrayList<>();
        JSONArray jsonArrayTemplates = jsonEmail.getJSONArray(Constants.OBJECT);

        for (int i = 0; i < jsonArrayTemplates.length(); i++) {
            JSONObject emailJson = jsonArrayTemplates.getJSONObject(i);
            app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Email email = fromJsonToEmail(emailJson);
            emails.add(email);
        }
        return emails;
    }

    public app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Email fromJsonToEmail(JSONObject emailJson) throws JSONException {
        app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Email email = new app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Email();
        email.id = emailJson.getString(Constants.ID);
        email.name = emailJson.getString(Constants.NAME);
        email.subject = emailJson.getString(Constants.SUBJECT);
        email.fromName = emailJson.getString(Constants.FROM_NAME);
        email.replyTo = emailJson.getString(Constants.REPLY_TO);
        email.subscriptionId = emailJson.has(Constants.SUBSCRIPTION) ? emailJson.getString(Constants.SUBSCRIPTION) : null;
        email.authorName = emailJson.has(Constants.AUTHOR_NAME) ? emailJson.getString(Constants.AUTHOR_NAME) : null;
        email.authorMail = emailJson.has(Constants.AUTHOR_MAIL) ? emailJson.getString(Constants.AUTHOR_MAIL) : null;
        email.templatePath = emailJson.getString(Constants.TEMPLATE_PATH);
        email.createdAt = Long.valueOf(emailJson.getString(Constants.CREATED));

        return email;
    }

    @Override
    public app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Email getEmailById(String id) throws JSONException {
        JSONObject emailJson = restClient.getRestClient().get(Constants.EMAIL_URL + Constants.SLASH + id, new HashMap<>());
        return fromJsonToEmail(emailJson);
    }

    @Override
    public app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Email createEmail(Map<String, Object> map) throws JSONException {
        JSONObject jsonResponce = restClient.getRestClient().post(Constants.EMAIL_URL, createJsonBody(map).toString());
        return fromJsonToEmail(jsonResponce);
    }

    @Override
    public void deleteEmail(String id) throws JSONException {
        Response response = restClient.getRestClient().delete(Constants.EMAIL_URL + Constants.SLASH + id);
        if (response.getStatus() != 204) {
            LOG.info("Error occurred while deleting email");
        }
    }

}




