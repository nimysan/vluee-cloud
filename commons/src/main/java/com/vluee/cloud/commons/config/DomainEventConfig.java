package com.vluee.cloud.commons.config;

import com.vluee.cloud.commons.ddd.support.event.DelegateDomainEventSender;
import com.vluee.cloud.commons.ddd.support.event.DomainEventFactory;
import com.vluee.cloud.commons.ddd.support.event.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.event.publisher.DomainEventPublisher;
import com.vluee.cloud.commons.ddd.support.event.serialize.DomainEventSerializer;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.DefaultDomainEventSender;
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
    public DomainEventPublisher domainEventPublisher(DomainEventRepository domainEventRepository, DomainEventSerializer domainEventSerializer, DomainEventFactory domainEventFactory, DelegateDomainEventSender delegateDomainEventSender) {
        return new SimpleDomainEventPublisher(domainEventRepository, domainEventSerializer, domainEventFactory, delegateDomainEventSender);
    }

    @Bean
    @ConditionalOnMissingBean
    @DependsOn
    public EventListenerBeanPostProcessor eventListenerBeanPostProcessor() {
        return new EventListenerBeanPostProcessor();
    }

    @Bean
    public DomainEventFactory domainEventFactory(DomainEventSerializer domainEventSerializer) {
        return new DomainEventFactory(domainEventSerializer);
    }

    @Bean
    @ConditionalOnMissingBean
    public DelegateDomainEventSender domainEventSender(DomainEventRepository domainEventRepository) {
        return new DefaultDomainEventSender(domainEventRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public DomainEventSerializer domainEventSerializer() {
        return new JacksonDomainEventSerializer();
    }

}
