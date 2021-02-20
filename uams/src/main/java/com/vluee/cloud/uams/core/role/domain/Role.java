package com.vluee.cloud.uams.core.role.domain;

import com.vluee.cloud.commons.common.data.id.LongIdGenerator;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AggregateRoot
@Entity
@Table(name = "roles")
@NoArgsConstructor
@GenericGenerator(name = LongIdGenerator.ID_GENERATOR_NAME, strategy = LongIdGenerator.DISTRIBUTED_ID_GENERATOR_CLASS_NAME)
public class Role extends BaseAggregateRoot {

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

    @Getter
    @Column(name = "role_describe")
    private String description;

}
