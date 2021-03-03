package com.vluee.cloud.commons.ddd.support.event;

import cn.hutool.json.JSONUtil;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@NoArgsConstructor
public class SimpleDomainEvent extends BaseAggregateRoot implements Serializable {

    public SimpleDomainEvent(AggregateId aggregateId, Date eventTime, boolean isPublished, @NotNull Serializable event) {
        this.aggregateId = aggregateId;
        this.eventName = event.getClass().getCanonicalName();
        this.eventTime = eventTime;
        this.published = isPublished;
        this.content = JSONUtil.toJsonStr(event);
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

    @Transient
    private Serializable sourceEvent;

    public synchronized Serializable getSourceEvent() {
        try {
            if (sourceEvent == null && StringUtils.isNotBlank(this.content)) {
                sourceEvent = restoreEvent();
            }
        } catch (ClassNotFoundException exception) {
            throw new DomainEventDefinitionException(String.format("事件名称不是一个存在的类，导致无法恢复事件。ID %s", this.getAggregateId()), exception);
        }

        if (sourceEvent == null) {
            throw new DomainEventDefinitionException(String.format("无法恢复事件。ID %s", this.getAggregateId()));
        }
        return sourceEvent;
    }

    private Serializable restoreEvent() throws ClassNotFoundException {
        return (Serializable) JSONUtil.toBean(this.content, Class.forName(this.eventName));
    }

    public void markAsPublished() {
        this.published = true;
    }
}
