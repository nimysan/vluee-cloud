package com.vluee.cloud.tenants.core.brands.domain;

import cn.hutool.core.date.DateUtil;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AggregateRoot
@NoArgsConstructor // for jpa
public class Brand extends BaseAggregateRoot {

    public Brand(AggregateId aggregateId, AggregateId tenantId, String brandName) {
        this.aggregateId = aggregateId;
        this.tenantId = tenantId;
        this.brandName = brandName;
        this.createdAt = DateUtil.date();
    }

    public Brand(AggregateId aggregateId, AggregateId tenantId, Brand parentBrand, String brandName) {
        this(aggregateId, tenantId, brandName);
        this.parentBrand = parentBrand;
    }

    /**
     * 所属租户
     */
    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "aggregateId", column = @Column(name = "tenantId")))
    private AggregateId tenantId;

    /**
     * 品牌名称
     */
    @Column(nullable = false)
    private String brandName;

    /**
     * 品牌简称
     */
    @Column(nullable = true)
    private String shortName;
    /**
     * 品牌相关性质
     */
    @Embedded
    private BrandProfile brandProfile;

    /**
     * 数据创建时间
     */
    @Column
    private Date createdAt;

    /**
     * 父品牌
     */
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id", referencedColumnName = "aggregateId", nullable = true)
    private Brand parentBrand;

    /**
     * 子品牌列表
     */
    @OneToMany(mappedBy = "parentBrand")
    private List<Brand> childBrands;

    public void addChildBrand(Brand brand) {
        childBrands.add(brand);
    }
}
