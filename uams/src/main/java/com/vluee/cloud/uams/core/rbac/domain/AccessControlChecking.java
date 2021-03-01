package com.vluee.cloud.uams.core.rbac.domain;

import cn.hutool.core.date.DateUtil;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.commons.ddd.annotations.domain.ValueObject;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;

import javax.validation.constraints.NotNull;
import java.util.Date;

@AggregateRoot
public class AccessControlChecking extends BaseAggregateRoot {

    private final AggregateId roleOwnerId;

    private final AggregateId permissionId;

    private final ACLResult aclResult;

    private AggregateId hitRoleId;

    private Date checkDate;

    public static AccessControlChecking deny(AggregateId aclId, AggregateId roleOwner, AggregateId permissionId) {
        return new AccessControlChecking(aclId, roleOwner, permissionId, ACLResult.DENY);
    }

    public static AccessControlChecking access(AggregateId aclId, AggregateId roleOwner, AggregateId permissionId, AggregateId hitRoleId) {
        return new AccessControlChecking(aclId, roleOwner, permissionId, ACLResult.ALLOW, hitRoleId);
    }

    public AccessControlChecking(@NotNull AggregateId aclId, @NotNull AggregateId roleOwner, @NotNull AggregateId permissionId, @NotNull ACLResult aclResult, AggregateId hitRoleId) {
        this(aclId, roleOwner, permissionId, aclResult);
        this.hitRoleId = hitRoleId;
    }

    public AccessControlChecking(@NotNull AggregateId aclId, @NotNull AggregateId roleOwner, @NotNull AggregateId permissionId, @NotNull ACLResult aclResult) {
        this.aggregateId = aclId;
        this.roleOwnerId = roleOwner;
        this.permissionId = permissionId;
        this.checkDate = DateUtil.date();
        this.aclResult = aclResult;
    }

    /**
     * 返回该RBAC的结果
     *
     * @return
     */
    public boolean allow() {
        return ACLResult.ALLOW.equals(this.aclResult);
    }


    @ValueObject
    enum ACLResult {
        DENY, ALLOW;
    }

}
