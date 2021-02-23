package com.vluee.cloud.uams.core.permission;

public class PermissionFactory {

    public Permission createApiPermission(String verb, String url, String name, String description) {
        RestApiResource resource = new RestApiResource(name, url, description);
        Permission permission = new Permission(new Operation(verb), resource);
        return permission;
    }
}
