package com.vluee.cloud.uams.core.authorize;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.DomainService;
import com.vluee.cloud.uams.core.authorize.domain.CheckPermission;
import com.vluee.cloud.uams.core.permission.Permission;
import com.vluee.cloud.uams.core.role.domain.Role;
import com.vluee.cloud.uams.core.role.domain.RoleRepository;
import com.vluee.cloud.uams.core.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

@DomainService
@AllArgsConstructor
@Service
@Slf4j
public class AuthorizeService {

    private final RoleRepository roleRepository;

    /**
     * RBACSpecification
     * @return
     */
    public CheckPermission checkRBAC(@NotNull User user, @NotNull Permission permission) {
        Collection<AggregateId> roles = user.ownedRoles();
        Optional<Role> matchedRole = roles.stream().map(aggregateId -> roleRepository.findById(Long.parseLong(aggregateId.getId()))).filter(t -> t.isPresent()).map(t -> t.get()).filter(t -> t.hasPermission(permission)).findFirst();
        //返回结果

        return null;
    }
}