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

@Domain(id = "catEntryDomain_44aee5a3-7112-45a1-a751-7127535d9ec6",
        name = "Marketing Management",
        ecosystemId = "catEntryEcosystem_8a0e8ce7-6289-492f-9202-52284024280c",
        ecosystemName = "CRM",
        ecosystemVersion = "41af214f-4d61-4775-bd23-b58698880df5")
@Entity(name = "Subscription", id = "localDomainEntity_48175504-a124-494c-b310-09ab33e2372b", primaryKey = "Id", supportStore = false)
@Searchable
public class Subscription {

    @Searchable
    @ToString
    @Field.Text(name = "Id", required = false, attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String id;

    @Searchable
    @ToString
    @Field.Text(name = "Name", required = false, attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String name;

    @Searchable
    @Field.Text(name = "Description", required = false, attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String description;

    @Searchable
    @ToString
    @Field.Text(name = "Purpose", required = false, attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String purpose;

    @Searchable
    @ToString
    @Field.Text(name = "Created At", required = false, attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String createdAt;


    public Subscription(String id, String name, String description, String purpose, String createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.purpose = purpose;
        this.createdAt = createdAt;
    }

    public Subscription() {
    }

}