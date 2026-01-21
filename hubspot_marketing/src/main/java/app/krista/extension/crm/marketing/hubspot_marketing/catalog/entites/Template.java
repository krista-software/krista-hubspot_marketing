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
        ecosystemVersion = "82d5ab73-7eeb-46b7-adb5-e39a8762a569")
@Entity(name = "Template", id = "localDomainEntity_520f9f00-8ea0-4423-a9fd-cbfc7fd8cee8", primaryKey = "Id", supportStore = false)
@Searchable
public class Template {

    @Searchable
    @ToString
    @Field.Text(name = "Id", required = false, attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String id;

    @Searchable
    @ToString
    @Field.Text(name = "File Name", required = false, attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String fileName;

    @Searchable
    @ToString
    @Field.Text(name = "Folder Name", required = false, attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String folderName;


    @Searchable
    @ToString
    @Field.Text(name = "Template Path", required = false, attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String templatePath;

    @Searchable
    @ToString
    @Field.Text(name = "Template Type", required = false, attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String templateType;

    @Searchable
    @ToString
    @Field.Text(name = "Category", required = false, attributes = {@Attribute(name = "visualWidth", value = "S")})
    public String category;


}