package com.vluee.cloud.uams.core.rbac.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.uams.core.permission.ApiPermission;
import com.vluee.cloud.uams.core.permission.PermissionFactory;
import com.vluee.cloud.uams.core.role.domain.CRole;
import com.vluee.cloud.uams.core.role.domain.CRoleRepository;
import com.vluee.cloud.uams.core.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AccessControlServiceTest {

    private AccessControlService accessControlService;
    private PermissionFactory permissionFactory = new PermissionFactory();

    private CRoleRepository roleRepository;

    private AccessControlCheckingRepository accessControlCheckingRepository;

    @BeforeEach
    public void setup() {
        roleRepository = Mockito.mock(CRoleRepository.class);
        accessControlCheckingRepository = Mockito.mock(AccessControlCheckingRepository.class);
        accessControlService = new AccessControlService(roleRepository, accessControlCheckingRepository);
    }

    @Test
    public void testAccessAllow() {

        //given
        AggregateId userId = AggregateId.generate();
        AggregateId permissionId = AggregateId.generate();
        User user = new User(userId);

        AggregateId roleId1 = AggregateId.generate();
        CRole role = new CRole(roleId1, "super");

        ApiPermission apiPermission = permissionFactory.createApiPermission("GET", "/hotels", "酒店", "获取酒店列表");
        role.addPermission(apiPermission.getAggregateId());
        user.addRole(roleId1);
        Mockito.when(roleRepository.load(roleId1)).thenReturn(role);

        //testing
        AccessControlChecking accessControlChecking = accessControlService.checkAccess(user, apiPermission);

        //verify
        Mockito.verify(accessControlCheckingRepository).save(accessControlChecking);

        Assertions.assertNotNull(accessControlChecking);
        Assertions.assertTrue(accessControlChecking.allow());

    }

    @Test
    public void testAccessDenyByNoRole() {

        //given
        AggregateId userId = AggregateId.generate();
        AggregateId permissionId = AggregateId.generate();
        User user = new User(userId);

        AggregateId roleId1 = AggregateId.generate();
        CRole role = new CRole(roleId1, "super");

        ApiPermission apiPermission = permissionFactory.createApiPermission("GET", "/hotels", "酒店", "获取酒店列表");
        role.addPermission(apiPermission.getAggregateId());
        user.addRole(roleId1);
        Mockito.when(roleRepository.load(roleId1)).thenReturn(null);

        //testing
        AccessControlChecking accessControlChecking = accessControlService.checkAccess(user, apiPermission);

        //verify
        Mockito.verify(accessControlCheckingRepository).save(accessControlChecking);

        Assertions.assertNotNull(accessControlChecking);
        Assertions.assertFalse(accessControlChecking.allow());

    }

    @Test
    public void testAccessDenyNoPermission() {

        //given
        AggregateId userId = AggregateId.generate();
        AggregateId permissionId = AggregateId.generate();
        User user = new User(userId);

        AggregateId roleId1 = AggregateId.generate();
        CRole role = new CRole(roleId1, "super");

        ApiPermission apiPermission = permissionFactory.createApiPermission("GET", "/hotels", "酒店", "获取酒店列表");
//        role.grantPermission(apiPermission);
        user.addRole(roleId1);
        Mockito.when(roleRepository.load(roleId1)).thenReturn(role);

        //testing
        AccessControlChecking accessControlChecking = accessControlService.checkAccess(user, apiPermission);

        //verify
        Mockito.verify(accessControlCheckingRepository).save(accessControlChecking);

        Assertions.assertNotNull(accessControlChecking);
        Assertions.assertFalse(accessControlChecking.allow());

    }
}