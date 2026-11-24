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

package app.krista.extension.crm.marketing.hubspot_marketing.catalog.entites;

import app.krista.extension.impl.anno.*;

import java.util.Map;

@Domain(id = "catEntryDomain_44aee5a3-7112-45a1-a751-7127535d9ec6",
        name = "Marketing Management",
        ecosystemId = "catEntryEcosystem_8a0e8ce7-6289-492f-9202-52284024280c",
        ecosystemName = "CRM",
        ecosystemVersion = "8de2405a-4e3d-45a3-bebe-d99fa0da2ff5")
@Entity(name = "Email", id = "localDomainEntity_b80cdefa-ba36-4478-9f9a-18779806c72a", primaryKey = "Id", supportStore = false)
@Searchable
public class Email {

    @Searchable
    @ToString
    @Field.Text(name = "Id", required = false, attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String id;

    @Searchable
    @ToString
    @Field.Text(name = "Name", attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String name;

    @Searchable
    @ToString
    @Field.Text(name = "Subject", attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String subject;

    @Searchable
    @ToString
    @Field.Text(name = "From Name", attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String fromName;

    @Searchable
    @ToString
    @Field.Text(name = "Reply To", attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String replyTo;

    @Searchable
    @ToString
    @Field.Text(name = "Author Name", required = false, attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String authorName;

    @Searchable
    @ToString
    @Field.Text(name = "Author Mail", required = false, attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String authorMail;

    @Searchable
    @ToString
    @Field.Text(name = "Template Path", attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String templatePath;

    @Searchable
    @ToString
    @Field.Text(name = "Subscription Id", required = false, attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String subscriptionId;

    @ToString
    @Searchable
    @Field.Date(name = "Created At", showHowManyDaysInViewer = 60, attributes = {@Attribute(name = "visualWidth", value = "S")})
    public Long createdAt;

    public Email(Map<String, Object> emailData) {
        this.id = getValue(emailData, "Id");
        this.name = getValue(emailData, "Name");
        this.fromName = getValue(emailData, "From Name");
        this.subject = getValue(emailData, "Subject");
        this.replyTo = getValue(emailData, "Reply To");
        this.subscriptionId = getValue(emailData, "Subscription Id");
        this.authorMail = getValue(emailData, "Author Mail");
        this.authorName = getValue(emailData, "Author Name");
        this.templatePath = getValue(emailData, "Template Path");
        this.createdAt = Long.valueOf(getValue(emailData, "Created At"));

    }

    public Email() {
    }

    private String getValue(Map<String, Object> emailData, String key) {
        Object value = emailData.get(key);
        if (value != null) {
            return value.toString();
        } else {
            return null;
        }
    }

}
