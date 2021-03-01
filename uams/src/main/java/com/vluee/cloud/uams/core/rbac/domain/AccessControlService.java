package com.vluee.cloud.uams.core.rbac.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.DomainService;
import com.vluee.cloud.uams.core.permission.Permission;
import com.vluee.cloud.uams.core.role.domain.CRole;
import com.vluee.cloud.uams.core.role.domain.RoleRepository;
import com.vluee.cloud.uams.core.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

@DomainService
@Service
@AllArgsConstructor
public class AccessControlService {

    private final RoleRepository roleRepository;

    private final AccessControlCheckingRepository accessControlCheckingRepository;

    /**
     * 检查用户是否拥有给定权限
     *
     * @param user
     * @param permission
     */
    public AccessControlChecking checkAccess(@NotNull User user, @NotNull Permission permission) {
        AccessControlChecking rabc = null;
        Collection<AggregateId> aggregateIds = user.ownedRoles();
        if (aggregateIds == null) {
            rabc = AccessControlChecking.deny(AggregateId.generate(), user.getId(), permission.getAggregateId());
        } else {
            //查找是否有含permission的角色
            Optional<CRole> matchRole = aggregateIds.stream().map(t -> roleRepository.findById(t)).filter(t -> t.isPresent()).map(t -> t.get()).filter(t -> t.hasPermission(permission.getAggregateId())).findFirst();
            if (matchRole.isPresent()) {
                //allow
                rabc = AccessControlChecking.access(AggregateId.generate(), user.getId(), permission.getAggregateId(), matchRole.get().getAggregateId());
            } else {
                //deny
                rabc = AccessControlChecking.deny(AggregateId.generate(), user.getId(), permission.getAggregateId());
            }
        }

        accessControlCheckingRepository.save(rabc);
        return rabc;
    }
}
