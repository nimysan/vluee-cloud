package com.vluee.cloud.commons.ddd.support.event;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.event.exception.DomainEventDefinitionException;
import com.vluee.cloud.commons.ddd.support.event.serialize.DomainEventSerializer;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Builder
public class SimpleDomainEvent implements Serializable {

    public static final Long DEFAULT_VERSION = 0L;
    public static final int DEFAULT_RETRIES = 0;

    public SimpleDomainEvent(AggregateId aggregateId, Long version, boolean isArchive, String eventName, int retries, Date eventTime, boolean published, String content, Serializable sourceEvent) {
        this.aggregateId = aggregateId;
        this.version = version;
        this.isArchive = isArchive;
        this.eventName = eventName;
        this.retries = retries;
        this.eventTime = eventTime;
        this.published = published;
        this.content = content;
        this.sourceEvent = sourceEvent;
    }

    public SimpleDomainEvent(AggregateId aggregateId, Date eventTime, boolean isPublished, @NotNull Serializable event, DomainEventSerializer domainEventSerializer) {
        this.aggregateId = aggregateId;
        this.eventName = event.getClass().getCanonicalName();
        this.eventTime = eventTime;
        this.published = isPublished;
        this.sourceEvent = event;
        this.content = domainEventSerializer.serialize(event);
        this.retries = DEFAULT_RETRIES;
        this.version = DEFAULT_VERSION;
    }

    @Getter
    private AggregateId aggregateId;

    @Getter
    private Long version;

    @Getter
    private boolean isArchive = false;

    @Getter
    private String eventName;

    @Getter
    private int retries = 0;

    @Getter
    private Date eventTime;

    @Getter
    private boolean published = false;

    @Getter
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

    public void incrementRetries() {
        this.retries = this.retries + 1;
    }


}
