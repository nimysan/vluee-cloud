package com.vluee.cloud.commons.ddd.annotations.event;

import com.vluee.cloud.commons.ddd.support.infrastructure.events.stream.DomainEventClient;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.MessageMapping;

import java.lang.annotation.*;


/**
 * 一个具体的处理消息的方法
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@MessageMapping
@Documented
@StreamListener(DomainEventClient.INPUT_TOPIC)
public @interface DomainEventAction {
}
