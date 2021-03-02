package com.vluee.cloud.uams.core.permission;

import com.vluee.cloud.commons.common.data.id.LongIdGenerator;
import com.vluee.cloud.commons.ddd.annotations.domain.ValueObject;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Permission的资源
 */
@ValueObject
@ToString
@MappedSuperclass
public class Resource {

    @Id
    @Getter
    private Long id;

    @Getter
    @Column(name = "resource_name", nullable = false)
    private String name;

    @Column(name = "resource_type")
    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;

    @Column(name = "resource_description", length = 1024, nullable = true)
    private String description;

    public Resource(Long id, ResourceType resourceType, String name, String description) {
        this.id = id;
        this.resourceType = resourceType;
        this.name = name;
        this.description = description;
    }

    public enum ResourceType {
        /**
         * API 为 rest api的类型
         */
        API, MENU, UI, BUTTON, MODULE
    }
}
