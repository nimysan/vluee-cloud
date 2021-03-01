package com.vluee.cloud.uams.core.rbac.domain;

import cn.hutool.core.date.DateUtil;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.commons.ddd.annotations.domain.ValueObject;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@AggregateRoot
public class AccessControlChecking extends BaseAggregateRoot {

    @Getter
    private final AggregateId roleOwnerId;

    @Getter
    private final AggregateId permissionId;

    @Getter
    private final ACResult aclResult;

    @Getter
    private AggregateId hitRoleId;

    @Getter
    private final Date checkDate;

    public static AccessControlChecking deny(AggregateId aclId, AggregateId roleOwner, AggregateId permissionId) {
        return new AccessControlChecking(aclId, roleOwner, permissionId, ACResult.DENY);
    }

    public static AccessControlChecking access(AggregateId aclId, AggregateId roleOwner, AggregateId permissionId, AggregateId hitRoleId) {
        return new AccessControlChecking(aclId, roleOwner, permissionId, ACResult.ALLOW, hitRoleId);
    }

    public AccessControlChecking(@NotNull AggregateId aclId, @NotNull AggregateId roleOwner, @NotNull AggregateId permissionId, @NotNull AccessControlChecking.ACResult aclResult, AggregateId hitRoleId) {
        this(aclId, roleOwner, permissionId, aclResult);
        this.hitRoleId = hitRoleId;
    }

    public AccessControlChecking(@NotNull AggregateId aclId, @NotNull AggregateId roleOwner, @NotNull AggregateId permissionId, @NotNull AccessControlChecking.ACResult aclResult) {
        this.aggregateId = aclId;
        this.roleOwnerId = roleOwner;
        this.permissionId = permissionId;
        this.checkDate = DateUtil.date();
        this.aclResult = aclResult;
    }

    /**
     * 返回该RBAC的结果
     *
     * @return true means allow
     */
    public boolean allow() {
        return ACResult.ALLOW.equals(this.aclResult);
    }


    @ValueObject
    enum ACResult {
        DENY, ALLOW
    }

}
