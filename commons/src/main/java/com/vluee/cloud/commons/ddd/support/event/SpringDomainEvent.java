package com.vluee.cloud.commons.ddd.support.event;

import com.vluee.cloud.commons.ddd.support.event.DomainEvent;
import org.springframework.context.ApplicationEvent;

public class SpringDomainEvent extends ApplicationEvent implements DomainEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public SpringDomainEvent(Object source) {
        super(source);
    }
}
