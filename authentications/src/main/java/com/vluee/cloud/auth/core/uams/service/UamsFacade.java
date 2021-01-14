package com.vluee.cloud.auth.core.uams.service;

import cn.hutool.core.convert.Convert;
import com.vluee.cloud.auth.interfaces.outbound.feign.UamsServiceProxy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UamsFacade {

    private final UamsServiceProxy uamsServiceProxy;

    public Set<String> listAuthorities(String clientId, String username) {
        String userRoles = uamsServiceProxy.getUserRoles(clientId, username);
        return Convert.toList(String.class, userRoles).stream().collect(Collectors.toSet());
    }
}
