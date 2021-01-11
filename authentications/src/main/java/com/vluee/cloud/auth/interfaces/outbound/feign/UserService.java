package com.vluee.cloud.auth.interfaces.outbound.feign;

import com.vluee.cloud.commons.common.AiStoreConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(AiStoreConstants.Services.USER)
public interface UserService {

    @GetMapping("/users/{username}")
    UserDetailsVO loadUserByUsername(@PathVariable String username);

}
