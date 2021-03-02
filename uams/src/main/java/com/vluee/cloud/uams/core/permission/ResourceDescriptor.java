package com.vluee.cloud.uams.core.permission;

import cn.hutool.core.date.DateUtil;
import com.vluee.cloud.commons.ddd.annotations.domain.ValueObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

@ValueObject
@ToString
@Embeddable
public class ResourceDescriptor {
    @Column
    @Getter
    @Setter
    private String name;

    @Column
    @Getter
    @Setter
    private String description;

    @Column
    private Date createdAt;

    public ResourceDescriptor(String name, String description) {
        this.name = name;
        this.description = description;
        this.createdAt = DateUtil.date();
    }
}
