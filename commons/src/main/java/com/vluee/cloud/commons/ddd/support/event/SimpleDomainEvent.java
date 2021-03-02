package com.vluee.cloud.commons.ddd.support.event;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
public class SimpleDomainEvent extends BaseAggregateRoot {

    public SimpleDomainEvent(AggregateId aggregateId, String eventName, Date eventTime, boolean isPublished, String jsonContent) {
        this.aggregateId = aggregateId;
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.published = isPublished;
        this.content = jsonContent;
    }

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_time")
    private Date eventTime;

    @Column(name = "is_publish")
    private boolean published = false;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content", columnDefinition = "BLOB NOT NULL", nullable = true)
    private String content;

    public void markAsPublished() {
        this.published = true;
    }
}
