package com.lottus.sfbservice.credentials.common.utils;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberUtils {


    /**
     * Evaluates if the first number is lower or equals to the second number.
     *
     * @param first The first number to compare with.
     * @param second The second number to compare to.
     * @return TRUE if first number is lower or equal than second number, otherwise FALSE.
     */
    public static boolean isEqualOrLowerThan(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) <= 0;
    }

}
