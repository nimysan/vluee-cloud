package com.vluee.cloud.tenants.core.tenants.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@AggregateRoot
@NoArgsConstructor // for jpa
public class Tenant extends BaseAggregateRoot {

    @Getter
    @Column
    private String tenantName;

    public Tenant(AggregateId aggregateId, String tenantName) {
        this.aggregateId = aggregateId;
        this.tenantName = tenantName;
    }

}
