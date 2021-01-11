package com.vluee.cloud.uams.infrastructure.dependencies;

import com.vluee.cloud.uams.core.brand.service.BrandQueryService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * 调取微服务
 * <p>
 * 请参考： https://docs.spring.io/spring-cloud-openfeign/docs/2.2.5.RELEASE/reference/html/#spring-cloud-feign
 */
@Component
@FeignClient("tenants")
public interface RestBrandQueryService extends BrandQueryService {

}
