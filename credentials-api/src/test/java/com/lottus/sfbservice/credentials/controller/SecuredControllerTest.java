package com.lottus.sfbservice.credentials.controller;

import static com.lottus.sfbservice.credentials.constants.ApplicationConstants.BEARER_VALUE_ON_HEADER;
import static com.lottus.sfbservice.credentials.constants.ApplicationConstants.HEADER_SERVICE_ID;

import com.lottus.sfbservice.credentials.config.ApplicationConfiguration;

import org.springframework.http.HttpHeaders;

public abstract class SecuredControllerTest {

    protected final String EMAIL_ON_ACCESS_TOKEN = "any@email.com";
    protected final String FULL_NAME_ON_ACCESS_TOKEN = "Full Name";
    protected final Long STUDENT_ID_ON_ACCESS_TOKEN = 346901L;

    private final String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9."
            + "eyJzdHVkZW50SWQiOiIzNDY5MDEiLCJuYW1lIjoiRnVsbCBOYW1lIiwiZW1haWwiOiJhbnlAZW1haWwuY29tIiwiaWF0IjoxNjMzNDYy"
            + "NTk0LCJleHAiOjE2MzM0NjM0OTR9."
            + "JiTewDj39pocuSJRFE8vaXpb3rDitCpuPUb5Hd1DUFE";

    protected abstract ApplicationConfiguration getAppConfig();

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_SERVICE_ID, getAppConfig().getApiId());
        headers.add(HttpHeaders.AUTHORIZATION, BEARER_VALUE_ON_HEADER + ACCESS_TOKEN);

        return headers;
    }

}
