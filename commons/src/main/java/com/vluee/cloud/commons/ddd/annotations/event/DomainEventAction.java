package com.vluee.cloud.commons.ddd.annotations.event;

import com.vluee.cloud.commons.ddd.support.infrastructure.events.stream.DomainEventClient;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.MessageMapping;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@MessageMapping
@Documented
@StreamListener(DomainEventClient.INPUT_TOPIC)
public @interface DomainEventAction {
}
