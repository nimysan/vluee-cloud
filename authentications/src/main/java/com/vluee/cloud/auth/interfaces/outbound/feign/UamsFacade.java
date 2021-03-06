package com.vluee.cloud.auth.interfaces.outbound.feign;

import com.vluee.cloud.commons.common.ServiceConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(ServiceConstants.Services.UAMS)
public interface UamsFacade {

    @GetMapping("/users/{username}/clients/{clientId}/roles")
    public String getUserRoles(@PathVariable String username, @PathVariable String clientId);
}
