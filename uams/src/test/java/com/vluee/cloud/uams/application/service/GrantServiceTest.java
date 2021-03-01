package com.vluee.cloud.uams.application.service;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.uams.application.command.GrantPermissionToRoleCommand;
import com.vluee.cloud.uams.core.permission.Permission;
import com.vluee.cloud.uams.core.permission.PermissionFactory;
import com.vluee.cloud.uams.core.permission.PermissionRepository;
import com.vluee.cloud.uams.core.role.domain.CRole;
import com.vluee.cloud.uams.core.role.domain.CRoleRepository;
import com.vluee.cloud.uams.core.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GrantServiceTest {

    private GrantService grantService;

    private CRoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    private PermissionFactory permissionFactory = new PermissionFactory();


    @BeforeEach
    public void setup() {
        roleRepository = Mockito.mock(CRoleRepository.class);
        grantService = new GrantService(roleRepository);
    }

    @Test
    public void testGrant() {
        //given
        AggregateId userId = AggregateId.generate();
        AggregateId permissionId = AggregateId.generate();
        User user = new User(userId);

        AggregateId roleId1 = AggregateId.generate();
        CRole role = new CRole(roleId1, "super");

        Permission apiPermission = permissionFactory.createApiPermission("GET", "/hotels", "酒店", "获取酒店列表");

        GrantPermissionToRoleCommand grantPermissionToRoleCommand = new GrantPermissionToRoleCommand(apiPermission.getAggregateId(), roleId1);
        grantService.grantPermissionToRole(grantPermissionToRoleCommand);
//        Assertions.assertNotNull(grant);
//        Assertions.assertEquals(GrantAction.GrantOperation.ADD, grant.getGrantOperation());
        Assertions.assertTrue(role.hasPermission(apiPermission.getAggregateId()));
    }
}