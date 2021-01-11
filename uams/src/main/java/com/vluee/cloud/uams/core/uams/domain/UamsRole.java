package com.vluee.cloud.uams.core.uams.domain;

import com.vluee.cloud.commons.common.AiStoreConstants;
import com.vluee.cloud.commons.common.data.TenancyAndAuditAware;
import com.vluee.cloud.commons.common.string.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * “角色”
 */
@Entity
public class UamsRole extends TenancyAndAuditAware implements BrandResource {

    @Deprecated
    public UamsRole() {
    }

    public UamsRole(String tenancyId, String roleName) {
        super(tenancyId);
        this.name = roleName;
    }

    public UamsRole(String tenancyId, String roleName, @NotNull String brandId) {
        super(tenancyId);
        this.name = roleName;
        this.brandId = brandId;
    }

    @Id
    @GeneratedValue(generator = "customGenerationId")
    @GenericGenerator(name = "customGenerationId", strategy = "cn.jzdata.aistore.user.core.uams.service.UamsIdGenerator")
    private String id;

    private String name;

    private String description;

    private String brandId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "uams_role_resource_maps", joinColumns = {@JoinColumn(name = "role_id")})
    private Collection<UamsProtectedResource> resources = new ArrayList<>(2);

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

    public Collection<UamsProtectedResource> getResources() {
        return Collections.unmodifiableCollection(this.resources);
    }

    public void addResource(UamsProtectedResource resource) {
        this.resources.add(resource);
    }

    @Override
    public String getBrandId() {
        return this.brandId;
    }


    /**
     * 获取所有资源的授权码
     *
     * @return
     */
    public Collection<String> getAllResourceKeys(AiStoreConstants.UamsPermissionType type) {
        return getResources().stream().map(t -> t.getId()).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UamsRole uamsRole = (UamsRole) o;
        return id.equals(uamsRole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean isOwnByBrand() {
        return StringUtils.isNotEmpty(this.brandId);
    }
}
