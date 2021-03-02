package com.vluee.cloud.commons.ddd.support.infrastructure.events;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.event.DomainEventPublisher;
import com.vluee.cloud.commons.ddd.support.event.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.event.SimpleDomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;

@Slf4j
public class SpringDomainEventPublisher implements DomainEventPublisher, ApplicationContextAware {

    public SpringDomainEventPublisher(DomainEventRepository domainEventRepository) {
        this.domainEventRepository = domainEventRepository;
    }

    private ApplicationContext applicationContext;

    private final DomainEventRepository domainEventRepository;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = this.applicationContext;
    }

    @Override
    public void publish(Serializable event) {
        SimpleDomainEvent simpleDomainEvent = new SimpleDomainEvent(AggregateId.generate(), event.getClass().getCanonicalName(), DateUtil.date(), false, JSONUtil.toJsonStr(event));
        domainEventRepository.save(simpleDomainEvent);
        log.info("Event is saved ... {}", simpleDomainEvent);
    }
}
