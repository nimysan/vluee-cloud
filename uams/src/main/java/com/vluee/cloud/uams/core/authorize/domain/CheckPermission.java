package com.vluee.cloud.uams.core.authorize.domain;

import cn.hutool.core.date.DateUtil;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.uams.core.permission.Permission;
import com.vluee.cloud.uams.core.role.domain.Role;
import com.vluee.cloud.uams.core.user.User;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 反映一次检查权限操作检查
 */
@AggregateRoot
public class CheckPermission {

    public CheckPermission(@NotNull User user, @NotNull Permission permission) {
        this.result = null;//check(role, permission);
        this.checkDate = DateUtil.date();
    }

    /**
     *
     */
    private CheckPermissionResult result;

    public boolean isAllow() {
        return result.isAllow();
    }

    /**
     * 检查日期
     */
    private Date checkDate;

    private CheckPermissionResult check(@NotNull Role role, @NotNull Permission permission) {
        if (permission.isDisabled()) {
            return new CheckPermissionResult(CheckPermissionResult.EnumResult.PERMISSION_DISABLED);
        }
        if (permission.isGrantToRole(role)) {
            return CheckPermissionResult.allow();
        } else {
            return CheckPermissionResult.deny();
        }
    }
}
