package com.vluee.cloud.commons.ddd.support.event;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import com.vluee.cloud.commons.ddd.support.event.exception.DomainEventDefinitionException;
import com.vluee.cloud.commons.ddd.support.event.serialize.DomainEventSerializer;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@NoArgsConstructor
public class SimpleDomainEvent extends BaseAggregateRoot implements Serializable {

    public SimpleDomainEvent(AggregateId aggregateId, Date eventTime, boolean isPublished, @NotNull Serializable event, DomainEventSerializer domainEventSerializer) {
        this.aggregateId = aggregateId;
        this.eventName = event.getClass().getCanonicalName();
        this.eventTime = eventTime;
        this.published = isPublished;
        this.sourceEvent = event;
        this.content = domainEventSerializer.serialize(event);
        this.retries = 0;
    }

    @Column(name = "event_name")
    private String eventName;

    @Column
    private int retries = 0;

    @Column(name = "event_time")
    private Date eventTime;

    @Column(name = "is_publish")
    private boolean published = false;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content", columnDefinition = "BLOB NOT NULL", nullable = true)
    private String content;

    @Transient
    private Serializable sourceEvent;

    public synchronized Serializable getSourceEvent(DomainEventFactory domainEventFactory) {
        try {
            if (sourceEvent == null && StringUtils.isNotBlank(this.content)) {
                sourceEvent = (Serializable) domainEventFactory.restoreEvent(this.content, Class.forName(this.eventName));
            }
        } catch (ClassNotFoundException exception) {
            throw new DomainEventDefinitionException(String.format("事件无法恢复", this.getAggregateId()), exception);
        }

        if (sourceEvent == null) {
            throw new DomainEventDefinitionException(String.format("无法恢复事件。ID %s", this.getAggregateId()));
        }
        return sourceEvent;
    }

    public void markAsPublished() {
        this.published = true;
    }

    public void incrementRetries(){
        this.retries = this.retries +1;
    }


}
