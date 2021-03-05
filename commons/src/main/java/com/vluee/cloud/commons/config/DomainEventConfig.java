package com.vluee.cloud.commons.config;

import com.vluee.cloud.commons.ddd.support.event.DelegateDomainEventSender;
import com.vluee.cloud.commons.ddd.support.event.DomainEventFactory;
import com.vluee.cloud.commons.ddd.support.event.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.event.publisher.DomainEventCompensationHandler;
import com.vluee.cloud.commons.ddd.support.event.publisher.DomainEventPublisher;
import com.vluee.cloud.commons.ddd.support.event.serialize.DomainEventSerializer;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.DefaultDomainEventSender;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.EventListenerBeanPostProcessor;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.JacksonDomainEventSerializer;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.SimpleDomainEventPublisher;
import com.vluee.cloud.commons.distributedlock.MutexLockFactory;
import com.vluee.cloud.commons.distributedlock.MutexLockRepository;
import com.vluee.cloud.commons.distributedlock.mem.InMemMutexLockRepository;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class DomainEventConfig implements ApplicationRunner, ApplicationContextAware {

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

    @Bean
    @ConditionalOnMissingBean
    public DomainEventCompensationHandler domainEventCompensationHandler(MutexLockFactory mutexLockFactory, DomainEventRepository domainEventRepository, DelegateDomainEventSender delegateDomainEventSender) {
        return new DomainEventCompensationHandler(delegateDomainEventSender, domainEventRepository, mutexLockFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    public MutexLockFactory mutexLockFactory(MutexLockRepository mutexLockRepository) {
        return new MutexLockFactory(mutexLockRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public MutexLockRepository mutexLockRepository() {
        return new InMemMutexLockRepository();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        DomainEventCompensationHandler bean = applicationContext.getBean(DomainEventCompensationHandler.class);
        bean.startCompensate(eventLockIdentifier());
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected String eventLockIdentifier() {
        return "sass";
    }
}
