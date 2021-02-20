package com.vluee.cloud.uams.application;

import com.vluee.cloud.uams.core.role.domain.Role;
import com.vluee.cloud.uams.core.role.domain.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

/**
 * 角色和角色组相关应用服务
 */
@AllArgsConstructor
@Slf4j
public class RoleApplicationService {

    private final RoleRepository roleRepository;

    /**
     * 创建给定名称的角色
     *
     * @param roleName
     */
    public void createRole(@NotNull String roleName) {
        Role role = new Role(roleName);
        roleRepository.save(role);
    }

}
