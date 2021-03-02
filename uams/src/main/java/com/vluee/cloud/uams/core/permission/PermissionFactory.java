package com.vluee.cloud.uams.core.permission;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.common.data.id.LongIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissionFactory {

    @Autowired
    private LongIdGenerator longIdGenerator;

    /**
     * 针对API类型的资源，只有一种 “执行” 权限
     *
     * @param resource
     * @return
     */
    public ApiPermission createApiPermission(ApiResource resource) {
        ApiPermission permission = new ApiPermission(AggregateId.generate(), Operation.API_EXECUTE_OPERATION, resource);
        return permission;
    }
}
