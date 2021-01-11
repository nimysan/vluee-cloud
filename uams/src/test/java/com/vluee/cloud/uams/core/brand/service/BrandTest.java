package com.vluee.cloud.uams.core.brand.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrandTest {

    @Test
    public void testBrandCreate() {
        Brand brand = Brand.builder().brandId("123").brandName("喆啡").tenantId("1").tenantName("jj").build();
        assertEquals("123", brand.getBrandId());
        assertEquals("喆啡", brand.getBrandName());
        assertEquals("1", brand.getTenantId());
        assertEquals("jj", brand.getTenantName());
    }
}