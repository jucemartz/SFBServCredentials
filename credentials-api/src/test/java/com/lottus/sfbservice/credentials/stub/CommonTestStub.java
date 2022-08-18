package com.lottus.sfbservice.credentials.stub;

import com.github.javafaker.Faker;

import java.util.Locale;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonTestStub {

    public static final Faker FAKER = new Faker(new Locale("en-GB"));

    public static final String CURP_MASK = "????####??????##";


    public static Long randomPositiveLong() {
        return FAKER.random().nextLong(100000L);
    }

}
