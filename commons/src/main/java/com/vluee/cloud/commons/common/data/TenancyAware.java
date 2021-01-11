package com.vluee.cloud.commons.common.data;

import javax.persistence.MappedSuperclass;

/**
 * 所有需要租户隔离的管理实体， 请继承本类
 */
@MappedSuperclass
public abstract class TenancyAware {

    private String tenancyId;
}
