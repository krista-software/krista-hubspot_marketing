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

package app.krista.extension.crm.marketing.hubspot_marketing.util;

/**
 * Centralized error messages for the HubSpot Marketing extension.
 * This class provides consistent, user-friendly error messages across the application.
 * 
 * <p><b>Security Note:</b> Error messages may include user-provided input (e.g., email addresses, IDs)
 * to help with troubleshooting. While this information is not highly sensitive, ensure that
 * error messages are logged appropriately and not exposed to unauthorized users.</p>
 */
public final class ErrorMessages {
    
    private ErrorMessages() {
        // Private constructor to prevent instantiation
    }

    // ========== Authentication Errors ==========
    
    public static final String INVALID_CLIENT_CREDENTIALS = 
        "Authentication failed: The Client ID or Client Secret is invalid. Please verify your HubSpot app credentials and try again.";
    
    public static final String REFRESH_TOKEN_EXPIRED = 
        "Refresh Token Expired: Your authentication token has expired. Please reauthorize yourself to continue.";
    
    public static final String AUTHORIZATION_REQUIRED = 
        "Authorization required: Please authorize yourself and click 'Validate Attributes' before saving changes to proceed further.";
    
    public static String unauthorizedUser(String configuredEmail, String authenticatedEmail) {
        return "Unauthorized user: User email '" + configuredEmail + "' configured in the setup does not match with authenticated user's email '" + authenticatedEmail + "'. Please verify your credentials.";
    }

    // ========== Validation Errors ==========
    
    public static String nullOrEmpty(String paramName) {
        return "Invalid input: " + paramName + " cannot be null or empty. Please provide a valid value.";
    }
    
    public static final String INVALID_STATE_PARAMETERS = 
        "Invalid state parameters: The OAuth state validation failed. This may indicate a security issue or an expired authorization request. Please try again.";

    // ========== API Errors ==========
    
    public static final String RESOURCE_NOT_FOUND = 
        "Resource not found: The requested HubSpot resource does not exist or you don't have permission to access it. Please verify the resource ID and your access permissions.";
    
    public static final String UNAUTHORIZED_ACCESS = 
        "Unauthorized access: Your credentials do not have permission to access this resource. Please check your HubSpot account permissions.";
    
    public static String apiRequestFailed(String responseBody) {
        return "HubSpot API request failed: " + responseBody + ". Please check the request parameters and try again.";
    }

    // ========== Email Entity Errors ==========
    
    public static String errorGettingEmailById(String id, String details) {
        return "Error retrieving email: Unable to fetch email with ID '" + id + "'. Details: " + details + ". Please verify the email ID exists in HubSpot.";
    }
    
    public static String errorCreatingEmail(String details) {
        return "Error creating email: Unable to create the email in HubSpot. Details: " + details + ". Please check your email data and try again.";
    }
    
    public static String errorDeletingEmail(String id, String details) {
        return "Error deleting email: Unable to delete email with ID '" + id + "'. Details: " + details + ". Please verify the email ID and your permissions.";
    }

    // ========== Template Entity Errors ==========
    
    public static String errorGettingTemplateById(String id, String details) {
        return "Error retrieving template: Unable to fetch template with ID '" + id + "'. Details: " + details + ". Please verify the template ID exists in HubSpot.";
    }

    // ========== Subscription Entity Errors ==========
    
    public static String errorGettingSubscriptionById(String id, String details) {
        return "Error retrieving subscription: Unable to fetch subscription with ID '" + id + "'. Details: " + details + ". Please verify the subscription ID exists in HubSpot.";
    }

    // ========== OAuth Errors ==========
    
    public static String errorParsingOAuthResponse(String details) {
        return "OAuth error: Unable to parse the OAuth response from HubSpot. Details: " + details + ". This may indicate an issue with the authentication flow. Please try again.";
    }
    
    public static String errorValidatingRefreshToken(String details) {
        return "Refresh token validation failed: Unable to validate the refresh token owner. Details: " + details + ". Please reauthorize your HubSpot account.";
    }

    // ========== General Errors ==========
    
    public static final String UNSUPPORTED_METHOD = 
        "Unsupported operation: This method is not supported for this entity type. Please refer to the documentation for supported operations.";
}

