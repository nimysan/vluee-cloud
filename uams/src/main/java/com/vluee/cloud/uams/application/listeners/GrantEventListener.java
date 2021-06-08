package com.vluee.cloud.uams.application.listeners;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.uams.core.permission.domain.ApiPermission;
import com.vluee.cloud.uams.core.permission.domain.ApiPermissionRepository;
import com.vluee.cloud.uams.core.permission.domain.PermissionFactory;
import com.vluee.cloud.uams.core.resources.domain.ApiResourceRepository;
import com.vluee.cloud.uams.core.resources.domain.ResourceType;
import com.vluee.cloud.uams.core.resources.domain.events.ResourceCreatedEvent;
import com.vluee.cloud.uams.core.role.domain.events.RolePermissionAddedEvent;
import com.vluee.cloud.uams.core.role.domain.events.RolePermissionRemovedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
public class GrantEventListener {

    private final PermissionFactory permissionFactory;
    private final ApiPermissionRepository apiPermissionRepository;
    private final ApiResourceRepository apiResourceRepository;

    /**
     * 记录授权日志
     *
     * @param addPermissionToRoleEvent
     */
    public void handle(RolePermissionAddedEvent addPermissionToRoleEvent) {
        log.info("add event listener {}", addPermissionToRoleEvent);
    }

    /**
     * 记录取消授权日志
     *
     * @param rolePermissionRemovedEvent
     */
    public void handle(RolePermissionRemovedEvent rolePermissionRemovedEvent) {
        log.info("add event listener {}", rolePermissionRemovedEvent);
    }

    @Transactional
    public void handle(ResourceCreatedEvent resourceCreatedEvent) {
        //permission created
        if (ResourceType.API.equals(resourceCreatedEvent.getResourceType())) {
            AggregateId resourceId = resourceCreatedEvent.getResourceId();
            ApiPermission apiPermission = permissionFactory.createApiPermission(apiResourceRepository.load(resourceId));
            apiPermissionRepository.save(apiPermission);
        }
    }
}
