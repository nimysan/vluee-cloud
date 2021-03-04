/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vluee.cloud.commons.ddd.support.infrastructure.events;

import com.vluee.cloud.commons.ddd.annotations.event.EventListener;
import com.vluee.cloud.commons.ddd.support.event.publisher.DomainEventPublisher;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.handler.AsynchronousEventHandler;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.handler.EventHandler;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.handler.SpringEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

/**
 * Registers spring beans methods as event handlers in spring event publisher
 * (if needed).
 */
@Slf4j
public class EventListenerBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

    private BeanFactory beanFactory;
    private DomainEventPublisher eventPublisher;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        for (Method method : bean.getClass().getMethods()) {
            EventListener listenerAnnotation = method.getAnnotation(EventListener.class);
            if (listenerAnnotation == null) {
                continue;
            }
            Class<?> eventType = method.getParameterTypes()[0];
            EventHandler handler;
            if (listenerAnnotation.asynchronous()) {
                //TODO just a temporary fake impl
                handler = new AsynchronousEventHandler(eventType, beanName, method, beanFactory);
                //TODO add to some queue
            } else {
                handler = new SpringEventHandler(eventType, beanName, method, beanFactory);
            }
            eventPublisher.registerEventHandler(handler);
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // do nothing
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        eventPublisher = beanFactory.getBean(DomainEventPublisher.class);
    }
}
