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

import app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites.Email;
import app.krista.extension.crm.marketing.hubspot_marketing.catalog.stores.EmailStore;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.connector.RestClientProvider;
import app.krista.extension.crm.marketing.hubspot_marketing.impl.storeimpl.EmailStoreImpl;
import org.codehaus.jettison.json.JSONException;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmailStoreTest {

    @Mock
    RestClientProvider restClient;

    @Mock
    EmailStoreImpl emailStoreImpl;

    @Test
    public void test_createEmail_success() throws IOException, JSONException {
        //Given
        EmailStoreImpl emailStoreImpl = mock(EmailStoreImpl.class);
        EmailStore emailStore = new EmailStore(emailStoreImpl);
        Map<String, Object> map = new HashMap<>();
        when(emailStoreImpl.createEmail(map)).thenReturn(new Email());

        //When
        Email result = emailStore.create(map);

        //Then
        assertNotNull(result);
    }

    @Test
    public void test_getEmailById_success() throws IOException, JSONException {
        RestClientProvider restClient = mock(RestClientProvider.class);
        EmailStoreImpl emailStore = mock(EmailStoreImpl.class);
        EmailStore emailStoreUnderTest = new EmailStore(emailStore);
        String id = "123";

        when(emailStore.getEmailById(id)).thenReturn(new Email());
        Email result = emailStoreUnderTest.get(id);

        assertNotNull(result);
    }

    @Test
    public void test_get_throwsIllegalStateException_whenIdIsNullOrEmpty() throws IOException {
        RestClientProvider restClient = mock(RestClientProvider.class);
        EmailStoreImpl emailStore = mock(EmailStoreImpl.class);
        EmailStore emailStoreUnderTest = new EmailStore(emailStore);
        String id = null;

        assertThrows(IllegalStateException.class, () -> emailStoreUnderTest.get(id));
    }

    @Test
    public void test_create_throwsRuntimeException_whenJSONExceptionOccurs() throws IOException, JSONException {
        RestClientProvider restClient = mock(RestClientProvider.class);
        EmailStoreImpl emailStore = mock(EmailStoreImpl.class);
        EmailStore emailStoreUnderTest = new EmailStore(emailStore);
        Map<String, Object> map = new HashMap<>();

        when(emailStore.createEmail(map)).thenThrow(new JSONException("JSON exception occurred"));

        assertThrows(RuntimeException.class, () -> emailStoreUnderTest.create(map));
    }

    public Map<String, Object> getMapForEmail() throws JSONException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "MAIL");
        jsonMap.put("subject", "created from api main");
        jsonMap.put("fromName", "Aradhya Gudadhe");
        jsonMap.put("replyTo", "9665830211a@gmail.com");
        jsonMap.put("templatePath", "EMAIL_DND_TEMPLATE/test template 1.html");
        jsonMap.put("subscription", "275386342");

        return jsonMap;

    }


}