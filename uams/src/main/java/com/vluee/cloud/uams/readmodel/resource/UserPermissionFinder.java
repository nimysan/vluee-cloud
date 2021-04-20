package com.vluee.cloud.uams.readmodel.resource;


import com.vluee.cloud.commons.ddd.annotations.domain.Finder;

@Finder
public interface UserPermissionFinder {
    public boolean hasPermission(String userId, String permissionId);
}
