package com.lottus.sfbservice.credentials.exception;

public class ServiceException extends HandledException {

    public ServiceException(ErrorType errorType, String errorCode, String errorMessage) {
        super(errorType, errorCode, errorMessage);
    }
}
