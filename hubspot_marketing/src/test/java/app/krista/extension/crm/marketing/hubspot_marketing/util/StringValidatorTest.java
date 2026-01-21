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
 * Unit tests for StringValidator utility class.
 * Tests validation logic with various input scenarios including
 * null, empty, whitespace, and valid strings.
 */
public class StringValidatorTest {

    // ========== Constructor Tests ==========

    @Test
    public void testConstructorIsPrivate() throws Exception {
        // Arrange & Act
        java.lang.reflect.Constructor<StringValidator> constructor = StringValidator.class.getDeclaredConstructor();
        
        // Assert
        assertTrue("Constructor should be private", java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
    }

    // ========== Null Input Tests ==========

    @Test
    public void testIsNulOrEmptyThrowException_WithNullInput() {
        // Arrange
        String input = null;
        String paramName = "testParam";

        // Act & Assert
        try {
            StringValidator.isNulOrEmptyThrowException(input, paramName);
            fail("Should have thrown IllegalStateException for null input");
        } catch (IllegalStateException e) {
            // Assert
            assertNotNull("Exception message should not be null", e.getMessage());
            assertTrue("Exception message should mention parameter name", e.getMessage().contains(paramName));
            assertTrue("Exception message should mention null or empty", e.getMessage().contains("null or empty"));
        }
    }

    @Test
    public void testIsNulOrEmptyThrowException_WithNullInputAndNullParamName() {
        // Arrange
        String input = null;
        String paramName = null;

        // Act & Assert
        try {
            StringValidator.isNulOrEmptyThrowException(input, paramName);
            fail("Should have thrown IllegalStateException for null input");
        } catch (IllegalStateException e) {
            // Assert
            assertNotNull("Exception message should not be null", e.getMessage());
            assertTrue("Exception message should contain 'null'", e.getMessage().contains("null"));
        }
    }

    // ========== Empty String Tests ==========

    @Test
    public void testIsNulOrEmptyThrowException_WithEmptyString() {
        // Arrange
        String input = "";
        String paramName = "emailId";

        // Act & Assert
        try {
            StringValidator.isNulOrEmptyThrowException(input, paramName);
            fail("Should have thrown IllegalStateException for empty string");
        } catch (IllegalStateException e) {
            // Assert
            assertNotNull("Exception message should not be null", e.getMessage());
            assertTrue("Exception message should mention parameter name", e.getMessage().contains(paramName));
            assertTrue("Exception message should mention null or empty", e.getMessage().contains("null or empty"));
            assertTrue("Exception message should provide guidance", e.getMessage().contains("provide a valid value"));
        }
    }

    @Test
    public void testIsNulOrEmptyThrowException_WithEmptyStringAndEmptyParamName() {
        // Arrange
        String input = "";
        String paramName = "";

        // Act & Assert
        try {
            StringValidator.isNulOrEmptyThrowException(input, paramName);
            fail("Should have thrown IllegalStateException for empty string");
        } catch (IllegalStateException e) {
            // Assert
            assertNotNull("Exception message should not be null", e.getMessage());
        }
    }

    // ========== Whitespace Tests ==========

    @Test
    public void testIsNulOrEmptyThrowException_WithSingleSpace() {
        // Arrange
        String input = " ";
        String paramName = "userId";

        // Act - Should NOT throw exception (single space is not empty)
        StringValidator.isNulOrEmptyThrowException(input, paramName);
        
        // Assert - If we reach here, no exception was thrown (which is correct behavior)
        assertTrue("Validation passed for single space", true);
    }

    @Test
    public void testIsNulOrEmptyThrowException_WithMultipleSpaces() {
        // Arrange
        String input = "   ";
        String paramName = "templateId";

        // Act - Should NOT throw exception (spaces are not empty string)
        StringValidator.isNulOrEmptyThrowException(input, paramName);
        
        // Assert - If we reach here, no exception was thrown (which is correct behavior)
        assertTrue("Validation passed for multiple spaces", true);
    }

    @Test
    public void testIsNulOrEmptyThrowException_WithTab() {
        // Arrange
        String input = "\t";
        String paramName = "subscriptionId";

        // Act - Should NOT throw exception (tab is not empty string)
        StringValidator.isNulOrEmptyThrowException(input, paramName);
        
        // Assert - If we reach here, no exception was thrown (which is correct behavior)
        assertTrue("Validation passed for tab character", true);
    }

    @Test
    public void testIsNulOrEmptyThrowException_WithNewline() {
        // Arrange
        String input = "\n";
        String paramName = "contactId";

        // Act - Should NOT throw exception (newline is not empty string)
        StringValidator.isNulOrEmptyThrowException(input, paramName);
        
        // Assert - If we reach here, no exception was thrown (which is correct behavior)
        assertTrue("Validation passed for newline character", true);
    }

    // ========== Valid Input Tests ==========

    @Test
    public void testIsNulOrEmptyThrowException_WithValidString() {
        // Arrange
        String input = "validValue";
        String paramName = "emailId";

        // Act - Should NOT throw exception
        StringValidator.isNulOrEmptyThrowException(input, paramName);
        
        // Assert - If we reach here, no exception was thrown (which is correct)
        assertTrue("Validation passed for valid string", true);
    }

    @Test
    public void testIsNulOrEmptyThrowException_WithNumericString() {
        // Arrange
        String input = "12345";
        String paramName = "id";

        // Act - Should NOT throw exception
        StringValidator.isNulOrEmptyThrowException(input, paramName);
        
        // Assert - If we reach here, no exception was thrown (which is correct)
        assertTrue("Validation passed for numeric string", true);
    }

    @Test
    public void testIsNulOrEmptyThrowException_WithSpecialCharacters() {
        // Arrange
        String input = "!@#$%^&*()";
        String paramName = "token";

        // Act - Should NOT throw exception
        StringValidator.isNulOrEmptyThrowException(input, paramName);
        
        // Assert - If we reach here, no exception was thrown (which is correct)
        assertTrue("Validation passed for special characters", true);
    }

    @Test
    public void testIsNulOrEmptyThrowException_WithStringContainingSpaces() {
        // Arrange
        String input = "valid value with spaces";
        String paramName = "name";

        // Act - Should NOT throw exception
        StringValidator.isNulOrEmptyThrowException(input, paramName);
        
        // Assert - If we reach here, no exception was thrown (which is correct)
        assertTrue("Validation passed for string with spaces", true);
    }

    @Test
    public void testIsNulOrEmptyThrowException_WithLongString() {
        // Arrange
        String input = "This is a very long string that contains many characters and should still be considered valid";
        String paramName = "description";

        // Act - Should NOT throw exception
        StringValidator.isNulOrEmptyThrowException(input, paramName);
        
        // Assert - If we reach here, no exception was thrown (which is correct)
        assertTrue("Validation passed for long string", true);
    }

    @Test
    public void testIsNulOrEmptyThrowException_WithUnicodeCharacters() {
        // Arrange
        String input = "Hello 世界 🌍";
        String paramName = "message";

        // Act - Should NOT throw exception
        StringValidator.isNulOrEmptyThrowException(input, paramName);
        
        // Assert - If we reach here, no exception was thrown (which is correct)
        assertTrue("Validation passed for unicode characters", true);
    }

    // ========== Parameter Name Tests ==========

    @Test
    public void testIsNulOrEmptyThrowException_WithDifferentParamNames() {
        // Arrange
        String[] paramNames = {"id", "emailId", "templateId", "subscriptionId", "userId", "contactId"};

        // Act & Assert
        for (String paramName : paramNames) {
            try {
                StringValidator.isNulOrEmptyThrowException(null, paramName);
                fail("Should have thrown IllegalStateException for param: " + paramName);
            } catch (IllegalStateException e) {
                assertTrue("Exception message should contain param name: " + paramName, 
                          e.getMessage().contains(paramName));
            }
        }
    }

    // ========== Edge Case Tests ==========

    @Test
    public void testIsNulOrEmptyThrowException_WithSingleCharacter() {
        // Arrange
        String input = "a";
        String paramName = "code";

        // Act - Should NOT throw exception
        StringValidator.isNulOrEmptyThrowException(input, paramName);
        
        // Assert - If we reach here, no exception was thrown (which is correct)
        assertTrue("Validation passed for single character", true);
    }

    @Test
    public void testIsNulOrEmptyThrowException_WithZeroString() {
        // Arrange
        String input = "0";
        String paramName = "count";

        // Act - Should NOT throw exception
        StringValidator.isNulOrEmptyThrowException(input, paramName);
        
        // Assert - If we reach here, no exception was thrown (which is correct)
        assertTrue("Validation passed for zero string", true);
    }

    @Test
    public void testIsNulOrEmptyThrowException_WithBooleanString() {
        // Arrange
        String input = "true";
        String paramName = "flag";

        // Act - Should NOT throw exception
        StringValidator.isNulOrEmptyThrowException(input, paramName);
        
        // Assert - If we reach here, no exception was thrown (which is correct)
        assertTrue("Validation passed for boolean string", true);
    }

    // ========== Exception Type Tests ==========

    @Test
    public void testIsNulOrEmptyThrowException_ThrowsCorrectExceptionType() {
        // Arrange
        String input = null;
        String paramName = "testParam";

        // Act & Assert
        try {
            StringValidator.isNulOrEmptyThrowException(input, paramName);
            fail("Should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // Expected exception type
            assertTrue("Should be IllegalStateException", e instanceof IllegalStateException);
        } catch (Exception e) {
            fail("Should have thrown IllegalStateException, but threw: " + e.getClass().getName());
        }
    }

    @Test
    public void testIsNulOrEmptyThrowException_ExceptionMessageFormat() {
        // Arrange
        String input = "";
        String paramName = "emailAddress";

        // Act & Assert
        try {
            StringValidator.isNulOrEmptyThrowException(input, paramName);
            fail("Should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // Assert message format
            String message = e.getMessage();
            assertNotNull("Exception message should not be null", message);
            assertTrue("Message should start with 'Invalid input'", message.startsWith("Invalid input"));
            assertTrue("Message should contain parameter name", message.contains(paramName));
            assertTrue("Message should end with guidance", message.endsWith("Please provide a valid value."));
        }
    }
}

