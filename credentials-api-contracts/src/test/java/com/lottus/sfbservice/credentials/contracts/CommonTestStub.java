package com.lottus.sfbservice.credentials.contracts;

import com.github.javafaker.Faker;

import java.text.MessageFormat;
import java.util.Locale;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonTestStub {

    public static final Faker FAKER = new Faker(new Locale("en-GB"));

    public static final String VALUE_NOT_SPECIFIED_MESSAGE = "Debe especificar un valor para {0}";

    public static String getValueNotSpecifiedErrorMessage(String fieldName) {
        return MessageFormat.format(VALUE_NOT_SPECIFIED_MESSAGE, fieldName);
    }
}
