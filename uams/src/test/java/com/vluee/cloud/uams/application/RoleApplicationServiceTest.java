package com.vluee.cloud.uams.application;

import com.vluee.cloud.uams.core.role.domain.Role;
import com.vluee.cloud.uams.core.role.domain.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Optional;

class RoleApplicationServiceTest {

    @Inject
    private RoleApplicationService roleApplicationService;

    @Inject
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testCreateRole() {
        String roleName = "super";
        roleApplicationService.createRole(roleName);
        Optional<Role> role = roleRepository.loadByName(roleName);
        Assertions.assertTrue(role.isPresent());
        Assertions.assertEquals(roleName, role.get().getName());
    }

    @Test
    public void testCreateDuplicationNameRole() {
        String roleName = "super";
        roleApplicationService.createRole(roleName);
        Assertions.assertThrows(RuntimeException.class, () -> roleApplicationService.createRole(roleName));
    }

    @Test
    public void testRegisterResource() {
        //提交资源
    }

}