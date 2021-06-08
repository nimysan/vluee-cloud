package com.vluee.cloud.commons.ddd.support.event.handler;

import com.vluee.cloud.commons.ddd.support.infrastructure.events.handler.EventHandler;

import java.util.HashSet;
import java.util.Set;

public class DefaultDomainEventHandler {

    private Set<EventHandler> eventHandlers = new HashSet<EventHandler>();

    public void registerHandler(EventHandler eventHandler) {
        if (!eventHandlers.contains(eventHandler)) {
            eventHandlers.add(eventHandler);
        }
    }
}
