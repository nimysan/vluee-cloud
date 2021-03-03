package com.vluee.cloud.commons.config;

import com.vluee.cloud.commons.ddd.support.event.DomainEventPublisher;
import com.vluee.cloud.commons.ddd.support.event.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.EventListenerBeanPostProcessor;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.SimpleDomainEventPublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class DomainEventConfig {

    @Bean
    @ConditionalOnMissingBean
    public DomainEventPublisher domainEventPublisher(DomainEventRepository domainEventRepository) {
        SimpleDomainEventPublisher simpleDomainEventPublisher = new SimpleDomainEventPublisher(domainEventRepository);
        return simpleDomainEventPublisher;
    }

    @Bean
    @DependsOn
    public EventListenerBeanPostProcessor eventListenerBeanPostProcessor() {
        return new EventListenerBeanPostProcessor();
    }
}
