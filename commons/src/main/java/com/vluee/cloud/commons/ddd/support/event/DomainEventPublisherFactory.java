package com.vluee.cloud.commons.ddd.support.event;

import com.vluee.cloud.commons.ddd.support.event.publisher.DomainEventPublisher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 构造domain event publisher的工厂
 */
public class DomainEventPublisherFactory implements ApplicationContextAware {

    private static DomainEventPublisher STATIC_DOMAIN_EVENT_PUBLISHER;

    private static ApplicationContext applicationContext;

    public static synchronized DomainEventPublisher getPublisher() {
        if (STATIC_DOMAIN_EVENT_PUBLISHER == null) {
            STATIC_DOMAIN_EVENT_PUBLISHER = applicationContext.getBean(DomainEventPublisher.class);
        }
        return STATIC_DOMAIN_EVENT_PUBLISHER;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
