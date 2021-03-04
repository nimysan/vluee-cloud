package com.vluee.cloud.commons.ddd.support.infrastructure.events;

import com.vluee.cloud.commons.ddd.support.event.DelegateDomainEventSender;
import com.vluee.cloud.commons.ddd.support.event.DomainEventFactory;
import com.vluee.cloud.commons.ddd.support.event.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.event.SimpleDomainEvent;
import com.vluee.cloud.commons.ddd.support.event.publisher.DomainEventPublisher;
import com.vluee.cloud.commons.ddd.support.event.serialize.DomainEventSerializer;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.handler.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Slf4j
public class SimpleDomainEventPublisher implements DomainEventPublisher, ApplicationContextAware {

    private final DomainEventRepository domainEventRepository;
    private final DomainEventSerializer domainEventSerializer;
    private final DomainEventFactory domainEventFactory;
    private final DelegateDomainEventSender delegateDomainEventSender;

    public SimpleDomainEventPublisher(@NotNull DomainEventRepository domainEventRepository, @NotNull final DomainEventSerializer domainEventSerializer, @NotNull final DomainEventFactory domainEventFactory, final DelegateDomainEventSender delegateDomainEventSender) {
        this.domainEventRepository = domainEventRepository;
        this.domainEventSerializer = domainEventSerializer;
        this.domainEventFactory = domainEventFactory;
        this.delegateDomainEventSender = delegateDomainEventSender;
    }

    private ApplicationContext applicationContext;

    public void registerEventHandler(EventHandler handler) {
        delegateDomainEventSender.add(handler);
    }

    @Override
    public String serializeEvent(Serializable event) {
        return this.domainEventSerializer.serialize(event);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = this.applicationContext;
    }

    /**
     * 将领域事件保存下来，并由 DelegateDomainEventSender 去真实发布事件
     *
     * @param event
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY) //领域事件的存储须与领域在一个事务内存储，确保强一致性。
    public void publish(Serializable event) {
        final SimpleDomainEvent simpleDomainEvent = domainEventFactory.createFrom(event);
        domainEventRepository.save(simpleDomainEvent);

        //事件存储完成的回调 - 真实发布
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    try {
                        //确保任何异常都能够被catch, 否则会导致无法将connection资源返回来池子
                        delegateDomainEventSender.sendEvent(simpleDomainEvent);
                    } catch (Throwable e) {
                        log.error("Failed to handle event", e);
                    }
                }
            });
        }
    }
}
