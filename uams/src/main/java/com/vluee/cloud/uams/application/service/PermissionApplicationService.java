package com.vluee.cloud.uams.application.service;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.uams.core.permission.domain.ApiPermission;
import com.vluee.cloud.uams.core.permission.domain.ApiPermissionRepository;
import com.vluee.cloud.uams.core.resources.domain.*;
import com.vluee.cloud.uams.core.user.domain.User;
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

    private final ApiPermissionRepository apiPermissionRepository;
    private final ResourceFactory resourceFactory;

    ApiPermission registerApiPermission(@NotNull RestApi restApi, @NotNull String resourceName, String description) {
        ApiResource resource = resourceFactory.createApiResource(restApi, new ResourceDescriptor(resourceName, description));
        ApiPermission permission = new ApiPermission(AggregateId.generate(), new ResourceOperation(restApi.getVerb()), resource);
        apiPermissionRepository.save(permission);
        return permission;
    }

    /**
     * 检查某个用户是否拥有某个API的权限
     * <p>
     * 为了性能考虑， 讲check permission的逻辑直接delegate到网关模块去处理和检查 （为了性能破坏设计）
     *
     * @param user
     * @param apiPermission
     * @return
     */
    public boolean checkUserApiPermission(User user, ApiPermission apiPermission) {
        return false;//TODO
    }
}
