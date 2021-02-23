package com.vluee.cloud.uams.core.system.domain;

import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.uams.core.authorize.domain.CheckPermission;

import java.util.Collection;

/**
 * 一个虚拟的聚合，用于容纳全系统范围内的实体和值对象
 */
@AggregateRoot
public class System {

    public void createRole(String roleName) {

    }

    public Collection<CheckPermission> permissionCheckingHistory;
}
