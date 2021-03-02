package com.vluee.cloud.uams.application.service;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.uams.core.permission.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * 1. 注册资源
 * <p>
 * 2. 设计权限（给资源加上操作动词）
 * <p>
 * 样例：
 * <p>
 * 更新酒店房间数量 ---  动词： update 资源：酒店房间数量  ---> POST /hotels/{hotelid}/rooms/{size}
 */
@ApplicationService
@AllArgsConstructor
@Service
public class PermissionApplicationService {

    private final PermissionRepository permissionRepository;
    private final ResourceFactory resourceFactory;

    ApiPermission registerApiPermission(@NotNull RestApi restApi, @NotNull String resourceName, String description) {
        ApiResource resource = resourceFactory.createApiResource(restApi, new ResourceDescriptor(resourceName, description));
        ApiPermission permission = new ApiPermission(AggregateId.generate(), new Operation(restApi.getVerb()), resource);
        permissionRepository.save(permission);
        return permission;
    }
}
