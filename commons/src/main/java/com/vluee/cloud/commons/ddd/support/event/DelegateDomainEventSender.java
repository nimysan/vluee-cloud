package com.vluee.cloud.commons.ddd.support.event;

import com.vluee.cloud.commons.ddd.support.infrastructure.events.handler.EventHandler;

public interface DelegateDomainEventSender {

    public void sendEvent(SimpleDomainEvent event);

    void add(EventHandler handler);
}
