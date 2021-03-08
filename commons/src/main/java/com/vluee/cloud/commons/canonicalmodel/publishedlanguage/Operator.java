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
package com.vluee.cloud.commons.canonicalmodel.publishedlanguage;


import cn.hutool.core.date.DateUtil;
import com.vluee.cloud.commons.ddd.annotations.domain.ValueObject;

import javax.persistence.*;
import java.util.Date;

/**
 * For Audit purpose
 */
@ValueObject
@Embeddable
public class Operator {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "operatorId", nullable = false))})
    private AggregateId aggregateId;

    private String name;


    private Date operationTime;

    @SuppressWarnings("unused")
    private Operator() {
    }

    public Operator(AggregateId aggregateId, String name) {
        this.aggregateId = aggregateId;
        this.name = name;
        this.operationTime = DateUtil.date();
    }

    public AggregateId getAggregateId() {
        return aggregateId;
    }

    public String getName() {
        return name;
    }

    public Date getOperationTime(){
        return this.operationTime;
    }
}
