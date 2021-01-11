package com.vluee.cloud.uams.core.uams.domain;

import com.vluee.cloud.commons.common.data.TenancyAndAuditAware;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

/**
 * “用户组”
 */
@Entity
public class UamsUserGroup extends TenancyAndAuditAware implements BrandResource, BrandOwner {

    @Deprecated
    public UamsUserGroup() {
        this.brandId = null;
    }

    public UamsUserGroup(String tenancyId, String groupName) {
        super(tenancyId);
        this.roles = new HashSet<>(1);
        this.users = new HashSet<>(1);
        this.name = groupName;
        this.brandId = null;
    }

    public UamsUserGroup(String tenancyId, String groupName, String brandId) {
        super(tenancyId);
        this.roles = new HashSet<>(1);
        this.users = new HashSet<>(1);
        this.brandId = brandId;
        this.name = groupName;
    }

    @javax.persistence.Id
    @GeneratedValue(generator = "customGenerationId")
    @GenericGenerator(name = "customGenerationId", strategy = "cn.jzdata.aistore.user.core.uams.service.UamsIdGenerator")
    private String Id;

    private String name;

    private final String brandId;

    private String description;

    @ManyToMany(mappedBy = "userGroups", fetch = FetchType.LAZY)
    private Set<UamsUser> users;

    /**
     * 一个user_group可以拥有多个角色
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "uams_group_role_maps", joinColumns = {@JoinColumn(name = "group_id")})
    private Set<UamsRole> roles;

    public String getId() {
        return Id;
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

    public Set<UamsUser> getUsers() {
        return users;
    }

    public Set<UamsRole> getRoles() {
        return roles;
    }

    @Override
    public String getBrandId() {
        return brandId;
    }

    public void addUser(UamsUser uamsUser) {
        users.add(uamsUser);
    }

    public void addRole(UamsRole uamsRole) {
        roles.add(uamsRole);
    }

    @Override
    public Collection<String> getBrands() {
        if (isOwnByBrand()) {
            return Arrays.asList(this.brandId);
        } else {
            //这里应该是返回所有品牌
            return Collections.emptyList();
        }
    }
}
