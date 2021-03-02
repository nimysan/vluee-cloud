package com.vluee.cloud.uams.core.permission.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.uams.core.resources.domain.ApiResource;
import com.vluee.cloud.uams.core.resources.domain.ResourceOperation;
import org.springframework.stereotype.Component;

@Component
public class PermissionFactory {

    /**
     * 针对API类型的资源，只有一种 “执行” 权限
     *
     * @param resource
     * @return
     */
    public ApiPermission createApiPermission(ApiResource resource) {
        ApiPermission permission = new ApiPermission(AggregateId.generate(), ResourceOperation.API_EXECUTE_RESOURCE_OPERATION, resource);
        return permission;
    }
}
