package com.vluee.cloud.uams.core.uams.domain;

import com.vluee.cloud.commons.common.data.AuditAware;
import com.vluee.cloud.commons.common.uams.ResourceDescriptor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class UamsProtectedResource extends AuditAware {

    @Deprecated
    public UamsProtectedResource() {
    }

    public UamsProtectedResource(@NotNull String name, String description, String contributor, @NotNull ResourceDescriptor resourceDescriptor) {
        this.name = name;
        this.description = description;
        this.contributor = contributor;
        this.resourceDescriptor = resourceDescriptor.toJsonStr();
    }

    @Id
    @GeneratedValue(generator = "customGenerationId")
    @GenericGenerator(name = "customGenerationId", strategy = "cn.jzdata.aistore.user.core.uams.service.UamsIdGenerator")
    private String id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源描述
     */
    private String description;

    /**
     * 资源贡献者
     */
    private String contributor;

    /**
     * 资源描述符号
     */
    private String resourceDescriptor;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getResourceDescriptorInJsonStr() {
        return resourceDescriptor;
    }

    public void setResourceDescriptor(@NotNull ResourceDescriptor resourceDescriptor) {
        this.resourceDescriptor = resourceDescriptor.toJsonStr();
    }
}
