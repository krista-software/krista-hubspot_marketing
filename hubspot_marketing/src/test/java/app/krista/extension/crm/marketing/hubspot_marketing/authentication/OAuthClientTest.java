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


import app.krista.extension.crm.marketing.hubspot_marketing.HubspotAttributes;

import static org.junit.Assert.assertEquals;


public class OAuthClientTest {

    @org.junit.Test
    public void test_generateOAuthUrl() {
        // Create a real HubspotAttributes instance instead of mocking
        HubspotAttributes hubspotAttributes = new HubspotAttributes(
            "testClientId",
            "testClientSecret",
            "test@example.com",
            "http://example.com"
        );

        OAuthClient oAuthClient = new OAuthClient(hubspotAttributes);

        String state = "testState";
        String expectedUrl = "https://app.hubspot.com/oauth/authorize?client_id=testClientId&redirect_uri=http%3A%2F%2Fexample.com%2Fhsmarketing%2Fcallback&scope=content+communication_preferences.read+communication_preferences.read_write+communication_preferences.write+settings.users.read+crm.objects.contacts.read&state=testState";
        String actualUrl = oAuthClient.getOAuthUrl(state);

        assertEquals(expectedUrl, actualUrl);
    }


}