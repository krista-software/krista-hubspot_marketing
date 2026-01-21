# Email Entity

## Overview

The Email entity provides comprehensive management of HubSpot marketing emails through Krista's entity store interface. This entity enables creation, retrieval, searching, and deletion of marketing emails within your HubSpot account.

## Entity Details

- **Entity Name**: Email
- **Primary Key**: Id
- **Domain**: Marketing Management
- **Ecosystem**: CRM
- **Searchable**: Yes
- **Store Support**: Entity Store operations

## Supported Operations

| Operation | Supported | Description |
|-----------|-----------|-------------|
| **Create** | Yes | Create new marketing emails |
| **Read** | Yes | Retrieve email by ID |
| **Update** | No | Update operations not supported |
| **Delete** | Yes | Delete emails by ID |
| **Search** | Yes | Search emails with filters |
| **List** | Yes | List all emails with pagination |

## Entity Fields

### Input/Output Parameters

| Field Name | Type | Required | Searchable | Description | Example |
|------------|------|----------|------------|-------------|---------|
| **Id** | Text | No | Yes | Unique identifier for the email | `67567` |
| **Name** | Text | Yes | Yes | Display name of the email | `Appointment Scheduled` |
| **Subject** | Text | Yes | Yes | Email subject line | `Your appointment is confirmed` |
| **From Name** | Text | Yes | Yes | Sender's display name | `Jack Henry` |
| **Reply To** | Text | Yes | Yes | Reply-to email address | `JackHenry@gmail.com` |
| **Author Name** | Text | No | Yes | Email author's name | `Jack Henry` |
| **Author Mail** | Text | No | Yes | Email author's email address | `JackHenry@gmail.com` |
| **Template Path** | Text | Yes | Yes | Path to email template | `EMAIL_DND_TEMPLATE/test template 2.html` |
| **Subscription Id** | Text | No | Yes | Associated subscription ID | `5653177348` |
| **Created At** | Date | No | Yes | Email creation timestamp | `1640995200000` |

### Field Descriptions

#### Id
- **Purpose**: Unique identifier assigned by HubSpot
- **Format**: Numeric string
- **Auto-generated**: Yes, by HubSpot when email is created
- **Usage**: Required for get, update, and delete operations

#### Name
- **Purpose**: Human-readable name for the email
- **Format**: Free text, up to 255 characters
- **Usage**: Displayed in HubSpot interface and email lists
- **Best Practice**: Use descriptive names that indicate email purpose

#### Subject
- **Purpose**: Email subject line shown to recipients
- **Format**: Free text, recommended under 50 characters
- **Usage**: Displayed in recipient's inbox
- **Best Practice**: Clear, compelling subject lines improve open rates

#### From Name
- **Purpose**: Sender name displayed to recipients
- **Format**: Free text, typically person or company name
- **Usage**: Shown in recipient's inbox as sender
- **Best Practice**: Use consistent, recognizable sender names

#### Reply To
- **Purpose**: Email address for recipient replies
- **Format**: Valid email address
- **Usage**: Where replies are sent when recipients respond
- **Validation**: Must be valid email format

#### Author Name
- **Purpose**: Internal tracking of email creator
- **Format**: Free text
- **Usage**: Internal HubSpot tracking and reporting
- **Optional**: Can be left empty

#### Author Mail
- **Purpose**: Email address of the email creator
- **Format**: Valid email address
- **Usage**: Internal tracking and notifications
- **Optional**: Can be left empty

#### Template Path
- **Purpose**: Path to the email template file
- **Format**: File path string
- **Usage**: References the HTML template used for email content
- **Required**: Must point to valid template in HubSpot

#### Subscription Id
- **Purpose**: Links email to subscription preferences
- **Format**: Numeric string
- **Usage**: Manages unsubscribe and preference settings
- **Optional**: Can be empty for non-subscription emails

#### Created At
- **Purpose**: Timestamp when email was created
- **Format**: Unix timestamp (milliseconds)
- **Auto-generated**: Yes, by HubSpot
- **Usage**: Sorting, filtering, and reporting

## Usage Examples

### Creating an Email

```json
{
  "Name": "Welcome Email Campaign",
  "Subject": "Welcome to Our Newsletter!",
  "From Name": "Marketing Team",
  "Reply To": "marketing@company.com",
  "Author Name": "John Doe",
  "Author Mail": "john.doe@company.com",
  "Template Path": "WELCOME_TEMPLATE/welcome_email.html",
  "Subscription Id": "12345"
}
```

### Searching Emails

**Search by Name**:
```json
{
  "searchQuery": {
    "queryClauses": [
      {
        "fieldName": "Name",
        "operator": "CONTAINS",
        "operand": "Welcome"
      }
    ]
  },
  "pageIndex": 1,
  "pageSize": 10
}
```

**Search by Subject**:
```json
{
  "searchQuery": {
    "queryClauses": [
      {
        "fieldName": "Subject",
        "operator": "EQUALS",
        "operand": "Newsletter Update"
      }
    ]
  },
  "pageIndex": 1,
  "pageSize": 20
}
```

### Retrieving Email by ID

```json
{
  "id": "67567"
}
```

### Deleting an Email

```json
{
  "id": "67567"
}
```

## Search Capabilities

### Supported Search Fields

All fields marked as "Searchable" can be used in search queries:
- Id
- Name
- Subject
- From Name
- Reply To
- Author Name
- Author Mail
- Template Path
- Subscription Id
- Created At

### Search Operators

| Operator | Description | Example |
|----------|-------------|---------|
| `EQUALS` | Exact match | `"Name" EQUALS "Welcome Email"` |
| `CONTAINS` | Partial match | `"Subject" CONTAINS "Newsletter"` |
| `STARTS_WITH` | Prefix match | `"From Name" STARTS_WITH "Marketing"` |
| `ENDS_WITH` | Suffix match | `"Reply To" ENDS_WITH "@company.com"` |

### Pagination

- **Page Index**: 1-based indexing (first page = 1)
- **Page Size**: Number of results per page (1-100)
- **Default Page Size**: 20 results
- **Maximum Page Size**: 100 results

## Error Handling

### Common Errors

#### "Id is required"
**Cause**: Missing or empty ID parameter for get/delete operations
**Resolution**: Provide valid email ID

#### "Error occurred while creating email"
**Cause**: Invalid input parameters or HubSpot API error
**Resolution**: 
1. Verify all required fields are provided
2. Check field formats and values
3. Ensure template path exists in HubSpot

#### "Error occurred while deleting email"
**Cause**: Email not found or insufficient permissions
**Resolution**:
1. Verify email ID exists
2. Check user permissions for email deletion
3. Ensure email is not in use by active campaigns

### Validation Rules

- **Required Fields**: Name, Subject, From Name, Reply To, Template Path must be provided
- **Email Format**: Reply To and Author Mail must be valid email addresses
- **Template Path**: Must reference existing template in HubSpot
- **ID Format**: Must be numeric string when provided

## Best Practices

### Email Creation
1. **Use Descriptive Names**: Choose names that clearly identify the email's purpose
2. **Optimize Subject Lines**: Keep under 50 characters for better deliverability
3. **Consistent From Names**: Use recognizable sender names for brand consistency
4. **Valid Reply Addresses**: Ensure reply-to addresses are monitored and functional

### Template Management
1. **Organize Templates**: Use clear folder structure in template paths
2. **Template Validation**: Verify templates exist before creating emails
3. **Version Control**: Maintain template versions for email consistency

### Search Optimization
1. **Use Specific Queries**: Narrow searches with multiple criteria
2. **Pagination**: Use appropriate page sizes for performance
3. **Field Selection**: Search on indexed fields for better performance

## Integration Patterns

### Workflow Integration
```javascript
// Create email in workflow
const emailData = {
  "Name": `Campaign ${campaignId}`,
  "Subject": dynamicSubject,
  "From Name": "Marketing Team",
  "Reply To": "marketing@company.com",
  "Template Path": selectedTemplate
};

const email = await emailStore.create(emailData);
```

### Batch Operations
```javascript
// Search and process multiple emails
const searchResults = await emailStore.search({
  queryClauses: [{
    fieldName: "Created At",
    operator: "GREATER_THAN",
    operand: lastProcessedDate
  }]
}, 1, 50);

for (const email of searchResults) {
  // Process each email
}
```

## Performance Considerations

### Search Performance
- **Index Usage**: Search on indexed fields (all searchable fields are indexed)
- **Page Size**: Use reasonable page sizes (20-50) for optimal performance
- **Query Complexity**: Simple queries perform better than complex multi-field searches

### Rate Limiting
- **HubSpot Limits**: Respect HubSpot API rate limits
- **Batch Processing**: Group operations when possible
- **Error Handling**: Implement retry logic for rate limit errors

## See Also

- [Template Entity](pages/TemplateEntity.md) - Managing email templates
- [Subscription Entity](pages/SubscriptionEntity.md) - Managing subscription preferences
- [Extension Configuration](pages/ExtensionConfiguration.md) - Setup requirements
- [Authentication](pages/Authentication.md) - OAuth and permission requirements
