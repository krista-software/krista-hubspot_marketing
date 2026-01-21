# HubSpot Marketing Extension - Architecture Documentation

## Overview

The HubSpot Marketing Extension is a Krista SDK-based integration that enables seamless interaction with HubSpot's Marketing API. This extension provides capabilities for managing marketing emails, templates, and subscription preferences through a secure OAuth 2.0 authentication flow.

**Version**: 2.0.5  
**Extension ID**: `hsmarketing`  
**Extension Name**: Hubspot Marketing New  
**Java Version**: 21

---

## Table of Contents

1. [Architecture Overview](#architecture-overview)
2. [Component Structure](#component-structure)
3. [Authentication Flow](#authentication-flow)
4. [Entity Management](#entity-management)
5. [Error Handling](#error-handling)
6. [API Integration](#api-integration)
7. [Design Patterns](#design-patterns)
8. [Security Considerations](#security-considerations)

---

## Architecture Overview

The extension follows a layered architecture pattern with clear separation of concerns:

```
┌─────────────────────────────────────────────────────────────┐
│                    Krista Platform                          │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│              HubSpot Marketing Extension                    │
│  ┌───────────────────────────────────────────────────────┐  │
│  │  Extension Layer (HubspotExtension.java)              │  │
│  └───────────────────────────────────────────────────────┘  │
│                            │                                 │
│  ┌───────────────────────────────────────────────────────┐  │
│  │  Catalog Layer (Entity Stores)                        │  │
│  │  - EmailStore                                         │  │
│  │  - TemplateStore                                      │  │
│  │  - SubscriptionStore                                  │  │
│  └───────────────────────────────────────────────────────┘  │
│                            │                                 │
│  ┌───────────────────────────────────────────────────────┐  │
│  │  Service Layer (Store Implementations)                │  │
│  │  - EmailStoreImpl                                     │  │
│  │  - TemplateStoreImpl                                  │  │
│  │  - SubscriptionStoreImpl                              │  │
│  └───────────────────────────────────────────────────────┘  │
│                            │                                 │
│  ┌───────────────────────────────────────────────────────┐  │
│  │  Authentication Layer                                 │  │
│  │  - OAuthClient                                        │  │
│  │  - RestClient                                         │  │
│  │  - RestClientProvider                                 │  │
│  └───────────────────────────────────────────────────────┘  │
│                            │                                 │
│  ┌───────────────────────────────────────────────────────┐  │
│  │  Utility Layer                                        │  │
│  │  - ErrorMessages                                      │  │
│  │  - StringValidator                                    │  │
│  │  - Constants                                          │  │
│  └───────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                  HubSpot Marketing API                      │
└─────────────────────────────────────────────────────────────┘
```

---

## Component Structure

### 1. Extension Layer

**File**: `HubspotExtension.java`

The main entry point for the extension, responsible for:
- Extension initialization and configuration
- Attribute validation
- Connection testing
- Catalog registration

**Key Methods**:
- `validateAttributes()`: Validates OAuth credentials and tests connection
- `getCatalogs()`: Returns available entity catalogs (Email, Template, Subscription)

### 2. Catalog Layer

Entity stores implementing the `EntityStore<T>` interface:

#### EmailStore
- **Purpose**: Manages marketing email operations
- **Operations**: Create, Read, Delete, Search
- **Entity**: `Email`

#### TemplateStore
- **Purpose**: Manages email template operations
- **Operations**: Read, Search
- **Entity**: `Template`

#### SubscriptionStore
- **Purpose**: Manages subscription preference operations
- **Operations**: Read, Search
- **Entity**: `Subscription`

### 3. Service Layer

Implementation classes that handle the actual business logic:

- **EmailStoreImpl**: Implements email CRUD operations
- **TemplateStoreImpl**: Implements template retrieval and search
- **SubscriptionStoreImpl**: Implements subscription retrieval and search

### 4. Authentication Layer

#### OAuthClient
Handles OAuth 2.0 authentication flow:
- Authorization URL generation
- Access token exchange
- Refresh token management

#### RestClient
HTTP client for HubSpot API communication:
- GET, POST, PATCH, DELETE operations
- Bearer token authentication
- Response handling and error management

#### RestClientProvider
Factory for creating authenticated REST clients:
- Admin client creation
- User-specific client creation
- Token refresh handling

### 5. API Layer

**HubspotResource**: JAX-RS resource for OAuth callback handling
- Receives OAuth authorization codes
- Exchanges codes for access tokens
- Validates user authorization
- Stores refresh tokens

---

## Authentication Flow

```
┌──────────┐                                    ┌──────────────┐
│  Krista  │                                    │   HubSpot    │
│ Platform │                                    │     API      │
└────┬─────┘                                    └──────┬───────┘
     │                                                  │
     │ 1. Request Authorization URL                    │
     ├─────────────────────────────────────────────────►
     │                                                  │
     │ 2. Return Authorization URL                     │
     ◄─────────────────────────────────────────────────┤
     │                                                  │
     │ 3. User Authorizes (Browser)                    │
     ├─────────────────────────────────────────────────►
     │                                                  │
     │ 4. Redirect with Authorization Code             │
     ◄─────────────────────────────────────────────────┤
     │                                                  │
     │ 5. Exchange Code for Access Token               │
     ├─────────────────────────────────────────────────►
     │                                                  │
     │ 6. Return Access Token + Refresh Token          │
     ◄─────────────────────────────────────────────────┤
     │                                                  │
     │ 7. Validate Refresh Token Owner                 │
     ├─────────────────────────────────────────────────►
     │                                                  │
     │ 8. Return Owner Email                           │
     ◄─────────────────────────────────────────────────┤
     │                                                  │
     │ 9. Store Refresh Token                          │
     │                                                  │
     │ 10. Make API Requests with Access Token         │
     ├─────────────────────────────────────────────────►
     │                                                  │
     │ 11. Return API Response                         │
     ◄─────────────────────────────────────────────────┤
     │                                                  │
```

### OAuth 2.0 Flow Details

1. **Authorization Request**: User initiates OAuth flow
2. **User Authorization**: User grants permissions in HubSpot
3. **Code Exchange**: Extension exchanges authorization code for tokens
4. **Token Storage**: Refresh token stored securely
5. **API Access**: Access token used for API requests
6. **Token Refresh**: Automatic refresh when access token expires

---

## Entity Management

### Email Entity

**Attributes**:
- `id`: Unique identifier
- `name`: Email name
- `subject`: Email subject line
- `created`: Creation timestamp
- `updated`: Last update timestamp

**Operations**:
- `create(Map<String, Object>)`: Create new email
- `get(String id)`: Retrieve email by ID
- `delete(String id)`: Delete email
- `search(SearchQuery, pageIndex, pageSize)`: Search emails with pagination

### Template Entity

**Attributes**:
- `id`: Unique identifier
- `name`: Template name
- `path`: Template path
- `isAvailableForNewContent`: Availability flag

**Operations**:
- `get(String id)`: Retrieve template by ID
- `search(SearchQuery, pageIndex, pageSize)`: Search templates with pagination

### Subscription Entity

**Attributes**:
- `id`: Unique identifier
- `name`: Subscription name
- `description`: Subscription description
- `active`: Active status

**Operations**:
- `get(String id)`: Retrieve subscription by ID
- `search(SearchQuery, pageIndex, pageSize)`: Search subscriptions with pagination

---

## Error Handling

The extension uses a centralized error handling approach through the `ErrorMessages` utility class.

### Error Categories

1. **Authentication Errors**
   - Invalid credentials
   - Expired refresh tokens
   - Unauthorized users

2. **Validation Errors**
   - Null or empty parameters
   - Invalid state parameters

3. **API Errors**
   - Resource not found
   - Unauthorized access
   - API request failures

4. **Entity Errors**
   - Email operation failures
   - Template retrieval errors
   - Subscription access errors

### Error Message Principles

- **User-Friendly**: Clear, actionable messages
- **Contextual**: Include relevant details (IDs, parameters)
- **Secure**: Avoid exposing sensitive information
- **Consistent**: Standardized format across the extension

---

## API Integration

### HubSpot Marketing API Endpoints

| Entity | Endpoint | Method | Purpose |
|--------|----------|--------|---------|
| Email | `/marketing/v3/emails` | GET | List emails |
| Email | `/marketing/v3/emails/{id}` | GET | Get email by ID |
| Email | `/marketing/v3/emails` | POST | Create email |
| Email | `/marketing/v3/emails/{id}` | DELETE | Delete email |
| Template | `/content/api/v2/templates` | GET | List templates |
| Template | `/content/api/v2/templates/{id}` | GET | Get template by ID |
| Subscription | `/email/public/v1/subscriptions` | GET | List subscriptions |
| Subscription | `/email/public/v1/subscriptions/{id}` | GET | Get subscription by ID |

---

## Design Patterns

### 1. Factory Pattern
- **RestClientProviderFactory**: Creates REST client instances with proper authentication

### 2. Dependency Injection
- Uses HK2/Jersey for dependency injection
- `@Inject` annotation for constructor injection
- `@Service` annotation for service registration

### 3. Entity Store Pattern
- Implements `EntityStore<T>` interface
- Provides CRUD operations for entities
- Supports search and pagination

### 4. Validator Pattern
- **StringValidator**: Centralized input validation
- Throws exceptions with user-friendly messages

---

## Security Considerations

1. **OAuth 2.0 Authentication**: Secure token-based authentication
2. **Refresh Token Storage**: Tokens stored securely in Krista platform
3. **User Validation**: Ensures authorized user matches configured user
4. **Error Message Sanitization**: Sensitive data not exposed in error messages
5. **HTTPS Communication**: All API calls use HTTPS
6. **Token Refresh**: Automatic token refresh prevents unauthorized access

---

## Version History

### Version 2.0.5 (Current)
- Centralized error messages in `ErrorMessages` utility class
- Improved error handling across all components
- Enhanced Javadoc documentation
- Better user-friendly error messages

### Version 2.0.4
- Previous stable release

---

## Dependencies

- **Krista SDK**: Core extension framework
- **Jersey Client**: JAX-RS client for HTTP communication
- **Jettison**: JSON processing
- **ScribeJava**: OAuth library
- **Log4j2**: Logging framework
- **HK2**: Dependency injection

---

## Future Enhancements

1. **Async Operations**: Add asynchronous processing for bulk operations
2. **Caching**: Implement caching for frequently accessed data
3. **Batch Operations**: Support batch create/update/delete
4. **Webhook Support**: Handle HubSpot webhooks for real-time updates
5. **Advanced Search**: Enhanced search capabilities with filters
6. **Metrics**: Add performance metrics and monitoring

---

*Last Updated: 2025-11-13*  
*Extension Version: 2.0.5*

