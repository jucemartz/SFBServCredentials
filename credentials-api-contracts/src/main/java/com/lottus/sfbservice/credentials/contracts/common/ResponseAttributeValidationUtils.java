package com.lottus.sfbservice.credentials.contracts.common;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class ResponseAttributeValidationUtils {

    private ResponseAttributeValidationUtils() {
    }

    /**
     * Validates if attribute is null.
     *
     * @param input     The field value to validate.
     * @param fieldName The field name under validation.
     * @throws IllegalArgumentException if value is null.
     */
    public static void validateNotNull(Object input, String fieldName) {
        if (isNull(input)) {
            throw new IllegalStateException(fieldName + " has an unexpected null value");
        }
    }

    /**
     * Validates if attribute is Blank.
     *
     * @param input     The field value to validate.
     * @param fieldName The field name under validation.
     * @throws IllegalArgumentException if value is null or blank.
     */
    public static void validateNotBlank(String input, String fieldName) {
        validateNotNull(input, fieldName);
        if (isBlank(input)) {
            throw new IllegalStateException(fieldName + " has an unexpected blank value");
        }
    }

}
