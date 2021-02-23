package com.vluee.cloud.uams.application;

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

    Permission registerApiPermission(@NotNull RestApi restApi, @NotNull String resourceName, String description) {
        RestApiResource resource = new RestApiResource(resourceName, restApi.getUrl(), description);
        Permission permission = new Permission(AggregateId.generate(), new Operation(restApi.getVerb()), resource);
        permissionRepository.save(permission);
        return permission;
    }
}
