package com.vluee.cloud.uams.core.rbac.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.DomainService;
import com.vluee.cloud.uams.core.permission.domain.ApiPermission;
import com.vluee.cloud.uams.core.role.domain.CRole;
import com.vluee.cloud.uams.core.role.domain.CRoleRepository;
import com.vluee.cloud.uams.core.user.domain.User;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * 鉴权服务
 */
@DomainService
@AllArgsConstructor
public class AccessControlService {

    private final CRoleRepository roleRepository;

    private final AccessControlCheckingRepository accessControlCheckingRepository;

    /**
     * 检查用户是否允许做这个操作
     *
     * @param user       用户
     * @param permission 操作
     */
    public AccessControlChecking checkAccess(@NotNull User user, @NotNull ApiPermission permission) {

        AccessControlChecking checking;
        Collection<AggregateId> aggregateIds = user.ownedRoles();

        if (aggregateIds == null) {
            checking = AccessControlChecking.deny(AggregateId.generate(), user.getId(), permission.getAggregateId());
        } else {
            //查找是否有含permission的角色
            Optional<CRole> matchRole = aggregateIds.stream().map(roleRepository::load).filter(Objects::nonNull).filter(t -> t.hasPermission(permission.getAggregateId())).findFirst();
            if (matchRole.isPresent()) {
                //allow
                checking = AccessControlChecking.access(AggregateId.generate(), user.getId(), permission.getAggregateId(), matchRole.get().getAggregateId());
            } else {
                //deny
                checking = AccessControlChecking.deny(AggregateId.generate(), user.getId(), permission.getAggregateId());
            }
        }

        accessControlCheckingRepository.save(checking);
        return checking;
    }
}
