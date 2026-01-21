# Authentication

## Overview

The HubSpot Marketing Extension uses OAuth 2.0 for secure authentication with HubSpot's Marketing API. This ensures that user credentials are never shared directly with the extension while providing controlled access to HubSpot marketing data based on granted permissions.

## Authentication Modes

### User Account Authentication

The extension supports **User Account Authentication** where each user authenticates with their own HubSpot account:

- **Individual Access**: Each user maintains their own connection to HubSpot
- **Personal Permissions**: Access is limited to marketing data the user has permission to view
- **Account Selection**: Users can choose which HubSpot account to connect during authentication
- **Token Isolation**: Each user's tokens are stored separately and securely

**When to Use User Authentication**:
- When users need access to their personal HubSpot marketing data
- For multi-tenant environments where users have different HubSpot accounts
- When fine-grained permission control is required

## OAuth 2.0 Scopes and Permissions

The extension requires the following OAuth 2.0 scopes for full functionality:

### Required Scopes

| Scope | Permission Level | Description |
|-------|------------------|-------------|
| `content` | Read/Write | Access to marketing content including emails and templates |
| `communication_preferences.read` | Read | Read communication preferences and subscription settings |
| `communication_preferences.read_write` | Read/Write | Manage communication preferences and subscription settings |
| `communication_preferences.write` | Write | Update communication preferences and subscription settings |
| `settings.users.read` | Read | Read user settings and information |
| `crm.objects.contacts.read` | Read | Read access to contact objects for email targeting |

### Scope URLs

The complete scope string used in OAuth requests:
```
content communication_preferences.read communication_preferences.read_write communication_preferences.write settings.users.read crm.objects.contacts.read
```

### Why These Scopes Are Required

- **Content Management**: Full access to marketing emails and templates requires content scope
- **Communication Preferences**: Managing subscription preferences and email communication settings requires communication_preferences scopes
- **Contact Integration**: Contact read access enables email targeting and personalization
- **User Context**: User settings provide context for personalized marketing operations

## OAuth 2.0 Grant Types

### Authorization Code Grant

- **Grant Type**: `authorization_code`
- **Used For**: Initial authentication and token acquisition
- **Flow**: User authorization → Authorization code → Access token

**Request Example**:
```http
POST https://api.hubapi.com/oauth/v1/token
Content-Type: application/x-www-form-urlencoded

grant_type=authorization_code&
code={authorization_code}&
client_id={client_id}&
client_secret={client_secret}&
redirect_uri={redirect_uri}
```

**Response Example**:
```json
{
  "access_token": "CKaeyJhbGciOiJIUzI1NiJ9...",
  "refresh_token": "CKaeyJhbGciOiJIUzI1NiJ9...",
  "expires_in": 1800,
  "token_type": "bearer"
}
```

### Refresh Token Grant

- **Grant Type**: `refresh_token`
- **Used For**: Obtaining new access tokens when they expire
- **Flow**: Refresh token → New access token

**Request Example**:
```http
POST https://api.hubapi.com/oauth/v1/token
Content-Type: application/x-www-form-urlencoded

grant_type=refresh_token&
refresh_token={refresh_token}&
client_id={client_id}&
client_secret={client_secret}&
redirect_uri={redirect_uri}
```

## Authentication Flow

### Step-by-Step OAuth Flow

1. **User Initiates Authentication**
   - User clicks "Validate Attributes" in extension setup
   - Extension generates authorization URL with required scopes

2. **Authorization Request**
   - User is redirected to HubSpot authorization server
   - URL: `https://app.hubspot.com/oauth/authorize`
   - Parameters: client_id, redirect_uri, scope, state

3. **User Authorization**
   - User logs into HubSpot (if not already logged in)
   - User reviews and approves requested permissions
   - User selects HubSpot account (if multiple accounts available)

4. **Authorization Code Exchange**
   - HubSpot redirects back to extension with authorization code
   - Extension exchanges code for access token and refresh token
   - Tokens are securely stored for future API requests

5. **API Access**
   - Extension uses access token for HubSpot Marketing API requests
   - Token is included in Authorization header: `Bearer {access_token}`

## Token Management

### Access Token Expiration

- **Lifetime**: 30 minutes (1800 seconds)
- **Usage**: Required for all API requests
- **Format**: JWT (JSON Web Token)
- **Storage**: Encrypted in extension's secure storage

### Refresh Token Expiration

**For Verified Apps**:
- **Lifetime**: No expiration (permanent until revoked)
- **Usage**: Obtain new access tokens automatically
- **Revocation**: Only when user explicitly revokes access

**For Unverified Apps**:
- **Lifetime**: 30 days from last use
- **Usage**: Must be used within 30 days to remain valid
- **Renewal**: Each successful refresh extends the 30-day period

### Automatic Token Refresh

The extension automatically handles token refresh:

1. **Pre-emptive Refresh**: Tokens are refreshed before expiration
2. **Error-based Refresh**: If API returns 401, token is refreshed and request retried
3. **Background Process**: Token refresh happens transparently to users
4. **Failure Handling**: If refresh fails, user is prompted to re-authenticate

## Security Best Practices

### 1. Token Security
- All tokens are encrypted at rest
- Tokens are transmitted only over HTTPS
- No tokens are logged or exposed in client-side code
- Regular token rotation through refresh mechanism

### 2. Scope Minimization
- Extension requests only necessary scopes
- Users can review exact permissions before granting access
- Scope access is validated on each API request

### 3. State Parameter
- Unique state parameter prevents CSRF attacks
- State is validated on OAuth callback
- Random, unpredictable state values are generated

### 4. Redirect URI Validation
- Exact redirect URI matching required
- No wildcard or partial matching allowed
- URI must be HTTPS in production environments

## Troubleshooting Authentication

### Common Authentication Issues

#### "Invalid Client ID or client secret"
**Cause**: Incorrect credentials or app configuration
**Resolution**:
1. Verify Client ID and Client Secret from HubSpot app
2. Ensure app is not in draft mode
3. Check that all required scopes are enabled

#### "Redirect URI mismatch"
**Cause**: Redirect URI in HubSpot app doesn't match extension URL
**Resolution**:
1. Copy exact Extension Base URL from Krista
2. Add `/hsmarketing/callback` suffix
3. Update redirect URI in HubSpot app settings

#### "Access denied" during authorization
**Cause**: User declined permissions or insufficient account access
**Resolution**:
1. Ensure user has admin access to HubSpot account
2. Review and approve all requested scopes
3. Contact HubSpot admin if permissions are restricted

#### "Token expired" errors
**Cause**: Refresh token has expired or been revoked
**Resolution**:
1. Re-authenticate through extension setup
2. Complete OAuth flow again
3. Verify app is still installed in HubSpot account

### Authentication Debugging

**Enable Debug Logging**:
- Check extension logs for OAuth flow details
- Review API response codes and error messages
- Verify token refresh attempts and outcomes

**Test Connection**:
- Use "Test Connection" button in extension setup
- Verify API connectivity and token validity
- Confirm scope access with simple API call

## Marketing-Specific Considerations

### Content Permissions
- Ensure user has marketing content creation/editing permissions
- Verify access to email and template management features
- Check subscription management capabilities

### API Rate Limits
- HubSpot Marketing API has specific rate limits
- Extension handles rate limiting automatically
- Large operations may be queued or batched

### Data Access Scope
- Marketing data access depends on user permissions
- Some features may be limited based on HubSpot subscription level
- Enterprise features require appropriate HubSpot plan

## See Also

- [Extension Configuration](pages/ExtensionConfiguration.md) - Setup and configuration guide
- [Creating HubSpot App](pages/CreatingHubSpotApp.md) - HubSpot app creation steps
- [Email Entity](pages/EmailEntity.md) - First entity to test authentication
