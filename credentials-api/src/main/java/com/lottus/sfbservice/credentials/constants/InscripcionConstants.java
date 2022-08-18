package com.lottus.sfbservice.credentials.constants;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InscripcionConstants {

    public static final List<String> NON_CANCELLABLE_CATEGORIES = List.of("FEE", "TUI", "INS", "COM", "CSH", "CUR",
            "DIP", "NTC", "CNT");

    public static final String TRANSACTION_SOURCE_CODE = "T";

    public static final String FEED_TRANSACTION_STATUS = "Y";

    public static final int VIRTUAL_CAMPUS_SESSION_NUMBER = 0;

    public static final String UTC_CREDIT = "4159";

    public static final String UTC_DEBIT = "4160";

    public static final String CETC_CREDIT = "4157";

    public static final String CETC_DEBIT = "4158";

}
