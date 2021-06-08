package com.vluee.cloud.commons.ddd.support.infrastructure.events.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface DomainEventClient {

    public static final String DOMAIN_EVENT_TOPIC = "vluee_cloud_domain_events";

    public static final String INPUT_TOPIC = "domain_events_input";

    @Input(DomainEventClient.INPUT_TOPIC)
    SubscribableChannel input();

    @Output(DomainEventClient.DOMAIN_EVENT_TOPIC)
    MessageChannel output();

}
