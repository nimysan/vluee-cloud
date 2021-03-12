package com.vluee.cloud.gateway.interfaces.feign;

import com.vluee.cloud.commons.common.ServiceConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(ServiceConstants.Services.UAMS)
public interface UamsFacade {

    @PostMapping("/resources/apis")
    public void registerApi(@RequestParam String verb, @RequestParam String url);

}
