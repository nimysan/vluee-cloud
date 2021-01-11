package com.vluee.cloud.uams.core.uams.domain;

import com.vluee.cloud.commons.common.string.StringUtils;

/**
 * 品牌相关资源
 */
public interface BrandResource {

    /**
     * 获取所属的品牌
     *
     * @return
     */
    public String getBrandId();

    /**
     * 确认是否属于某个品牌
     *
     * @return
     */
    default public boolean isOwnByBrand() {
        return StringUtils.isNotEmpty(getBrandId());
    }

}
