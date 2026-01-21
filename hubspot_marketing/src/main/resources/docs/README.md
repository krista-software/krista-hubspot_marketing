# Krista HubSpot Marketing Extension

## Overview

The **HubSpot Marketing Extension** enables seamless integration between Krista and HubSpot's Marketing platform,
allowing you to manage marketing emails, templates, and subscriptions efficiently through automated workflows and
conversations.

## Key Features

**Email Management**: Create, retrieve, and manage HubSpot marketing emails
**Template Management**: Access and manage email templates
**Subscription Management**: Handle subscription data and preferences
**OAuth 2.0 Authentication**: Secure authentication with HubSpot
**Entity Store Support**: Search and filter emails, templates, and subscriptions
**Marketing Automation**: Streamline marketing workflows through Krista

## Quick Start Guide

1. **[Configure the Extension](pages/ExtensionConfiguration.md)** - Set up Client ID, Client Secret, and Email
2. **[Create HubSpot App](pages/CreatingHubSpotApp.md)** - Create and configure your HubSpot application
3. **[Authenticate](pages/Authentication.md)** - Complete OAuth 2.0 authentication flow
4. **[Explore Entities](pages/EmailEntity.md)** - Start with Email entity management

## Documentation Structure

### Getting Started

- [Extension Configuration](pages/ExtensionConfiguration.md) - Setup and configuration guide
- [Authentication](pages/Authentication.md) - OAuth 2.0 authentication details
- [Creating HubSpot App](pages/CreatingHubSpotApp.md) - HubSpot app creation steps

### Entity Management

- [Email Entity](pages/EmailEntity.md) - Marketing email operations
- [Template Entity](pages/TemplateEntity.md) - Email template management
- [Subscription Entity](pages/SubscriptionEntity.md) - Subscription data handling

### Additional Resources

- [Release Notes](pages/release_notes.md) - Version history and updates

## Extension Information

- **Extension Name**: Hubspot Marketing New
- **Version**: 2.0.3
- **JAXRS ID**: hsmarketing
- **Type**: Entity Store Extension
- **Supported Entities**: Email, Template, Subscription

## Getting Help

If you encounter issues:

- Check the configuration in [Extension Configuration](pages/ExtensionConfiguration.md)
- Verify authentication setup in [Authentication](pages/Authentication.md)
- Review entity-specific documentation for usage examples
- Check [Release Notes](pages/release_notes.md) for known issues

## Technical Requirements

- **HubSpot Account**: Active HubSpot account with marketing features
- **OAuth App**: HubSpot app with appropriate scopes
- **Krista Platform**: Compatible Krista version with entity store support