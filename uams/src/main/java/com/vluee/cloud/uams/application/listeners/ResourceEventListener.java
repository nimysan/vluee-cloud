package com.vluee.cloud.uams.application.listeners;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.event.EventListener;
import com.vluee.cloud.commons.ddd.annotations.event.EventListeners;
import com.vluee.cloud.uams.core.permission.domain.ApiPermission;
import com.vluee.cloud.uams.core.permission.domain.ApiPermissionRepository;
import com.vluee.cloud.uams.core.permission.domain.PermissionFactory;
import com.vluee.cloud.uams.core.resources.domain.ApiResourceRepository;
import com.vluee.cloud.uams.core.resources.domain.ResourceType;
import com.vluee.cloud.uams.core.resources.domain.events.ResourceCreatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@EventListeners
public class ResourceEventListener {

    private final PermissionFactory permissionFactory;
    private final ApiPermissionRepository apiPermissionRepository;
    private final ApiResourceRepository apiResourceRepository;

    /**
     * 1. 每次创建APIResource， 都会创建对应的APIPermission
     *
     * @param resourceCreatedEvent
     */
    @EventListener
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
