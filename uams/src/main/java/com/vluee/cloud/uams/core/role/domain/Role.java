package com.vluee.cloud.uams.core.role.domain;

import com.vluee.cloud.commons.common.data.AuditAware;
import com.vluee.cloud.commons.common.data.id.LongIdGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@GenericGenerator(name = LongIdGenerator.ID_GENERATOR_NAME, strategy = LongIdGenerator.DISTRIBUTED_ID_GENERATOR_CLASS_NAME)
public class Role extends AuditAware {

    public Role(String name) {
        this.name = name;
    }

    @Id
    @Getter
    @GeneratedValue(generator = LongIdGenerator.ID_GENERATOR_NAME)
    private Long id;

    @Getter
    @Column(name = "role_name")
    private String name;

}
