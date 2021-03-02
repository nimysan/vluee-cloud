package com.vluee.cloud.uams.core.permission;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.DomainFactory;

@DomainFactory
public class ResourceFactory {

    public ApiResource createApiResource(RestApi api, ResourceDescriptor resourceDescriptor) {
        return new ApiResource(AggregateId.generate(), api, resourceDescriptor);
    }
}
