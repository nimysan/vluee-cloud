package com.vluee.cloud.uams.core.role.domain;

import cn.hutool.core.date.DateUtil;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.uams.core.permission.domain.ApiPermission;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "role_api_grants")
@NoArgsConstructor
public class RoleApiPermissionGrant {

    public RoleApiPermissionGrant(CRole role, ApiPermission apiPermission) {
        this.apiPermission = apiPermission.getAggregateId();
        this.roleId = role.getAggregateId();
        this.grantedAt = DateUtil.date();
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column
    @Getter
    private Date grantedAt;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "role_id", nullable = false))})
    @Getter
    private AggregateId roleId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "permission_id", nullable = false))})
    @Getter
    private AggregateId apiPermission;

    @Column
    @Getter
    private boolean removed = false;

}
