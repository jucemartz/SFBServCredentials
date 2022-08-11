package com.lottus.sfbservice.credentials.exception;

import lombok.Getter;

@Getter
public class HandledException extends RuntimeException {

    public enum ErrorType {
        DATA_NOT_FOUND,
        INTERNAL_ERROR,
        INVALID_INPUT,
        INVALID_STATE,
        AUTHORIZATION_ERROR,
    }

    protected final ErrorType errorType;
    protected final String errorCode;


    /**
     * Creates a new instance of HandledException.
     *
     * @param errorType The errorType value.
     * @param errorCode The errorCode value.
     * @param errorMessage The errorMessage value.
     */
    public HandledException(ErrorType errorType, String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorType = errorType;
        this.errorCode = errorCode;
    }

    /**
     * Creates a new instance of HandledException.
     *
     * @param errorType The errorType value.
     * @param errorCode The errorCode value.
     * @param errorMessage The errorMessage value.
     * @param rootCause The rootCause exception.
     */
    public HandledException(ErrorType errorType, String errorCode, String errorMessage, Throwable rootCause) {
        super(errorMessage, rootCause);
        this.errorType = errorType;
        this.errorCode = errorCode;
    }

}
