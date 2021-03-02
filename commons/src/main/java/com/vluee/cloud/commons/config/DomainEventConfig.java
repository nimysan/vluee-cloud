package com.vluee.cloud.commons.config;

import com.vluee.cloud.commons.ddd.support.infrastructure.events.SpringDomainEventPublisher;
import com.vluee.cloud.commons.ddd.support.event.DomainEventPublisher;
import com.vluee.cloud.commons.ddd.support.event.DomainEventRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainEventConfig {

    @Bean
    @ConditionalOnMissingBean
    public DomainEventPublisher domainEventPublisher(DomainEventRepository domainEventRepository) {
        return new SpringDomainEventPublisher(domainEventRepository);
    }
}
