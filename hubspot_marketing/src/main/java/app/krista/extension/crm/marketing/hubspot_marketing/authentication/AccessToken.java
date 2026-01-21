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

package app.krista.extension.crm.marketing.hubspot_marketing.authentication;

import app.krista.extension.crm.marketing.hubspot_marketing.util.Constants;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class AccessToken {
    public final String accessKeyToken;
    public final int expiresIn;
    public final String tokenType;
    public final String refreshToken;

    public AccessToken(String accessTokenTemp, int expiresIn, String tokenType, String refreshToken) {
        this.accessKeyToken = accessTokenTemp;
        this.expiresIn = expiresIn;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
    }

    /**
     * Creates an AccessToken object from a JSON representation.
     * <p>
     * This method constructs an AccessToken object using the data parsed from a JSON object.
     * It expects the JSON object to contain fields for "access_token," "expires_in," "token_type,"
     * and "refresh_token."
     *
     * @param json The JSON object containing access token information.
     * @return An AccessToken object initialized with data from the JSON object.
     * @throws JSONException If there are issues parsing the JSON or if required fields are missing.
     */
    public static AccessToken create(JSONObject json) throws JSONException {
        return new AccessToken(
                json.getString(Constants.ACCESS_TOKEN),
                json.getInt(Constants.EXPIRES_IN),
                json.getString(Constants.TOKEN_TYPE),
                json.getString(Constants.REFRESH_TOKEN));
    }

}
