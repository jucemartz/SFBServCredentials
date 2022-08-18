package com.lottus.sfbservice.credentials.contracts.common;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Arrays;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.apache.commons.validator.routines.EmailValidator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParameterValidationUtils {

    /**
     * Validates if parameter is null.
     *
     * @param input     The field value to validate.
     * @param fieldName The field name under validation.
     * @throws IllegalArgumentException if value is null.
     */
    public static void validateNotNull(Object input, String fieldName) {
        if (isNull(input)) {
            throw new IllegalArgumentException("Debe especificar un valor para " + fieldName);
        }
    }

    /**
     * Validates if parameter is Blank.
     *
     * @param input     The field value to validate.
     * @param fieldName The field name under validation.
     * @throws IllegalArgumentException if value is null or empty.
     */
    public static void validateNotBlank(String input, String fieldName) {
        validateNotNull(input, fieldName);
        if (isBlank(input)) {
            throw new IllegalArgumentException("El valor de " + fieldName + " no puede ser vacío");
        }
    }

    /**
     * Validates if the specified string is a valid email address.
     *
     * @param email     The field value to validate.
     * @param fieldName The field name under validation.
     * @throws IllegalArgumentException if value is null.
     */
    public static void validateEmailAddress(String email, String fieldName) {
        if (Boolean.FALSE
                .equals(EmailValidator.getInstance().isValid(email))) {
            throw new IllegalArgumentException(fieldName + " no es una dirección de correo electrónico válida");
        }
    }

    /**
     * Validates if the specified string is a valid enum value.
     *
     * @param input     The field value to validate.
     * @param enumType  The class of the enum.
     * @param fieldName The field name under validation.
     * @throws IllegalArgumentException if value is not a valid enum value.
     */
    public static <T extends Enum<?>> void validateEnumValueIgnoringCase(String input, Class<T> enumType,
                                                                         String fieldName) {
        if (Arrays.stream(enumType.getEnumConstants()).noneMatch(e -> e.name().equalsIgnoreCase(input))) {
            throw new IllegalArgumentException(fieldName + " tiene un valor no permitido");
        }
    }

}
