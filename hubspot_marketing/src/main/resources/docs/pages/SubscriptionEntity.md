# Subscription Entity

## Overview

The Subscription entity provides access to HubSpot subscription preferences and settings through Krista's entity store
interface. This entity enables retrieval and searching of subscription data within your HubSpot account, which can be
used for managing email preferences and compliance.

## Entity Details

- **Entity Name**: Subscription
- **Primary Key**: Id
- **Domain**: Marketing Management
- **Ecosystem**: CRM
- **Searchable**: Yes
- **Store Support**: Read-only operations

## Supported Operations

| Operation  | Supported | Description                            |
|------------|-----------|----------------------------------------|
| **Create** | No        | Subscription creation not supported    |
| **Read**   | Yes       | Retrieve subscription by ID            |
| **Update** | No        | Subscription updates not supported     |
| **Delete** | No        | Subscription deletion not supported    |
| **Search** | Yes       | Search subscriptions with filters      |
| **List**   | Yes       | List all subscriptions with pagination |

> **Note**: The Subscription entity is read-only. Subscriptions must be created and managed directly in HubSpot's
> interface.

## Entity Fields

### Output Parameters

| Field Name      | Type | Required | Searchable | Description                              | Example                            |
|-----------------|------|----------|------------|------------------------------------------|------------------------------------|
| **Id**          | Text | No       | Yes        | Unique identifier for the subscription   | `5653177348`                       |
| **Name**        | Text | No       | Yes        | Display name of the subscription         | `Monthly Newsletter`               |
| **Description** | Text | No       | Yes        | Detailed description of the subscription | `Monthly updates and company news` |
| **Purpose**     | Text | No       | Yes        | Purpose or category of the subscription  | `Marketing Communications`         |
| **Created At**  | Text | No       | Yes        | Creation timestamp                       | `2023-12-01T10:30:00Z`             |

### Field Descriptions

#### Id

- **Purpose**: Unique identifier assigned by HubSpot
- **Format**: Numeric string
- **Usage**: Required for retrieving specific subscriptions and linking to emails
- **Auto-generated**: Yes, by HubSpot

#### Name

- **Purpose**: Human-readable name for the subscription
- **Format**: Free text, typically descriptive
- **Usage**: Displayed to users in subscription management interfaces
- **Example**: `Weekly Newsletter`, `Product Updates`, `Event Notifications`

#### Description

- **Purpose**: Detailed explanation of the subscription content
- **Format**: Free text, can be longer than name
- **Usage**: Provides users with context about subscription content
- **Example**: `Weekly digest of company news, product updates, and industry insights`

#### Purpose

- **Purpose**: Categorizes the subscription by business purpose
- **Format**: Category or classification string
- **Usage**: Groups subscriptions for management and compliance
- **Common Values**: `Marketing Communications`, `Transactional`, `Product Updates`, `Support`

#### Created At

- **Purpose**: Timestamp when subscription was created
- **Format**: ISO 8601 date string or timestamp
- **Usage**: Tracking, auditing, and chronological sorting
- **Auto-generated**: Yes, by HubSpot

## Usage Examples

### Retrieving Subscription by ID

```json
{
  "id": "5653177348"
}
```

**Response**:

```json
{
  "id": "5653177348",
  "name": "Monthly Newsletter",
  "description": "Monthly updates featuring company news, product announcements, and industry insights",
  "purpose": "Marketing Communications",
  "createdAt": "2023-12-01T10:30:00Z"
}
```

### Searching Subscriptions

**Search by Name**:

```json
{
  "searchQuery": {
    "queryClauses": [
      {
        "fieldName": "Name",
        "operator": "CONTAINS",
        "operand": "Newsletter"
      }
    ]
  },
  "pageIndex": 1,
  "pageSize": 10
}
```

**Search by Purpose**:

```json
{
  "searchQuery": {
    "queryClauses": [
      {
        "fieldName": "Purpose",
        "operator": "EQUALS",
        "operand": "Marketing Communications"
      }
    ]
  },
  "pageIndex": 1,
  "pageSize": 20
}
```

**Search by Description**:

```json
{
  "searchQuery": {
    "queryClauses": [
      {
        "fieldName": "Description",
        "operator": "CONTAINS",
        "operand": "product updates"
      }
    ]
  },
  "pageIndex": 1,
  "pageSize": 15
}
```

### List All Subscriptions

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
- Name
- Description
- Purpose
- Created At

### Search Operators

| Operator      | Description   | Example                                       |
|---------------|---------------|-----------------------------------------------|
| `EQUALS`      | Exact match   | `"Purpose" EQUALS "Marketing Communications"` |
| `CONTAINS`    | Partial match | `"Name" CONTAINS "Newsletter"`                |
| `STARTS_WITH` | Prefix match  | `"Name" STARTS_WITH "Weekly"`                 |
| `ENDS_WITH`   | Suffix match  | `"Name" ENDS_WITH "Updates"`                  |

### Pagination

- **Page Index**: 1-based indexing (first page = 1)
- **Page Size**: Number of results per page (1-100)
- **Default Page Size**: 20 results
- **Maximum Page Size**: 100 results

## Integration with Email Entity

Subscriptions are linked to emails through the `Subscription Id` field in the Email entity. This connection enables
proper subscription management and compliance.

### Example Integration

1. **Find Subscription for Email**:

```json
{
  "searchQuery": {
    "queryClauses": [
      {
        "fieldName": "Purpose",
        "operator": "EQUALS",
        "operand": "Marketing Communications"
      }
    ]
  }
}
```

2. **Use Subscription in Email Creation**:

```json
{
  "Name": "Monthly Newsletter - January",
  "Subject": "Your January Newsletter",
  "From Name": "Marketing Team",
  "Reply To": "marketing@company.com",
  "Template Path": "NEWSLETTER_TEMPLATES/monthly.html",
  "Subscription Id": "5653177348"
}
```

## Error Handling

### Common Errors

#### "Id is required"

**Cause**: Missing or empty ID parameter for get operations
**Resolution**: Provide valid subscription ID

#### "Error occurred while getting subscription by id"

**Cause**: Subscription not found or access denied
**Resolution**:

1. Verify subscription ID exists in HubSpot
2. Check user permissions for subscription access
3. Ensure subscription hasn't been deleted

#### "Error occurred while searching subscriptions"

**Cause**: Invalid search parameters or HubSpot API error
**Resolution**:

1. Verify search query format
2. Check field names and operators
3. Ensure user has subscription access permissions

### Validation Rules

- **ID Format**: Must be numeric string when provided
- **Search Parameters**: Field names must match entity field names exactly
- **Pagination**: Page index must be positive, page size between 1-100

## Best Practices

### Subscription Management

1. **Purpose Classification**: Use consistent purpose values for better organization
2. **Descriptive Names**: Choose clear, user-friendly subscription names
3. **Regular Auditing**: Periodically review subscription usage and relevance

### Compliance Considerations

1. **Subscription Tracking**: Link emails to appropriate subscriptions for compliance
2. **Purpose Documentation**: Maintain clear purpose definitions for legal requirements
3. **Audit Trail**: Use creation timestamps for compliance reporting

### Integration Patterns

1. **Subscription Selection**: Provide users with searchable subscription lists
2. **Purpose Filtering**: Filter subscriptions by purpose for relevant selections
3. **Validation**: Verify subscription IDs before using in email creation

## Performance Considerations

### Search Performance

- **Index Usage**: All searchable fields are indexed for optimal performance
- **Query Complexity**: Simple queries perform better than complex multi-field searches
- **Page Size**: Use reasonable page sizes (20-50) for optimal performance

### Caching Strategies

- **Subscription Lists**: Cache frequently accessed subscription lists
- **Purpose Groups**: Cache subscriptions grouped by purpose
- **Active Subscriptions**: Cache currently active subscription data

## Common Use Cases

### Subscription Browser

```javascript
// Get all marketing subscriptions
const marketingSubscriptions = await subscriptionStore.search({
  queryClauses: [{
    fieldName: "Purpose",
    operator: "EQUALS",
    operand: "Marketing Communications"
  }]
}, 1, 50);

// Display subscriptions for user selection
const subscriptionOptions = marketingSubscriptions.map(sub => ({
  value: sub.id,
  label: `${sub.name} - ${sub.description}`
}));
```

### Subscription Validation

```javascript
// Verify subscription exists before email creation
const subscriptionId = "5653177348";
try {
  const subscription = await subscriptionStore.get(subscriptionId);
  if (subscription && subscription.id) {
    // Use subscription.id in email creation
    return subscription.id;
  }
} catch (error) {
  console.error("Subscription not found:", error);
}
```

### Purpose-based Filtering

```javascript
// Get all subscriptions by purpose
const purposes = ["Marketing Communications", "Product Updates", "Support"];
const subscriptionsByPurpose = {};

for (const purpose of purposes) {
  subscriptionsByPurpose[purpose] = await subscriptionStore.search({
    queryClauses: [{
      fieldName: "Purpose",
      operator: "EQUALS",
      operand: purpose
    }]
  }, 1, 100);
}
```

## Compliance and Legal Considerations

### GDPR Compliance

- **Subscription Tracking**: Maintain clear records of subscription purposes
- **Consent Management**: Link subscriptions to consent records
- **Data Processing**: Document legal basis for each subscription purpose

### CAN-SPAM Compliance

- **Subscription Categories**: Clearly define subscription types and purposes
- **Unsubscribe Management**: Ensure proper subscription management for opt-outs
- **Commercial vs. Transactional**: Use purpose field to distinguish email types

### Best Practices for Compliance

1. **Clear Purposes**: Define specific, granular purposes for each subscription
2. **Regular Reviews**: Audit subscription usage and compliance regularly
3. **Documentation**: Maintain records of subscription creation and usage
4. **User Control**: Provide clear subscription management options to users

## See Also

- [Email Entity](pages/EmailEntity.md) - Using subscriptions in email creation
- [Template Entity](pages/TemplateEntity.md) - Managing email templates
- [Extension Configuration](pages/ExtensionConfiguration.md) - Setup requirements
- [Authentication](pages/Authentication.md) - OAuth and permission requirements
