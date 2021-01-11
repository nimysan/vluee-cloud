package com.vluee.cloud.tenants.core.brand.domain;

import com.vluee.cloud.commons.common.data.TenancyAndAuditAware;
import com.vluee.cloud.commons.common.data.id.LongIdGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tenant_brands")
@GenericGenerator(name = LongIdGenerator.ID_GENERATOR_NAME, strategy = LongIdGenerator.DISTRIBUTED_ID_GENERATOR_CLASS_NAME)
public class Brand extends TenancyAndAuditAware {

    @Deprecated
    public Brand() {

    }

    public Brand(String brandName, Brand parent) {

    }

    public Brand(String brandName) {
        this.brandName = brandName;
    }


    @Id
    @GeneratedValue(generator = LongIdGenerator.ID_GENERATOR_NAME)
    private Serializable id;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌图标
     */
    private String brandIcon;

    /**
     * 品牌对外显示的标准名称
     */
    private String brandDisplayName;

    /**
     * 品牌英文名称
     */
    private String brandEnglishName;


    /**
     * 父品牌
     */
    @OneToOne
    private Brand parent;

    /**
     * 子品牌列表
     */
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Brand.class)
//    @JoinTable(name = "tenant_brands", joinColumns = {@JoinColumn(name = "children")})
    private Set<Brand> children = new HashSet<>(1);

    public void addChildBrand(Brand childBrand) {
        childBrand.parent = this;
        this.children.add(childBrand);
    }

    public Serializable getId() {
        return id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandIcon() {
        return brandIcon;
    }

    public void setBrandIcon(String brandIcon) {
        this.brandIcon = brandIcon;
    }

    public String getBrandDisplayName() {
        return brandDisplayName;
    }

    public void setBrandDisplayName(String brandDisplayName) {
        this.brandDisplayName = brandDisplayName;
    }

    public String getBrandEnglishName() {
        return brandEnglishName;
    }

    public void setBrandEnglishName(String brandEnglishName) {
        this.brandEnglishName = brandEnglishName;
    }

    public Brand getParent() {
        return parent;
    }

    public void setParent(Brand parent) {
        this.parent = parent;
    }

    public Set<Brand> getChildren() {
        return children;
    }

    public void setChildren(Set<Brand> children) {
        this.children = children;
    }
}
