package com.vluee.cloud.uams.core.permission;

import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * REST API类型的资源
 */
@ToString(callSuper = true)
@Entity
@Table(name = "apis")
public class RestApiResource extends Resource {

    @Column
    private String restUrl;

    @Column
    private String verb;

    public RestApiResource(Long id, String name, String restUrl, String description) {
        super(id, ResourceType.API, name, description);
        //validation
        this.restUrl = restUrl;
    }

}
