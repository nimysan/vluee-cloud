package com.vluee.cloud.uams;

import com.vluee.cloud.uams.core.brand.service.Brand;
import com.vluee.cloud.uams.core.brand.service.BrandQueryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MockitBehaviorTest {

    @Test
    public void testMockitIoMock() {
        //一次mock可以多次执行
        BrandQueryService brandQueryService = Mockito.mock(BrandQueryService.class);
        Mockito.when(brandQueryService.getBrand("123")).thenReturn(Optional.of(new Brand("jj", "", "b1", "")));
        assertNotNull(brandQueryService.getBrand("123"));
        assertNotNull(brandQueryService.getBrand("123"));

        Mockito.reset(brandQueryService);
        Optional<Brand> brand = brandQueryService.getBrand("123");
        Assertions.assertThrows(NoSuchElementException.class, () -> brand.get());
    }
}
