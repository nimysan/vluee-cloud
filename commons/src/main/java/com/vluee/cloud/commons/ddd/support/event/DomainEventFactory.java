package com.vluee.cloud.commons.ddd.support.event;

import cn.hutool.core.date.DateUtil;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.event.serialize.DomainEventSerializer;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class DomainEventFactory {

    private final DomainEventSerializer domainEventSerializer;

    /**
     * 从'事件'对象创建'事件存储'对象
     *
     * @param sourceEvent
     * @return
     */
    public SimpleDomainEvent createFrom(Serializable sourceEvent) {
        SimpleDomainEvent simpleDomainEvent = new SimpleDomainEvent(AggregateId.generate(), DateUtil.date(), false, sourceEvent, domainEventSerializer);
        return simpleDomainEvent;
    }
}
