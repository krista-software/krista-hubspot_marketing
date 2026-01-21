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

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for ErrorMessages utility class.
 * Tests all error message constants and methods to ensure they provide
 * user-friendly, actionable error messages.
 */
public class ErrorMessagesTest {

    // ========== Constructor Tests ==========

    @Test
    public void testConstructorIsPrivate() throws Exception {
        // Arrange & Act
        java.lang.reflect.Constructor<ErrorMessages> constructor = ErrorMessages.class.getDeclaredConstructor();
        
        // Assert
        assertTrue("Constructor should be private", java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
    }

    // ========== Authentication Error Tests ==========

    @Test
    public void testInvalidClientCredentials() {
        // Arrange & Act
        String message = ErrorMessages.INVALID_CLIENT_CREDENTIALS;

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should mention authentication failed", message.contains("Authentication failed"));
        assertTrue("Should mention Client ID or Client Secret", message.contains("Client ID") || message.contains("Client Secret"));
        assertTrue("Should provide actionable guidance", message.contains("verify") || message.contains("try again"));
    }

    @Test
    public void testRefreshTokenExpired() {
        // Arrange & Act
        String message = ErrorMessages.REFRESH_TOKEN_EXPIRED;

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should mention refresh token", message.toLowerCase().contains("refresh token"));
        assertTrue("Should mention expired", message.toLowerCase().contains("expired"));
        assertTrue("Should provide actionable guidance", message.contains("reauthorize"));
    }

    @Test
    public void testAuthorizationRequired() {
        // Arrange & Act
        String message = ErrorMessages.AUTHORIZATION_REQUIRED;

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should mention authorization", message.toLowerCase().contains("authorization"));
        assertTrue("Should mention validate attributes", message.contains("Validate Attributes"));
    }

    @Test
    public void testUnauthorizedUser_WithValidEmails() {
        // Arrange
        String configuredEmail = "admin@example.com";
        String authenticatedEmail = "user@example.com";

        // Act
        String message = ErrorMessages.unauthorizedUser(configuredEmail, authenticatedEmail);

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should mention unauthorized", message.toLowerCase().contains("unauthorized"));
        assertTrue("Should include configured email", message.contains(configuredEmail));
        assertTrue("Should include authenticated email", message.contains(authenticatedEmail));
        assertTrue("Should provide actionable guidance", message.contains("verify"));
    }

    @Test
    public void testUnauthorizedUser_WithNullEmails() {
        // Arrange & Act
        String message = ErrorMessages.unauthorizedUser(null, null);

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should contain 'null' text", message.contains("null"));
    }

    // ========== Validation Error Tests ==========

    @Test
    public void testNullOrEmpty_WithParameterName() {
        // Arrange
        String paramName = "emailId";

        // Act
        String message = ErrorMessages.nullOrEmpty(paramName);

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should mention invalid input", message.contains("Invalid input"));
        assertTrue("Should include parameter name", message.contains(paramName));
        assertTrue("Should mention null or empty", message.contains("null or empty"));
        assertTrue("Should provide actionable guidance", message.contains("provide a valid value"));
    }

    @Test
    public void testNullOrEmpty_WithNullParameter() {
        // Arrange & Act
        String message = ErrorMessages.nullOrEmpty(null);

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should contain 'null' text", message.contains("null"));
    }

    @Test
    public void testInvalidStateParameters() {
        // Arrange & Act
        String message = ErrorMessages.INVALID_STATE_PARAMETERS;

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should mention invalid state", message.contains("Invalid state"));
        assertTrue("Should mention OAuth", message.contains("OAuth"));
        assertTrue("Should mention security", message.toLowerCase().contains("security"));
        assertTrue("Should provide actionable guidance", message.contains("try again"));
    }

    // ========== API Error Tests ==========

    @Test
    public void testResourceNotFound() {
        // Arrange & Act
        String message = ErrorMessages.RESOURCE_NOT_FOUND;

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should mention resource not found", message.contains("Resource not found"));
        assertTrue("Should mention HubSpot", message.contains("HubSpot"));
        assertTrue("Should provide actionable guidance", message.contains("verify"));
    }

    @Test
    public void testUnauthorizedAccess() {
        // Arrange & Act
        String message = ErrorMessages.UNAUTHORIZED_ACCESS;

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should mention unauthorized", message.toLowerCase().contains("unauthorized"));
        assertTrue("Should mention permissions", message.toLowerCase().contains("permission"));
        assertTrue("Should provide actionable guidance", message.contains("check"));
    }

    @Test
    public void testApiRequestFailed_WithResponseBody() {
        // Arrange
        String responseBody = "Invalid email format";

        // Act
        String message = ErrorMessages.apiRequestFailed(responseBody);

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should mention API request failed", message.contains("API request failed"));
        assertTrue("Should include response body", message.contains(responseBody));
        assertTrue("Should provide actionable guidance", message.contains("check") || message.contains("try again"));
    }

    @Test
    public void testApiRequestFailed_WithNullResponseBody() {
        // Arrange & Act
        String message = ErrorMessages.apiRequestFailed(null);

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should contain 'null' text", message.contains("null"));
    }

    // ========== Email Entity Error Tests ==========

    @Test
    public void testErrorGettingEmailById_WithValidParameters() {
        // Arrange
        String id = "12345";
        String details = "Connection timeout";

        // Act
        String message = ErrorMessages.errorGettingEmailById(id, details);

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should mention error retrieving email", message.contains("Error retrieving email"));
        assertTrue("Should include email ID", message.contains(id));
        assertTrue("Should include details", message.contains(details));
        assertTrue("Should provide actionable guidance", message.contains("verify"));
    }

    @Test
    public void testErrorGettingEmailById_WithNullParameters() {
        // Arrange & Act
        String message = ErrorMessages.errorGettingEmailById(null, null);

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should contain 'null' text", message.contains("null"));
    }

    @Test
    public void testErrorCreatingEmail_WithDetails() {
        // Arrange
        String details = "Missing required field: subject";

        // Act
        String message = ErrorMessages.errorCreatingEmail(details);

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should mention error creating email", message.contains("Error creating email"));
        assertTrue("Should include details", message.contains(details));
        assertTrue("Should provide actionable guidance", message.contains("check") || message.contains("try again"));
    }

    @Test
    public void testErrorCreatingEmail_WithNullDetails() {
        // Arrange & Act
        String message = ErrorMessages.errorCreatingEmail(null);

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should contain 'null' text", message.contains("null"));
    }

    @Test
    public void testErrorDeletingEmail_WithValidParameters() {
        // Arrange
        String id = "67890";
        String details = "Email not found";

        // Act
        String message = ErrorMessages.errorDeletingEmail(id, details);

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should mention error deleting email", message.contains("Error deleting email"));
        assertTrue("Should include email ID", message.contains(id));
        assertTrue("Should include details", message.contains(details));
        assertTrue("Should provide actionable guidance", message.contains("verify"));
    }

    // ========== Template Entity Error Tests ==========

    @Test
    public void testErrorGettingTemplateById_WithValidParameters() {
        // Arrange
        String id = "template-123";
        String details = "Template archived";

        // Act
        String message = ErrorMessages.errorGettingTemplateById(id, details);

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should mention error retrieving template", message.contains("Error retrieving template"));
        assertTrue("Should include template ID", message.contains(id));
        assertTrue("Should include details", message.contains(details));
        assertTrue("Should provide actionable guidance", message.contains("verify"));
    }

    // ========== Subscription Entity Error Tests ==========

    @Test
    public void testErrorGettingSubscriptionById_WithValidParameters() {
        // Arrange
        String id = "sub-456";
        String details = "Subscription inactive";

        // Act
        String message = ErrorMessages.errorGettingSubscriptionById(id, details);

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should mention error retrieving subscription", message.contains("Error retrieving subscription"));
        assertTrue("Should include subscription ID", message.contains(id));
        assertTrue("Should include details", message.contains(details));
        assertTrue("Should provide actionable guidance", message.contains("verify"));
    }

    // ========== OAuth Error Tests ==========

    @Test
    public void testErrorParsingOAuthResponse_WithDetails() {
        // Arrange
        String details = "Malformed JSON response";

        // Act
        String message = ErrorMessages.errorParsingOAuthResponse(details);

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should mention OAuth error", message.contains("OAuth error"));
        assertTrue("Should include details", message.contains(details));
        assertTrue("Should provide actionable guidance", message.contains("try again"));
    }

    @Test
    public void testErrorValidatingRefreshToken_WithDetails() {
        // Arrange
        String details = "Token revoked by user";

        // Act
        String message = ErrorMessages.errorValidatingRefreshToken(details);

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should mention refresh token validation", message.contains("Refresh token validation"));
        assertTrue("Should include details", message.contains(details));
        assertTrue("Should provide actionable guidance", message.contains("reauthorize"));
    }

    // ========== General Error Tests ==========

    @Test
    public void testUnsupportedMethod() {
        // Arrange & Act
        String message = ErrorMessages.UNSUPPORTED_METHOD;

        // Assert
        assertNotNull("Error message should not be null", message);
        assertTrue("Should mention unsupported operation", message.contains("Unsupported operation"));
        assertTrue("Should provide actionable guidance", message.contains("documentation"));
    }
}

