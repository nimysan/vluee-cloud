package com.vluee.cloud.commons.ddd.annotations.event;

import com.vluee.cloud.commons.ddd.support.event.DomainEvent;
import org.springframework.kafka.annotation.KafkaListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@KafkaListener(topics = DomainEvent.DOMAIN_EVENT_TOPIC, groupId = "group.1")
public @interface KafkaDomainEventListener {
}
