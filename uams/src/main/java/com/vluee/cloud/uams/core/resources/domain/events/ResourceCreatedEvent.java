package com.vluee.cloud.uams.core.resources.domain.events;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.uams.core.resources.domain.ResourceType;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ResourceCreatedEvent implements Serializable {
    private AggregateId resourceId;
    private ResourceType resourceType;
}
