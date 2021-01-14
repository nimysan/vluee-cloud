package com.vluee.cloud.commons.ddd;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringDomainEventPublisher implements DomainEventPublisher, ApplicationContextAware {

    private ApplicationContext applicationContext;

    //TODO
    @Override
    public void publish(DomainEvent domainEvent) {
        this.applicationContext.publishEvent(new SpringDomainEvent(applicationContext));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = this.applicationContext;
    }
}
