/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vluee.cloud.uams.infrastructure.events.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.domain.DomainEventPublisher;
import com.vluee.cloud.commons.ddd.support.domain.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.domain.SimpleDomainEvent;
import com.vluee.cloud.uams.infrastructure.events.impl.handlers.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
@AllArgsConstructor
public class SimpleEventPublisher implements DomainEventPublisher {

    private final DomainEventRepository domainEventRepository;

    private Set<EventHandler> eventHandlers = new HashSet<EventHandler>();

    public void registerEventHandler(EventHandler handler) {
        eventHandlers.add(handler);
        // new SpringEventHandler(eventType, beanName, method));
    }

    @Override
    public void publish(Serializable event) {
        //Normally we will save the domain event always
        SimpleDomainEvent simpleDomainEvent = convertEvent(event);
        domainEventRepository.save(simpleDomainEvent);
        doPublish(simpleDomainEvent.getAggregateId(), event);
    }

    private SimpleDomainEvent convertEvent(Serializable event) {
        SimpleDomainEvent simpleDomainEvent = new SimpleDomainEvent(AggregateId.generate(), event.getClass().getCanonicalName(), DateUtil.date(), true, JSONUtil.toJsonStr(event));
        return simpleDomainEvent;
    }

    protected void doPublish(AggregateId persistEventId, Object event) {
        for (EventHandler handler : new ArrayList<EventHandler>(eventHandlers)) {
            if (handler.canHandle(event)) {
                try {
                    handler.handle(event);
                } catch (Exception e) {
                    log.error("event handling error", e);
                }
            }
        }
    }
}
