package com.vluee.cloud.uams.application;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.uams.core.authorize.domain.CheckPermission;
import com.vluee.cloud.uams.core.authorize.service.AuthorizeService;
import com.vluee.cloud.uams.core.permission.*;
import com.vluee.cloud.uams.core.role.domain.Role;
import com.vluee.cloud.uams.core.role.domain.RoleRepository;
import com.vluee.cloud.uams.core.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthorizeApplicationServiceTest {

    private AuthorizeApplicationService authorizeApplicationService;
    private AuthorizeService authorizeService = Mockito.mock(AuthorizeService.class);
    private PermissionRepository permissionRepository = Mockito.mock(PermissionRepository.class);
    private PermissionFactory permissionFactory = new PermissionFactory();

    private RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
    private GrantRepository grantRepository = Mockito.mock(GrantRepository.class);

    @BeforeEach
    public void setup() {
        authorizeApplicationService = new AuthorizeApplicationService(authorizeService, grantRepository, permissionRepository);
    }


    //确权测试
    @Test
    public void testCheckPermission() {
        //确权
        AggregateId userId = new AggregateId("8901");
        AggregateId permissionId = new AggregateId("0988");

        User user = authorizeApplicationService.loadUser(userId);
        Permission apiPermission = permissionFactory.createApiPermission("GET", "/hotels", "酒店列表", "列出所有酒店");
        when(permissionRepository.findById(988L)).thenReturn(Optional.of(apiPermission)); // mock repository behaviour
        when(authorizeService.checkRBAC(user, apiPermission)).thenReturn(new CheckPermission(user, apiPermission));
        CheckPermission checkPermission = authorizeApplicationService.checkPermission(userId, permissionId);
        verify(authorizeService).checkRBAC(user, apiPermission);
        Assertions.assertNotNull(checkPermission);
    }

    @Test
    public void testGrantPermission() {
        AggregateId roleId = new AggregateId("8901");
        AggregateId permissionId = new AggregateId("0988");
        Permission apiPermission = permissionFactory.createApiPermission("GET", "/hotels", "酒店列表", "列出所有酒店");
        Role role = Mockito.mock(Role.class);
        //given
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(permissionRepository.findById(988L)).thenReturn(Optional.of(apiPermission)); // mock repository behaviour

        //do
        Grant grant = authorizeApplicationService.grant(role, apiPermission);

        //verify
        verify(authorizeService).grant(role, apiPermission);
    }

}