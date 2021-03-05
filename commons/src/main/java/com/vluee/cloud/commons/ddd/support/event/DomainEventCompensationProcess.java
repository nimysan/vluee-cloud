package com.vluee.cloud.commons.ddd.support.event;

import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class DomainEventCompensationProcess extends BaseAggregateRoot {

    /**
     * 分布式环境下机器
     */
    private String source;

    private Date startedAt;
}
