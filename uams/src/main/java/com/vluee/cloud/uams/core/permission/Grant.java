package com.vluee.cloud.uams.core.permission;

import cn.hutool.core.date.DateUtil;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.Date;

@AggregateRoot
@Slf4j
public class Grant extends BaseAggregateRoot {

    @Getter
    private Date grantTime;

    @Getter
    private AggregateId roleId;

    @Getter
    private AggregateId permissionId;

    @Getter
    private GrantOperation grantOperation = GrantOperation.ADD;

    /**
     * @param id
     * @param roleId
     * @param permissionId
     */
    public Grant(@NotNull AggregateId id, @NotNull AggregateId roleId, @NotNull AggregateId permissionId) {
        this.aggregateId = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
        this.grantTime = DateUtil.date();
        this.grantOperation = GrantOperation.ADD;
    }

    /**
     * 取消授权
     */
    public void cancelGrant() {
        this.grantOperation = GrantOperation.REMOVE;
        this.grantTime = DateUtil.date();
    }

    /**
     * 判定给定的permission是否有授权给给定的角色
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    public boolean positiveMatch(AggregateId roleId, AggregateId permissionId) {
        log.info("I am {}", this);
        if (GrantOperation.ADD.equals(this.grantOperation)) {
            return this.roleId.equals(roleId) && this.permissionId.equals(permissionId);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Grant{" +
                "aggregateId=" + aggregateId +
                ", grantTime=" + grantTime +
                ", roleId=" + roleId +
                ", permissionId=" + permissionId +
                ", grantOperation=" + grantOperation +
                '}';
    }

    enum GrantOperation {
        ADD, REMOVE
    }
}
