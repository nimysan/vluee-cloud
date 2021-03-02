package com.vluee.cloud.uams.core.permission;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.common.data.id.LongIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissionFactory {

    @Autowired
    private LongIdGenerator longIdGenerator;

    public ApiPermission createApiPermission(String verb, String url, String name, String description) {
        RestApiResource resource = new RestApiResource(longIdGenerator.nextId(), name, url, description);
        ApiPermission permission = new ApiPermission(AggregateId.generate(), new Operation(verb), resource);
        return permission;
    }
}
