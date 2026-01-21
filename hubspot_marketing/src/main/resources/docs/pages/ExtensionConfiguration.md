# Extension Configuration

## Overview

The HubSpot Marketing Extension requires proper configuration to connect with your HubSpot account and access marketing features. This guide walks you through the complete setup process.

## Prerequisites

Before configuring the extension, ensure you have:

1. **Active HubSpot Account** with marketing features enabled
2. **HubSpot Developer Account** access
3. **Admin Permissions** in your HubSpot account
4. **Krista Platform Access** with extension management rights

## Configuration Parameters

The extension requires three essential configuration parameters:

| Parameter | Type | Required | Description | Example |
|-----------|------|----------|-------------|---------|
| **Client ID** | Text | Yes | OAuth application identifier from HubSpot | `12345678-1234-1234-1234-123456789012` |
| **Client Secret** | Secured Text | Yes | OAuth application secret from HubSpot | `abcd1234-efgh-5678-ijkl-9012mnop3456` |
| **Email** | Email | Yes | Email address associated with HubSpot account | `marketing@company.com` |

### Parameter Details

#### Client ID
- **Purpose**: Identifies your HubSpot application during OAuth authentication
- **Format**: UUID format (36 characters with hyphens)
- **Location**: Available in your HubSpot app settings under "App Info"
- **Security**: Not sensitive, can be visible in client-side code
- **Validation**: Must be valid UUID format and correspond to an active HubSpot app

#### Client Secret
- **Purpose**: Authenticates your application during token exchange
- **Format**: UUID format (36 characters with hyphens)
- **Location**: Available in your HubSpot app settings under "Auth"
- **Security**: **Highly sensitive** - treat as password, never expose publicly
- **Validation**: Must match the Client ID and be from an active HubSpot app

#### Email
- **Purpose**: Associates the extension with a specific HubSpot user account
- **Format**: Valid email address format
- **Requirements**: Must be registered and active in the target HubSpot account
- **Permissions**: User should have appropriate marketing permissions
- **Validation**: Must be reachable and associated with HubSpot account

## Step-by-Step Configuration

### Step 1: Access Extension Setup

1. **Navigate to Krista Workspace**
   - Log into your Krista platform
   - Go to **Extensions** section
   - Find **HubSpot Marketing** extension
   - Click on the extension to open details

2. **Open Setup Tab**
   - Click on the **Setup** tab
   - You'll see the configuration form with three required fields

### Step 2: Obtain HubSpot Credentials

If you haven't created a HubSpot app yet, follow the [Creating HubSpot App](pages/CreatingHubSpotApp.md) guide first.

1. **Access HubSpot Developer Account**
   - Go to [HubSpot Developer Portal](https://developers.hubspot.com/)
   - Sign in with your HubSpot credentials
   - Navigate to **Apps** section

2. **Locate Your App**
   - Find your marketing app in the apps list
   - Click on the app name to open settings

3. **Copy Client ID**
   - Go to **App Info** tab
   - Copy the **App ID** (this is your Client ID)
   - Paste into Krista's **Client ID** field

4. **Copy Client Secret**
   - Go to **Auth** tab
   - Copy the **Client Secret**
   - Paste into Krista's **Client Secret** field

### Step 3: Configure Email

1. **Enter Email Address**
   - Use the email address associated with your HubSpot account
   - This should be an email with marketing permissions
   - Example: `marketing@yourcompany.com`

2. **Verify Email Access**
   - Ensure the email account can access HubSpot marketing features
   - Confirm the user has appropriate permissions for:
     - Email creation and management
     - Template access
     - Subscription management

### Step 4: Validate Configuration

1. **Click Validate Attributes**
   - After entering all three parameters
   - Click the **Validate Attributes** button
   - This will initiate OAuth authentication flow

2. **Complete OAuth Flow**
   - You'll be redirected to HubSpot for authorization
   - Log in with your HubSpot credentials if prompted
   - Review and approve the requested permissions
   - You'll be redirected back to Krista upon success

3. **Verify Success**
   - Look for success confirmation in Krista
   - The extension status should show as "Configured"

### Step 5: Test Connection

1. **Use Test Connection**
   - Click the **Test Connection** button
   - This verifies the extension can communicate with HubSpot
   - Should return success if configuration is correct

2. **Troubleshoot if Needed**
   - If test fails, review error messages
   - Common issues include:
     - Incorrect Client ID or Secret
     - Missing OAuth scopes
     - Email not associated with HubSpot account

## Required OAuth Scopes

The extension requires the following HubSpot OAuth scopes:

| Scope | Purpose | Required For |
|-------|---------|--------------|
| `content` | Access to marketing content | Email and template management |
| `crm.objects.contacts.read` | Read contact information | Email targeting and personalization |
| `settings.users.read` | Read user settings | User context and permissions |

### Scope Configuration

1. **In HubSpot App Settings**
   - Go to **Auth** tab
   - Under **Scopes**, ensure all required scopes are selected
   - Save changes if modifications are made

2. **Scope Validation**
   - During OAuth flow, users will see requested permissions
   - All scopes must be approved for full functionality
   - Missing scopes will result in limited functionality

## Security Considerations

### Client Secret Protection
- **Never commit** Client Secret to version control
- **Use environment variables** in production deployments
- **Rotate secrets** periodically for enhanced security
- **Limit access** to only necessary personnel

### OAuth Token Security
- Tokens are automatically managed by the extension
- Refresh tokens are stored securely and encrypted
- Access tokens expire after 30 minutes and are auto-refreshed
- Revoke access in HubSpot if security is compromised

### Network Security
- All communication uses HTTPS/TLS encryption
- OAuth redirects must use secure URLs
- Validate redirect URIs match exactly in HubSpot app settings

## Troubleshooting Configuration

### Common Configuration Errors

#### "Invalid Client ID or client secret"
**Symptoms**: Error during validation or test connection
**Causes**:
- Incorrect Client ID or Client Secret
- HubSpot app is in draft mode
- App has been deleted or suspended

**Resolution**:
1. Verify credentials in HubSpot Developer Portal
2. Ensure app is published (not draft)
3. Check app status and permissions

#### "Email not found or unauthorized"
**Symptoms**: Authentication succeeds but operations fail
**Causes**:
- Email not associated with HubSpot account
- User lacks marketing permissions
- Account suspended or restricted

**Resolution**:
1. Verify email is active in HubSpot
2. Check user permissions in HubSpot settings
3. Contact HubSpot admin for permission issues

#### "Redirect URI mismatch"
**Symptoms**: OAuth flow fails with redirect error
**Causes**:
- Redirect URI in HubSpot app doesn't match Krista URL
- Protocol mismatch (HTTP vs HTTPS)
- Port or path differences

**Resolution**:
1. Copy exact Extension Base URL from Krista
2. Add `/hsmarketing/callback` to create full redirect URI
3. Update redirect URI in HubSpot app settings

### Configuration Validation Checklist

Before completing setup, verify:

- [ ] Client ID is correct UUID format
- [ ] Client Secret matches the Client ID
- [ ] Email is valid and associated with HubSpot account
- [ ] HubSpot app has all required scopes enabled
- [ ] Redirect URI is correctly configured
- [ ] User has marketing permissions in HubSpot
- [ ] Test connection succeeds
- [ ] OAuth flow completes successfully

## Next Steps

After successful configuration:

1. **Complete Authentication**: Follow the [Authentication](pages/Authentication.md) guide
2. **Test Connection**: Use "Test Connection" button to verify setup
3. **Explore Entities**: Start with [Email Entity](pages/EmailEntity.md)
4. **Review Limitations**: Understand API limits and best practices

## See Also

- [Creating HubSpot App](pages/CreatingHubSpotApp.md) - HubSpot app setup guide
- [Authentication](pages/Authentication.md) - OAuth 2.0 authentication details
- [Email Entity](pages/EmailEntity.md) - First entity to try
