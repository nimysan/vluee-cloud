package com.vluee.cloud.uams.core.grant;

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
public class GrantAction extends BaseAggregateRoot {

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
    public GrantAction(@NotNull AggregateId id, @NotNull AggregateId roleId, @NotNull AggregateId permissionId, GrantOperation grantOperation) {
        this.aggregateId = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
        this.grantTime = DateUtil.date();
        this.grantOperation = grantOperation;
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

    public enum GrantOperation {
        ADD, REMOVE
    }
}
