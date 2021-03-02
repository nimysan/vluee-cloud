package com.vluee.cloud.uams.core.permission;

import cn.hutool.core.date.DateUtil;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
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
public class ApiResource extends BaseAggregateRoot {

    @Embedded
    private ResourceDescriptor resourceDescriptor;

    @Embedded
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

}
