package com.vluee.cloud.uams.core.permission.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;

public interface ApiPermissionRepository {

    public void save(ApiPermission permission);

    ApiPermission load(AggregateId permissionId);
}
