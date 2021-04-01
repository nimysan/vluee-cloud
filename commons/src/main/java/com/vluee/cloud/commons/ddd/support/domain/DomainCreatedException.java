package com.vluee.cloud.commons.ddd.support.domain;

public class DomainCreatedException extends RuntimeException {

    public DomainCreatedException() {
    }

    public DomainCreatedException(String message) {
        super(message);
    }

    public DomainCreatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainCreatedException(Throwable cause) {
        super(cause);
    }

    public DomainCreatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
