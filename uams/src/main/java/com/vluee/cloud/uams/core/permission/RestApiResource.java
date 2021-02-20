package com.vluee.cloud.uams.core.permission;

import lombok.ToString;

/**
 * REST API类型的资源
 */
@ToString(callSuper = true)
public class RestApiResource extends Resource {

    private String restUrl;

    public RestApiResource(String name, String restUrl, String description) {
        super(ResourceType.API, name, description);
        //validation
        this.restUrl = restUrl;
    }

}
