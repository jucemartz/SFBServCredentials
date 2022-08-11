package com.lottus.sfbservice.credentials.security;

import static com.lottus.sfbservice.credentials.constants.ApplicationConstants.BEARER_VALUE_ON_HEADER;
import static com.lottus.sfbservice.credentials.constants.ApplicationConstants.HEADER_SERVICE_ID;
import static com.lottus.sfbservice.credentials.enums.ApplicationErrorCode.ERROR_001;
import static com.lottus.sfbservice.credentials.enums.ApplicationErrorCode.ERROR_002;
import static com.lottus.sfbservice.credentials.enums.ApplicationErrorCode.ERROR_003;
import static com.lottus.sfbservice.credentials.enums.ApplicationErrorCode.ERROR_501;
import static com.lottus.sfbservice.credentials.exception.HandledException.ErrorType.AUTHORIZATION_ERROR;
import static com.lottus.sfbservice.credentials.exception.HandledException.ErrorType.INTERNAL_ERROR;
import static com.lottus.sfbservice.credentials.exception.HandledException.ErrorType.INVALID_INPUT;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import com.lottus.sfbservice.credentials.config.ApplicationConfiguration;
import com.lottus.sfbservice.credentials.exception.HandledException;

import feign.FeignException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    @Autowired
    private ApplicationConfiguration appConfig;

    @Autowired
    private AuthorizationServerRestClient authorizationServerRestClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String serviceId = request.getHeader(HEADER_SERVICE_ID);
        if (isNull(serviceId)) {
            throw new HandledException(INVALID_INPUT, ERROR_001.getErrorId(), appConfig.getErrorMessage(ERROR_001));
        }
        if (!appConfig.getApiId().equals(serviceId)) {
            throw new HandledException(INVALID_INPUT, ERROR_002.getErrorId(), appConfig.getErrorMessage(ERROR_002));
        }

        validateAccessToken(request.getHeader(HttpHeaders.AUTHORIZATION));

        return true;
    }

    private void validateAccessToken(String bearer) {
        if (isBlank(bearer) || !bearer.contains(BEARER_VALUE_ON_HEADER)) {
            throw new HandledException(INVALID_INPUT, ERROR_003.getErrorId(), appConfig.getErrorMessage(ERROR_003),
                    new Exception("Invalid or missing Authorization Header"));
        }
        String accessToken = bearer.replace(BEARER_VALUE_ON_HEADER, "");
        try {
            authorizationServerRestClient.validateAccessToken(accessToken);
        } catch (FeignException ex) {
            if (HttpStatus.FORBIDDEN.value() == ex.status()) {
                throw new HandledException(AUTHORIZATION_ERROR, ERROR_003.getErrorId(),
                        appConfig.getErrorMessage(ERROR_003), ex);
            } else {
                throw new HandledException(INTERNAL_ERROR, ERROR_501.getErrorId(),
                        appConfig.getErrorMessage(ERROR_501), ex);
            }
        }
    }
}
