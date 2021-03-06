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

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@SuppressWarnings("serial")
@Embeddable
public class AggregateId implements Serializable {

    @JsonProperty
    private String aggregateId;

    public AggregateId(String aggregateId) {
        Validate.notNull(aggregateId);
        this.aggregateId = aggregateId;
    }

    protected AggregateId() {
    }

    public static AggregateId generate() {
        return new AggregateId(UUID.randomUUID().toString());
    }

    @JsonGetter("aggregateId")
    public String getId() {
        return aggregateId;
    }

    @Override
    public int hashCode() {
        return aggregateId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AggregateId other = (AggregateId) obj;
        if (aggregateId == null) {
            if (other.aggregateId != null)
                return false;
        } else if (!aggregateId.equals(other.aggregateId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return aggregateId;
    }
}
