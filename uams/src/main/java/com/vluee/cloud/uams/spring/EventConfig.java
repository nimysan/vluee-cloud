package com.vluee.cloud.uams.spring;

import com.vluee.cloud.commons.config.DomainEventConfig;
import com.vluee.cloud.commons.distributedlock.MutexLockRepository;
import com.vluee.cloud.commons.distributedlock.db.DatabaseMutexLockRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig extends DomainEventConfig {

    @Bean
    public MutexLockRepository mutexLockRepository() {
        return new DatabaseMutexLockRepository();
    }

    @Override
    protected String eventLockIdentifier() {
        return "saas-uams";
    }
}
