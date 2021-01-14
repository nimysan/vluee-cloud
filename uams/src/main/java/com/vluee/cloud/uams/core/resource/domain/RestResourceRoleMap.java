package com.vluee.cloud.uams.core.resource.domain;

import com.vluee.cloud.commons.common.data.AuditAware;
import com.vluee.cloud.commons.common.data.id.LongIdGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "rest_resources_role_maps")
@GenericGenerator(name = LongIdGenerator.ID_GENERATOR_NAME, strategy = LongIdGenerator.DISTRIBUTED_ID_GENERATOR_CLASS_NAME)
public class RestResourceRoleMap extends AuditAware {
    public RestResourceRoleMap(Long resourceId, Long roleId) {
        this.resourceId = resourceId;
        this.roleId = roleId;
    }

    @Id
    @GeneratedValue(generator = LongIdGenerator.ID_GENERATOR_NAME)
    private Long id;

    @Column(name = "resource_id")
    private Long resourceId;

    @Column(name = "role_id")
    private Long roleId;
}
