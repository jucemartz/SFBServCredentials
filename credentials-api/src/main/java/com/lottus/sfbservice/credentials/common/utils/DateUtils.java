package com.lottus.sfbservice.credentials.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    private static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy";

    /**
     * Convert a Date object into a String based on dd-MM-yyyy format.
     *
     * @param date The date value.
     * @return The String representation of the input Date.
     */
    public static String dateToString(Date date) {
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
    }

}
