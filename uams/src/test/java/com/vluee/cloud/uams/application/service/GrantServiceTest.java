package com.vluee.cloud.uams.application.service;

import com.vluee.cloud.uams.core.permission.domain.PermissionFactory;
import com.vluee.cloud.uams.core.permission.domain.ApiPermissionRepository;
import com.vluee.cloud.uams.core.role.domain.CRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GrantServiceTest {

    private GrantService grantService;

    private CRoleRepository roleRepository;
    private ApiPermissionRepository apiPermissionRepository;

    private PermissionFactory permissionFactory = new PermissionFactory();


    @BeforeEach
    public void setup() {
        roleRepository = Mockito.mock(CRoleRepository.class);
//        grantService = new GrantService(roleRepository);
    }

    @Test
    public void testGrant() {
        //given
//        AggregateId userId = AggregateId.generate();
//        AggregateId permissionId = AggregateId.generate();
//        User user = new User(userId);
//
//        AggregateId roleId1 = AggregateId.generate();
//        CRole role = new CRole(roleId1, "super");
//
//        ApiPermission apiPermission = permissionFactory.createApiPermission("GET", "/hotels", "酒店", "获取酒店列表");
//
//        Mockito.when(roleRepository.load(roleId1)).thenReturn(role);
//
//        GrantPermissionToRoleCommand grantPermissionToRoleCommand = new GrantPermissionToRoleCommand(roleId1, apiPermission.getAggregateId());
//        grantService.grantPermissionToRole(grantPermissionToRoleCommand);
////        Assertions.assertNotNull(grant);
////        Assertions.assertEquals(GrantAction.GrantOperation.ADD, grant.getGrantOperation());
//        Assertions.assertTrue(role.hasPermission(apiPermission.getAggregateId()));
    }
}