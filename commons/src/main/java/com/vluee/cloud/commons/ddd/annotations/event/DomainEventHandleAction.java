package com.vluee.cloud.commons.ddd.annotations.event;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DomainEventHandleAction {
    boolean asynchronous();
}
