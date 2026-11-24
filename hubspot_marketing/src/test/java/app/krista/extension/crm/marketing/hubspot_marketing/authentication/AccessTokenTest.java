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

import app.krista.extension.crm.marketing.hubspot_marketing.TestConstants;
import app.krista.extension.crm.marketing.hubspot_marketing.authentication.AccessToken;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;


public class AccessTokenTest {

    @Test
    public void testValidAccessTokenCreation() {
        try {
            String jsonWithInvalidExpiresIn = "{\"access_token\": \"sampleAccessToken\", \"expires_in\": \"not_a_number\", \"token_type\": \"Bearer\", \"refresh_token\": \"sampleRefreshToken\"}";

            JSONObject json = new JSONObject(jsonWithInvalidExpiresIn);
            assertThrows(JSONException.class, () -> AccessToken.create(json));

        } catch (JSONException e) {
            fail("Exception thrown while creating AccessToken: " + e.getMessage());
        }
    }


    @Test
    public void testMissingFieldsInJSONObject() {
        String jsonData = "{"
                + "\"access_token\": \"sampleAccessToken\","
                + "\"token_type\": \"Bearer\""
                + "}";

        try {
            JSONObject json = new JSONObject(jsonData);
            assertThrows(JSONException.class, () -> AccessToken.create(json));
        } catch (JSONException e) {
            fail("Exception thrown while creating JSONObject: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidDataTypesInJSONObject() {
        try {
            JSONObject json = new JSONObject(TestConstants.ACCESS_TOKEN);
            assertThrows(JSONException.class, () -> AccessToken.create(json));
        } catch (JSONException e) {
            fail("Exception thrown while creating JSONObject: " + e.getMessage());
        }
    }

}