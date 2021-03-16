package com.vluee.cloud.tenants.core.brands.domain;

import com.vluee.cloud.commons.ddd.annotations.domain.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

@ValueObject
@Embeddable
public class BrandProfile {

    /**
     * 品牌说明
     */
    @Column
    private String brandDescription;

    /**
     * 品牌创建时间
     */
    @Column
    private Date brandCreatedAt;
}
