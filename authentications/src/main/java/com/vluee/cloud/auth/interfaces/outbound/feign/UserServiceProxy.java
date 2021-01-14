package com.vluee.cloud.auth.interfaces.outbound.feign;

import com.vluee.cloud.commons.common.ServiceConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(ServiceConstants.Services.USER)
public interface UserServiceProxy {

    @GetMapping("/users/{username}")
    UserDetailsVO loadUserByUsername(@PathVariable String username);

}
