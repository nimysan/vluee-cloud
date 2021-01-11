package com.vluee.cloud.gateway.core.events;

import org.springframework.context.ApplicationEvent;

public class RefreshAllEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public RefreshAllEvent(Object source) {
        super(source);
    }
}
