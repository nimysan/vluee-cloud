package com.vluee.cloud.gateway.interfaces.outbound.feign;

import com.vluee.cloud.commons.common.ServiceConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(ServiceConstants.Services.AUTH_SERVER)
public interface OAuth2Feign {

    @PostMapping("/oauth/token")
    public AccessTokenVo fetchToken(@RequestParam java.util.Map<String, String> parameters);
}
