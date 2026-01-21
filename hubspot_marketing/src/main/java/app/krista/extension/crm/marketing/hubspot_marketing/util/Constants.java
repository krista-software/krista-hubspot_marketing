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

package app.krista.extension.crm.marketing.hubspot_marketing.util;

public class Constants {


    public static final String OAUTH_URL = "https://app.hubspot.com/oauth/";
    public static final String REFRESH_TOKEN_OWNER_URL = "oauth/v1/refresh-tokens/";
    public static final String TOKEN_TYPE = "token_type";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String HASH = "#";
    public static final String USER = "user";

    public static final String ID = "id";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String EXPIRES_IN = "expires_in";
    public static final String CREATE_AT = "createdAt";
    public static final String SUBSCRIPTION_URL = "communication-preferences/v3/definitions";
    public static final String DESCRIPTION = "description";
    public static final String TOKEN_URL = "https://api.hubapi.com/oauth/v1/token";
    public static final String BASE_URL = "https://api.hubapi.com/";
    public static final String BEARER = "Bearer ";
    public static final String CLIENT_SECRET = "clientSecret";
    public static final String CLIENT_ID = "clientId";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String AUTHORIZATION = "Authorization";
    public static final String SUBJECT = "subject";
    public static final String SCOPE = "content communication_preferences.read communication_preferences.read_write communication_preferences.write settings.users.read crm.objects.contacts.read";
    public static final String TEMPLATE_URL = "content/api/v2/templates";
    public static final String EMAIL_URL = "marketing-emails/v1/emails";
    public static final String SLASH = "/";
    public static final String OBJECT = "objects";
    public static final String NAME = "name";
    public static final String FROM_NAME = "fromName";
    public static final String REPLY_TO = "replyTo";
    public static final String SUBSCRIPTION = "subscription";
    public static final String AUTHOR_NAME = "authorName";
    public static final String AUTHOR_MAIL = "author";
    public static final String TEMPLATE_PATH = "templatePath";
    public static final String PATH = "path";
    public static final String SUBSCRIPTION_DEFINITIONS = "subscriptionDefinitions";
    public static final String PURPOSE = "purpose";
    public static final String FILE_NAME = "filename";
    public static final String FOLDER_NAME = "folder";
    public static final String TEMPLATE_TYPE = "template_type";
    public static final String CATEGORY_ID = "category_id";
    public static final String UNSUPPORTED_METHOD_MSG = "This method is not supported yet";
    public static final String EMAIL = "email";
    public static final String CREATED = "created";


    private Constants() {
    }


}
