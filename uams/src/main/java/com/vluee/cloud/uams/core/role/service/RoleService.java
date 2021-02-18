package com.vluee.cloud.uams.core.role.service;

import com.vluee.cloud.uams.core.role.domain.Role;
import com.vluee.cloud.uams.core.role.domain.RoleRepository;
import com.vluee.cloud.uams.core.role.exception.RoleNotExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;

    public Role loadRole(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(RoleNotExistException::new);
    }

    public Iterable<Role> list() {
        return roleRepository.findAll();
    }
}
