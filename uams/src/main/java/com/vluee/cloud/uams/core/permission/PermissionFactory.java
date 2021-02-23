package com.vluee.cloud.uams.core.permission;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;

public class PermissionFactory {

    public Permission createApiPermission(String verb, String url, String name, String description) {
        RestApiResource resource = new RestApiResource(name, url, description);
        Permission permission = new Permission(AggregateId.generate(), new Operation(verb), resource);
        return permission;
    }
}
