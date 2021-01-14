package com.vluee.cloud.uams.core.resource.domain;

import com.vluee.cloud.commons.common.data.AuditAware;
import com.vluee.cloud.commons.common.data.id.LongIdGenerator;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "rest_resources")
@GenericGenerator(name = LongIdGenerator.ID_GENERATOR_NAME, strategy = LongIdGenerator.DISTRIBUTED_ID_GENERATOR_CLASS_NAME)
@NoArgsConstructor
public class RestResource extends AuditAware {


    @Id
    @GeneratedValue(generator = LongIdGenerator.ID_GENERATOR_NAME)
    private Long id;

    public RestResource(String verb, String urlPattern, String name) {
        this.verb = verb;
        this.urlPattern = urlPattern;
        this.name = name;
    }

    @Column(name = "verb")
    private String verb;

    @Column(name = "url_pattern")
    private String urlPattern;

    @Column(name = "resource_name", nullable = true, length = 1024)
    private String name;

    public String composeKey() {
        return this.verb + " " + this.urlPattern;
    }
}
