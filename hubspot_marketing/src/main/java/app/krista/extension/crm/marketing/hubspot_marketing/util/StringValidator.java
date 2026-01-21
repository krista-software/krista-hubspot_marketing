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
 * Utility class for string validation.
 * Provides common validation methods with user-friendly error messages.
 */
public class StringValidator {

    private StringValidator() {
        // Private constructor to prevent instantiation
    }

    /**
     * Validates that a string is not null or empty.
     *
     * @param input The string to validate
     * @param paramName The parameter name for error messaging
     * @throws IllegalStateException if the input is null or empty
     */
    public static void isNulOrEmptyThrowException(String input, String paramName) {
        if (input == null || input.isEmpty()) {
            throw new IllegalStateException(ErrorMessages.nullOrEmpty(paramName));
        }
    }
}
