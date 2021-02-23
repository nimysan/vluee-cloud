package com.vluee.cloud.uams.core.authorize.domain;

import com.vluee.cloud.commons.ddd.annotations.domain.ValueObject;
import com.vluee.cloud.uams.core.permission.Permission;
import com.vluee.cloud.uams.core.user.domain.User;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ValueObject
public class CheckPermissionResult {

    public CheckPermissionResult(@NotNull EnumResult result) {
        this.result = result;
    }

    private EnumResult result;
    private Date checkTime;

    private User user;
    private Permission permission;


    /**
     * 无权限
     *
     * @return
     */
    public static final CheckPermissionResult deny() {
        return new CheckPermissionResult(EnumResult.NO_PERMISSION);
    }

    /**
     * allow
     *
     * @return
     */
    public static final CheckPermissionResult allow() {
        return new CheckPermissionResult(EnumResult.HAS_PERMISSION);
    }

    enum EnumResult {
        PERMISSION_DISABLED, NO_PERMISSION, HAS_PERMISSION;
    }

    public boolean isAllow() {
        return EnumResult.HAS_PERMISSION.equals(this.result);
    }
}
