package com.vluee.cloud.commons.ddd.annotations.event;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DomainEventHandler {
}
