package com.vluee.cloud.commons.config;

import com.vluee.cloud.commons.ddd.DomainEventPublisher;
import com.vluee.cloud.commons.ddd.SpringDomainEventPublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainEventConfig {

    @Bean
    @ConditionalOnMissingBean
    public DomainEventPublisher domainEventPublisher() {
        return new SpringDomainEventPublisher();
    }
}
