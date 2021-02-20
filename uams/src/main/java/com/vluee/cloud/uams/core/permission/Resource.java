package com.vluee.cloud.uams.core.permission;

import lombok.Getter;
import lombok.ToString;

/**
 * Permission的资源
 */
@ToString
public class Resource {

    @Getter
    private Long id;

    @Getter
    private String name;
    private ResourceType resourceType;
    private String description;

    public Resource(ResourceType resourceType, String name, String description) {
        this.resourceType = resourceType;
        this.name = name;
        this.description = description;
    }

    public enum ResourceType {
        /**
         * API 为 rest api的类型
         */
        API, MENU, UI
    }
}
