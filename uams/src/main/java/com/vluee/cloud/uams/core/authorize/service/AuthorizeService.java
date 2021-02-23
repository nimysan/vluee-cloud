package com.vluee.cloud.uams.core.authorize.service;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.DomainService;
import com.vluee.cloud.uams.core.authorize.domain.CheckPermission;
import com.vluee.cloud.uams.core.permission.Grant;
import com.vluee.cloud.uams.core.permission.GrantRepository;
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
import java.util.stream.StreamSupport;

@DomainService
@AllArgsConstructor
@Service
@Slf4j
public class AuthorizeService {

    private final RoleRepository roleRepository;
    private final GrantRepository grantRepository;

    /**
     * RBACSpecification
     *
     * @return
     */
    public CheckPermission checkRBAC(@NotNull User user, @NotNull Permission permission) {
        Collection<AggregateId> roles = user.ownedRoles();
        Optional<Role> matchedRole = roles.stream().map(aggregateId -> roleRepository.findById(aggregateId)).filter(t -> t.isPresent()).map(t -> t.get()).filter(t -> t.hasPermission(permission)).findFirst();
        //返回结果
        Iterable<Grant> all = grantRepository.findAll();
        for (AggregateId roleId : roles) {
            boolean matchGrant = StreamSupport.stream(all.spliterator(), false).anyMatch(g -> g.positiveMatch(roleId, permission.getAggregateId()));
            if (matchGrant) {
                //match result
                return new CheckPermission(user, permission);
            }
        }

        return new CheckPermission(user, permission);
    }

    public Grant grant(@NotNull Role role, @NotNull Permission permission) {
        Grant grant = new Grant(AggregateId.generate(), role.getAggregateId(), permission.getAggregateId());
        grantRepository.save(grant);
        return grant;
    }

}