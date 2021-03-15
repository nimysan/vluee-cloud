package com.vluee.cloud.uams.core.resources.domain;

import cn.hutool.core.date.DateUtil;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * REST API类型的资源
 */
@ToString(callSuper = true)
@Entity
@Table(name = "api_resources")
@NoArgsConstructor //for jpa
public class ApiResource extends BaseAggregateRoot {

    @Embedded
    private ResourceDescriptor resourceDescriptor;

    @Embedded
    @Getter
    private RestApi restApi;

    @Embedded
    private Date createdAt;

    public ApiResource(AggregateId aggregateId, RestApi restApi, ResourceDescriptor resourceDescriptor) {
        //validation
        this(aggregateId, restApi);
        this.resourceDescriptor = resourceDescriptor;
    }

    public ApiResource(AggregateId aggregateId, RestApi restApi) {
        //validation
        this.aggregateId = aggregateId;
        this.restApi = restApi;
        this.createdAt = DateUtil.date();
    }

    public void updateVerb(String verb) {
        this.restApi.setVerb(verb);
    }

}
