package com.vluee.cloud.uams.core.brand.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

/**
 * 品牌相关服务
 */
public interface BrandQueryService {


    /**
     * 根据品牌ID查询品牌
     *
     * @param brandId 不允许为空
     * @return
     */
    @GetMapping("/brands/{id}")
    public Optional<Brand> getBrand(@NotBlank @PathVariable String brandId);

    public default void brandExists(@NotBlank String brandId) {
    }
}
