package com.vluee.cloud.uams.core.authorize.domain;

import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.uams.core.permission.Permission;
import com.vluee.cloud.uams.core.role.domain.Role;
import com.vluee.cloud.uams.core.user.domain.User;

import javax.validation.constraints.NotNull;

@AggregateRoot
public class Grant {

    public void grant(@NotNull Role role, @NotNull Permission permission) {
        //这是一次授权， 记录授权日志
    }

    public void checkPermission(@NotNull User user, @NotNull Permission permission) {
        //检查授权
        if (permission.isDisabled()) {
            //给出grant结果
        }
    }

}
