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
/**
 *
 */
package com.vluee.cloud.commons.ddd.support.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.event.DomainEventPublisherFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Slawek
 *
 */
@Component
@Scope("prototype")//created in domain factories, not in spring container, therefore we don't want eager creation
@MappedSuperclass
@Slf4j
public abstract class BaseAggregateRoot {

    public static enum AggregateStatus {
        ACTIVE, ARCHIVE
    }

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "idValue", column = @Column(name = "aggregateId", nullable = false))})
    protected AggregateId aggregateId;

    @Version
    private Long version;

    @Enumerated(EnumType.STRING)
    private AggregateStatus aggregateStatus = AggregateStatus.ACTIVE;

    public void markAsRemoved() {
        aggregateStatus = AggregateStatus.ARCHIVE;
    }

    public AggregateId getAggregateId() {
        return aggregateId;
    }

    public boolean isRemoved() {
        return aggregateStatus == AggregateStatus.ARCHIVE;
    }

    protected void domainError(String message) {
        throw new DomainOperationException(aggregateId, message);
    }

    protected void publish(Serializable event) {
        //get an elegant way
        DomainEventPublisherFactory.getPublisher().publish(event);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof BaseAggregateRoot) {
            BaseAggregateRoot other = (BaseAggregateRoot) obj;
            if (other.aggregateId == null)
                return false;
            return other.aggregateId.equals(aggregateId);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return aggregateId.hashCode();
    }
}
