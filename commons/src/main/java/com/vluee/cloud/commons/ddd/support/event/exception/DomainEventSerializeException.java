package com.vluee.cloud.commons.ddd.support.event.exception;

import lombok.Getter;

import java.io.Serializable;

/**
 * 事件序列化异常
 */
public class DomainEventSerializeException extends RuntimeException {
    @Getter
    private Serializable domainEvent;

    public DomainEventSerializeException(Serializable event, Throwable cause) {
        super(cause);
        this.domainEvent = event;
    }
}
