package com.vluee.cloud.commons.ddd.support.event.exception;

import lombok.Getter;

/**
 * 事件范序列化异常
 */
public class DomainEventReadException extends RuntimeException {

    @Getter
    private String domainEventContent;

    @Getter
    private Class klass;

    public DomainEventReadException(String content, Class klass, Throwable cause) {
        super(cause);
        this.domainEventContent = content;
        this.klass = klass;
    }
}
