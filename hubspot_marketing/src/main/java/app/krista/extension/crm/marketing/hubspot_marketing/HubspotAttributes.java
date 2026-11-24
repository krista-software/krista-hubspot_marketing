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

package app.krista.extension.crm.marketing.hubspot_marketing;

import app.krista.extension.executor.Invoker;
import app.krista.extension.request.RoutingInfo;
import app.krista.extension.request.protos.http.HttpProtocol;
import app.krista.extension.util.InvokerAttributes;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.util.Map;

@Service
public class HubspotAttributes {

    public static final String CLIENT_ID = "clientId";
    public static final String CLIENT_SECRET = "clientSecret";
    public static final String EMAIL = "email";
    private String clientId;
    private String clientSecret;
    private String emailId;
    private String redirectUrl;


    @Inject
    public HubspotAttributes(Invoker invoker) {
        this(invoker.getAttributes(), invoker.getRoutingInfo().getRoutingURL(HttpProtocol.PROTOCOL_NAME, RoutingInfo.Type.APPLIANCE));
    }

    public HubspotAttributes() {
    }

    public HubspotAttributes(Map<String, Object> attributes, String routingUrl) {
        this(InvokerAttributes.getStringOrNull(attributes, CLIENT_ID),
                InvokerAttributes.getStringOrNull(attributes, CLIENT_SECRET),
                InvokerAttributes.getStringOrNull(attributes, EMAIL),
                routingUrl
        );
    }

    public HubspotAttributes(String clientId, String clientSecret, String emailId, String routingUrlString) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.emailId = emailId;
        if (routingUrlString != null) {
            this.redirectUrl = routingUrlString;

        }
    }

    public static HubspotAttributes create(Map<String, Object> connectionAttributes, String routingUrl) {
        return new HubspotAttributes(connectionAttributes, routingUrl);
    }

    public void update(Map<String, Object> newAttributes) {
        clientId = InvokerAttributes.getStringOrNull(newAttributes, CLIENT_ID);
        clientSecret = InvokerAttributes.getStringOrNull(newAttributes, CLIENT_SECRET);
        emailId = InvokerAttributes.getStringOrNull(newAttributes, EMAIL);
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getEmailId() {
        return emailId;
    }


    public String getRedirectUrl() {
        return redirectUrl+ "/hsmarketing/callback";
    }
}
