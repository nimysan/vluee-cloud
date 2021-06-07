package com.vluee.cloud.commons.ddd.support.infrastructure.events.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface DomainEventClient {

    public static final String DOMAIN_EVENT_TOPIC = "vluee_cloud_domain_events";

    @Output(DomainEventClient.DOMAIN_EVENT_TOPIC)
    MessageChannel output();

}
