package com.vluee.cloud.commons.ddd.support.event.publisher;

import cn.hutool.core.thread.ThreadUtil;
import com.vluee.cloud.commons.ddd.support.event.DelegateDomainEventSender;
import com.vluee.cloud.commons.ddd.support.event.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.event.SimpleDomainEvent;
import com.vluee.cloud.commons.distributedlock.MutexLockFactory;
import com.vluee.cloud.commons.distributedlock.MutexLockLockException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 领域事件补偿器，读取未成功发布的事件并继续发布
 */
@Slf4j
@AllArgsConstructor
public class DomainEventCompensationHandler {

    private final DelegateDomainEventSender delegateDomainEventSender;
    private final DomainEventRepository domainEventRepository;
    private final MutexLockFactory mutexLockFactory;

    public void startCompensate(String resourceIdentifier) {
        ThreadUtil.execAsync(() -> {
            for (; ; ) {
                try {
                    mutexLockFactory.workWithLock("domain-events-" + resourceIdentifier, TimeUnit.MILLISECONDS, 400, () -> {
                        //distributeLock.lock(5 * 1000);
                        log.info("Fetch unpublished events and resend them");
                        Collection<SimpleDomainEvent> domainEvents = fetchUnPublishedEvents();
                        if (domainEvents == null || domainEvents.isEmpty()) {
                            ThreadUtil.safeSleep(10 * 1000);//暂停10s不处理
                        } else {
                            for (SimpleDomainEvent domainEvent : domainEvents) {
                                sendEvent(domainEvent);
                            }
                        }
                    });

                } catch (MutexLockLockException e) {
                    ThreadUtil.safeSleep(TimeUnit.SECONDS.toMillis(10));//暂停10s不做任何事情
                    log.warn("Failed to send events with exception.", e);
                }
            }
        }, true);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void sendEvent(SimpleDomainEvent simpleDomainEvent) {
        try {
            delegateDomainEventSender.sendEvent(simpleDomainEvent);
        } catch (Throwable e) {
            log.error("Sent domain event get error", e);
        } finally {

        }
    }

    @Transactional
    Collection<SimpleDomainEvent> fetchUnPublishedEvents() {
        return domainEventRepository.fetchNonPublishEvents();
    }
}
