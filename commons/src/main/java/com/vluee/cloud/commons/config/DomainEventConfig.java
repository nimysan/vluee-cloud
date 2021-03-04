package com.vluee.cloud.commons.config;

import com.vluee.cloud.commons.ddd.support.event.publisher.DomainEventPublisher;
import com.vluee.cloud.commons.ddd.support.event.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.event.serialize.DomainEventSerializer;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.EventListenerBeanPostProcessor;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.JacksonDomainEventSerializer;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.SimpleDomainEventPublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class DomainEventConfig {

    @Bean
    @ConditionalOnMissingBean
    public DomainEventPublisher domainEventPublisher(DomainEventRepository domainEventRepository, DomainEventSerializer domainEventSerializer) {
        SimpleDomainEventPublisher simpleDomainEventPublisher = new SimpleDomainEventPublisher(domainEventRepository, domainEventSerializer);
        return simpleDomainEventPublisher;
    }

    @Bean
    @ConditionalOnMissingBean
    @DependsOn
    public EventListenerBeanPostProcessor eventListenerBeanPostProcessor() {
        return new EventListenerBeanPostProcessor();
    }

    @Bean
    @ConditionalOnMissingBean
    public DomainEventSerializer domainEventSerializer() {
        return new JacksonDomainEventSerializer();
    }
}
