package com.vluee.cloud.uams.core.uams.domain;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * 品牌拥有者
 */
public interface BrandOwner {

    /**
     * 返回拥有的品牌
     *
     * @return
     */
    public Collection<String> getBrands();

    /**
     * 是否拥有某品牌.
     * <p>
     * 如果标记拥有所有品牌， 则直接为true
     *
     * @param brandId 品牌Id
     * @return
     */
    default public boolean isOwnBrand(@NotNull String brandId) {
        if (isOwnAllBrands()) {
            return true;
        }
        return getBrands().contains(brandId);
    }

    /**
     * 是否拥有所有品牌. 默认都为否。
     *
     * @return
     */
    default public boolean isOwnAllBrands() {
        return false;
    }
}
