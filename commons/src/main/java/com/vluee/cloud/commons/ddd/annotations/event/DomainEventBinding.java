package com.vluee.cloud.commons.ddd.annotations.event;

import com.vluee.cloud.commons.ddd.support.infrastructure.events.stream.DomainEventClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableBinding(DomainEventClient.class)
public @interface DomainEventBinding {
}
