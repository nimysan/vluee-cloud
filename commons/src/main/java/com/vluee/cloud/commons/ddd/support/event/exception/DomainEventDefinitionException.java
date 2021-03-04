package com.vluee.cloud.commons.ddd.support.event.exception;

/**
 * 无法从EventStore中恢复事件本身带来的异常
 */
public class DomainEventDefinitionException extends RuntimeException {
    public DomainEventDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainEventDefinitionException(String message) {
        super(message);
    }
}
