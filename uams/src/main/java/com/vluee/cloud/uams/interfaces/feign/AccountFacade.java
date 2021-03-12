package com.vluee.cloud.uams.interfaces.feign;

import com.vluee.cloud.commons.common.ServiceConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ServiceConstants.Services.AUTH_SERVER)
public interface AccountFacade {
    @PostMapping("/user-accounts")
    public void createAccount(@RequestParam String userId, @RequestParam String username, @RequestParam String password);
}
