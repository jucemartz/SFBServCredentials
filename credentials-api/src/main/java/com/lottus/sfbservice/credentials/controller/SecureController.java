package com.lottus.sfbservice.credentials.controller;

import static com.lottus.sfbservice.credentials.enums.ApplicationErrorCode.ERROR_004;
import static com.lottus.sfbservice.credentials.exception.HandledException.ErrorType.INVALID_INPUT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lottus.sfbservice.credentials.config.ApplicationConfiguration;
import com.lottus.sfbservice.credentials.constants.ApplicationConstants;
import com.lottus.sfbservice.credentials.exception.HandledException;
import com.lottus.sfbservice.credentials.security.JwtPayload;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

public class SecureController {

    private ObjectMapper objectMapper;

    private ApplicationConfiguration appConfig;

    protected HttpServletRequest request;

    private final Base64.Decoder base64Decoder = Base64.getDecoder();

    @Autowired
    void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    void setAppConfig(ApplicationConfiguration appConfig) {
        this.appConfig = appConfig;
    }

    @Autowired
    void setHttpRequest(HttpServletRequest request) {
        this.request = request;
    }

    protected JwtPayload getJwtPayload() {
        String hashedPayload = this.request.getHeader("Authorization")
                .replace(ApplicationConstants.BEARER_VALUE_ON_HEADER, "").split("\\.")[1];
        String payload = new String(base64Decoder.decode(hashedPayload));

        try {
            return objectMapper.readValue(payload, JwtPayload.class);
        } catch (JsonProcessingException ex) {
            throw new HandledException(INVALID_INPUT, ERROR_004.getErrorId(), appConfig.getErrorMessage(ERROR_004), ex);
        }
    }
}
