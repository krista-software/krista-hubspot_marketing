# Template Entity

## Overview

The Template entity provides access to HubSpot email templates through Krista's entity store interface. This entity enables retrieval and searching of email templates within your HubSpot account, which can be used for creating marketing emails.

## Entity Details

- **Entity Name**: Template
- **Primary Key**: Id
- **Domain**: Marketing Management
- **Ecosystem**: CRM
- **Searchable**: Yes
- **Store Support**: Read-only operations

## Supported Operations

| Operation | Supported | Description |
|-----------|-----------|-------------|
| **Create** | No | Template creation not supported |
| **Read** | Yes | Retrieve template by ID |
| **Update** | No | Template updates not supported |
| **Delete** | No | Template deletion not supported |
| **Search** | Yes | Search templates with filters |
| **List** | Yes | List all templates with pagination |

> **Note**: The Template entity is read-only. Templates must be created and managed directly in HubSpot's interface.

## Entity Fields

### Output Parameters

| Field Name | Type | Required | Searchable | Description | Example |
|------------|------|----------|------------|-------------|---------|
| **Id** | Text | No | Yes | Unique identifier for the template | `12345` |
| **File Name** | Text | No | Yes | Name of the template file | `welcome_email.html` |
| **Folder Name** | Text | No | Yes | Folder containing the template | `WELCOME_TEMPLATES` |
| **Template Path** | Text | No | Yes | Full path to the template | `WELCOME_TEMPLATES/welcome_email.html` |
| **Template Type** | Text | No | Yes | Type or category of template | `EMAIL_TEMPLATE` |
| **Category** | Text | No | Yes | Template category classification | `Marketing` |

### Field Descriptions

#### Id
- **Purpose**: Unique identifier assigned by HubSpot
- **Format**: Numeric string
- **Usage**: Required for retrieving specific templates
- **Auto-generated**: Yes, by HubSpot

#### File Name
- **Purpose**: Name of the template file
- **Format**: Filename with extension (typically .html)
- **Usage**: Identifies the template file within its folder
- **Example**: `newsletter_template.html`, `welcome_email.html`

#### Folder Name
- **Purpose**: Folder or directory containing the template
- **Format**: Folder name string
- **Usage**: Organizes templates into logical groups
- **Example**: `NEWSLETTER_TEMPLATES`, `WELCOME_SERIES`

#### Template Path
- **Purpose**: Complete path to the template file
- **Format**: Folder/filename combination
- **Usage**: Used in Email entity creation to reference templates
- **Example**: `EMAIL_DND_TEMPLATE/test template 2.html`

#### Template Type
- **Purpose**: Classification of template type
- **Format**: Type identifier string
- **Usage**: Categorizes templates by their intended use
- **Common Values**: `EMAIL_TEMPLATE`, `LANDING_PAGE`, `BLOG_POST`

#### Category
- **Purpose**: Business category or classification
- **Format**: Category name string
- **Usage**: Groups templates by business purpose
- **Example**: `Marketing`, `Sales`, `Support`, `Newsletter`

## Usage Examples

### Retrieving Template by ID

```json
{
  "id": "12345"
}
```

**Response**:
```json
{
  "id": "12345",
  "fileName": "welcome_email.html",
  "folderName": "WELCOME_TEMPLATES",
  "templatePath": "WELCOME_TEMPLATES/welcome_email.html",
  "templateType": "EMAIL_TEMPLATE",
  "category": "Marketing"
}
```

### Searching Templates

**Search by Folder Name**:
```json
{
  "searchQuery": {
    "queryClauses": [
      {
        "fieldName": "Folder Name",
        "operator": "EQUALS",
        "operand": "WELCOME_TEMPLATES"
      }
    ]
  },
  "pageIndex": 1,
  "pageSize": 10
}
```

**Search by Template Type**:
```json
{
  "searchQuery": {
    "queryClauses": [
      {
        "fieldName": "Template Type",
        "operator": "EQUALS",
        "operand": "EMAIL_TEMPLATE"
      }
    ]
  },
  "pageIndex": 1,
  "pageSize": 20
}
```

**Search by Category**:
```json
{
  "searchQuery": {
    "queryClauses": [
      {
        "fieldName": "Category",
        "operator": "CONTAINS",
        "operand": "Marketing"
      }
    ]
  },
  "pageIndex": 1,
  "pageSize": 15
}
```

### List All Templates

```json
{
  "searchQuery": {
    "queryClauses": []
  },
  "pageIndex": 1,
  "pageSize": 50
}
```

## Search Capabilities

### Supported Search Fields

All fields are searchable:
- Id
- File Name
- Folder Name
- Template Path
- Template Type
- Category

### Search Operators

| Operator | Description | Example |
|----------|-------------|---------|
| `EQUALS` | Exact match | `"Category" EQUALS "Marketing"` |
| `CONTAINS` | Partial match | `"File Name" CONTAINS "welcome"` |
| `STARTS_WITH` | Prefix match | `"Folder Name" STARTS_WITH "EMAIL"` |
| `ENDS_WITH` | Suffix match | `"File Name" ENDS_WITH ".html"` |

### Pagination

- **Page Index**: 1-based indexing (first page = 1)
- **Page Size**: Number of results per page (1-100)
- **Default Page Size**: 20 results
- **Maximum Page Size**: 100 results

## Integration with Email Entity

Templates are primarily used when creating Email entities. The `Template Path` field from this entity is used as the `Template Path` parameter in Email creation.

### Example Integration

1. **Search for Templates**:
```json
{
  "searchQuery": {
    "queryClauses": [
      {
        "fieldName": "Category",
        "operator": "EQUALS",
        "operand": "Newsletter"
      }
    ]
  }
}
```

2. **Use Template in Email Creation**:
```json
{
  "Name": "Monthly Newsletter",
  "Subject": "Your Monthly Update",
  "From Name": "Marketing Team",
  "Reply To": "marketing@company.com",
  "Template Path": "NEWSLETTER_TEMPLATES/monthly_newsletter.html"
}
```

## Error Handling

### Common Errors

#### "Id is required"
**Cause**: Missing or empty ID parameter for get operations
**Resolution**: Provide valid template ID

#### "Error occurred while getting template by id"
**Cause**: Template not found or access denied
**Resolution**:
1. Verify template ID exists in HubSpot
2. Check user permissions for template access
3. Ensure template hasn't been deleted

#### "Error occurred while searching templates"
**Cause**: Invalid search parameters or HubSpot API error
**Resolution**:
1. Verify search query format
2. Check field names and operators
3. Ensure user has template access permissions

### Validation Rules

- **ID Format**: Must be numeric string when provided
- **Search Parameters**: Field names must match entity field names exactly
- **Pagination**: Page index must be positive, page size between 1-100

## Best Practices

### Template Discovery
1. **Use Descriptive Searches**: Search by category or folder for better organization
2. **Pagination**: Use appropriate page sizes for performance
3. **Caching**: Cache frequently used template information

### Template Organization
1. **Folder Structure**: Organize templates in logical folders
2. **Naming Conventions**: Use consistent naming for easy discovery
3. **Categories**: Leverage categories for business-level organization

### Integration Patterns
1. **Template Selection**: Provide users with searchable template lists
2. **Path Validation**: Verify template paths before using in email creation
3. **Template Metadata**: Use template information for user-friendly displays

## Performance Considerations

### Search Performance
- **Index Usage**: All searchable fields are indexed for optimal performance
- **Query Complexity**: Simple queries perform better than complex multi-field searches
- **Page Size**: Use reasonable page sizes (20-50) for optimal performance

### Caching Strategies
- **Template Lists**: Cache frequently accessed template lists
- **Template Metadata**: Cache template details for faster email creation
- **Folder Contents**: Cache folder-based template listings

## Common Use Cases

### Template Browser
```javascript
// Get all templates by category
const marketingTemplates = await templateStore.search({
  queryClauses: [{
    fieldName: "Category",
    operator: "EQUALS",
    operand: "Marketing"
  }]
}, 1, 50);

// Display templates for user selection
const templateOptions = marketingTemplates.map(template => ({
  value: template.templatePath,
  label: `${template.fileName} (${template.folderName})`
}));
```

### Template Validation
```javascript
// Verify template exists before email creation
const templateId = "12345";
try {
  const template = await templateStore.get(templateId);
  if (template && template.templatePath) {
    // Use template.templatePath in email creation
    return template.templatePath;
  }
} catch (error) {
  console.error("Template not found:", error);
}
```

### Folder-based Organization
```javascript
// Get all templates in a specific folder
const folderTemplates = await templateStore.search({
  queryClauses: [{
    fieldName: "Folder Name",
    operator: "EQUALS",
    operand: "WELCOME_SERIES"
  }]
}, 1, 100);
```

## See Also

- [Email Entity](pages/EmailEntity.md) - Using templates in email creation
- [Subscription Entity](pages/SubscriptionEntity.md) - Managing subscription preferences
- [Extension Configuration](pages/ExtensionConfiguration.md) - Setup requirements
- [Authentication](pages/Authentication.md) - OAuth and permission requirements
