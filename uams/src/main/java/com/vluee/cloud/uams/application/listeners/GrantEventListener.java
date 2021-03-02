package com.vluee.cloud.uams.application.listeners;

import com.vluee.cloud.commons.ddd.annotations.event.EventListener;
import com.vluee.cloud.commons.ddd.annotations.event.EventListeners;
import com.vluee.cloud.uams.core.role.domain.events.RolePermissionAddedEvent;
import com.vluee.cloud.uams.core.role.domain.events.RolePermissionRemovedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventListeners
@AllArgsConstructor
public class GrantEventListener {

    /**
     * 记录授权日志
     *
     * @param addPermissionToRoleEvent
     */
    @EventListener
    public void handle(RolePermissionAddedEvent addPermissionToRoleEvent) {
        log.info("add event listener {}", addPermissionToRoleEvent);
    }

    /**
     * 记录取消授权日志
     *
     * @param rolePermissionRemovedEvent
     */
    @EventListener
    public void handle(RolePermissionRemovedEvent rolePermissionRemovedEvent) {
        log.info("add event listener {}", rolePermissionRemovedEvent);
    }
}
