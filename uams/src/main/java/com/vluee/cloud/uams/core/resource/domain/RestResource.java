package com.vluee.cloud.uams.core.resource.domain;

import com.vluee.cloud.commons.common.data.AuditAware;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rest_resources")
public class RestResource extends AuditAware {

    @Id
    private Long id;

    @Column(name = "verb")
    private String verb;

    @Column(name = "url_pattern")
    private String urlPattern;

    @Column(name = "resource_name", nullable = true, length = 1024)
    private String name;
}
