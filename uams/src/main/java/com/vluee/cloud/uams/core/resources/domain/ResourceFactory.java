package com.vluee.cloud.uams.core.resources.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.DomainFactory;
import com.vluee.cloud.commons.ddd.support.event.DomainEventPublisherFactory;
import com.vluee.cloud.uams.core.resources.domain.events.ResourceCreatedEvent;

import static com.vluee.cloud.uams.core.resources.domain.ResourceType.API;

@DomainFactory
public class ResourceFactory {

    public ApiResource createApiResource(RestApi api, ResourceDescriptor resourceDescriptor) {
        ApiResource apiResource = new ApiResource(AggregateId.generate(), api, resourceDescriptor);
        ResourceCreatedEvent event = ResourceCreatedEvent.builder().resourceId(apiResource.getAggregateId()).resourceType(API).build();
        DomainEventPublisherFactory.getPublisher().publish(event);
        return apiResource;
    }
}
