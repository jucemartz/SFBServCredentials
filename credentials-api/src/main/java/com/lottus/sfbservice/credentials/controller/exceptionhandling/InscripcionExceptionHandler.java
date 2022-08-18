package com.lottus.sfbservice.credentials.controller.exceptionhandling;

import static com.lottus.sfbservice.credentials.enums.ApplicationErrorCode.ERROR_100;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.lottus.sfbservice.credentials.common.ApplicationHelper;
import com.lottus.sfbservice.credentials.contracts.common.response.ApiErrorResponse;
import com.lottus.sfbservice.credentials.contracts.common.response.ApiResponseError;
import com.lottus.sfbservice.credentials.contracts.common.response.ApiResponseStatus;
import com.lottus.sfbservice.credentials.exception.HandledException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class InscripcionExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(InscripcionExceptionHandler.class);

    @Autowired
    ApplicationHelper appHelper;

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<ApiErrorResponse> handleIllegalArgumentExceptions(
            HttpServletRequest httpServletRequest, IllegalArgumentException ex) {
        this.logger.error("IllegalArgumentException handled in request", ex);

        ApiErrorResponse responseBody = ApiErrorResponse.builder()
                .withService(appHelper.getApiResponseService())
                .withStatus(ApiResponseStatus.builder().withId(ERROR_100.getErrorId())
                        .withInfo(ex.getMessage())
                        .build())
                .withError(ApiResponseError.builder().withId(String.valueOf(BAD_REQUEST.value()))
                        .withInfo("No trace for invalid input parameter")
                        .build())
                .build();

        return ResponseEntity.status(BAD_REQUEST).body(responseBody);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableExceptions(
            HttpServletRequest httpServletRequest, HttpMessageNotReadableException ex) {
        this.logger.error("HttpMessageNotReadableException handled in request", ex);

        String errorCode = String.valueOf(BAD_REQUEST.value());
        String errorMessage = nonNull(ex.getRootCause()) ? ex.getRootCause().getMessage()
                : ex.getMessage();

        return ResponseEntity.status(BAD_REQUEST)
                .body(buildUnexpectedErrorResponseBody(ex, errorCode, errorMessage));
    }


    @ExceptionHandler({HandledException.class})
    protected ResponseEntity<ApiErrorResponse> handleServiceException(HttpServletRequest httpServletRequest,
                                                                      HandledException ex) {
        this.logger.error(ex.getMessage(), ex);

        HttpStatus httpResponseStatus;
        switch (ex.getErrorType()) {
            case DATA_NOT_FOUND:
                httpResponseStatus = NOT_FOUND;
                break;
            case INVALID_STATE:
            case INVALID_INPUT:
                httpResponseStatus = BAD_REQUEST;
                break;
            case AUTHORIZATION_ERROR:
                httpResponseStatus = FORBIDDEN;
                break;
            case INTERNAL_ERROR:
            default:
                httpResponseStatus = INTERNAL_SERVER_ERROR;
                break;
        }

        String traceError = nonNull(ex.getCause()) ? ex.getCause().getMessage() : "No trace for handled exception";
        ApiErrorResponse responseBody = ApiErrorResponse.builder()
                .withService(appHelper.getApiResponseService())
                .withStatus(ApiResponseStatus.builder().withId(ex.getErrorCode()).withInfo(ex.getMessage())
                        .build())
                .withError(ApiResponseError.builder().withId(String.valueOf(httpResponseStatus.value()))
                        .withInfo(traceError).build())
                .build();

        return ResponseEntity.status(httpResponseStatus).body(responseBody);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ApiErrorResponse> handleAllExceptions(HttpServletRequest httpServletRequest,
                                                                   Exception ex) {
        this.logger.error(ex.getMessage(), ex);

        String errorCode = String.valueOf(INTERNAL_SERVER_ERROR.value());
        String errorMessage = "Ocurrió un error inesperado";

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(buildUnexpectedErrorResponseBody(ex, errorCode, errorMessage));
    }

    private ApiErrorResponse buildUnexpectedErrorResponseBody(Exception ex, String errorCode, String errorMessage) {
        StringBuilder detailedErrorMessage = new StringBuilder().append(ex.getMessage());
        Throwable cause = ex.getCause();
        while (nonNull(cause)) {
            detailedErrorMessage.append(" -> ").append(cause.getMessage());
            cause = cause.getCause();
        }
        return ApiErrorResponse.builder()
                .withService(appHelper.getApiResponseService())
                .withStatus(ApiResponseStatus.builder().withId(errorCode).withInfo(errorMessage)
                        .build())
                .withError(ApiResponseError.builder().withId(errorCode).withInfo(ex.toString()).build())
                .build();
    }
}
