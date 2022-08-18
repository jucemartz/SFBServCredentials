package com.lottus.sfbservice.credentials.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApplicationErrorCode {

    ERROR_001("001"),
    ERROR_002("002"),
    ERROR_003("003"),
    ERROR_004("004"),

    ERROR_100("100"),
    ERROR_101("101"),
    ERROR_102("102"),
    ERROR_103("103"),
    ERROR_104("104"),
    ERROR_105("105"),
    ERROR_106("106"),
    ERROR_107("107"),
    ERROR_108("108"),

    ERROR_501("501"),
    ERROR_502("502"),
    ERROR_503("503"),
    ERROR_504("504");

    private final String errorId;

    public String getErrorId() {
        return errorId;
    }
}
