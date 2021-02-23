package com.vluee.cloud.uams.application;

import com.vluee.cloud.uams.core.permission.Permission;
import com.vluee.cloud.uams.core.role.domain.Role;
import org.junit.jupiter.api.Test;

class UamsApplicationServiceTest {

    private UamsApplicationService uamsApplicationService;

    private PermissionApplicationService permissionApplicationService;

    @Test
    public void testGrantPermissionToUser() {
        //不允许直接给用户授权，必须通过角色
        uamsApplicationService.createRole("super");

        Role role;
        Permission permission;

        



    }
}