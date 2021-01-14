package com.vluee.cloud.uams.interfaces.query.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UamsController {

    /**
     * 查询 某个用户在某个客户端得角色列表
     *
     * @param username
     * @param clientId
     * @return
     */
    @GetMapping("/users/{username}/clients/${clientId}/roles")
    public String getUserRoles(@PathVariable String username, @PathVariable String clientId) {
        return "admin,guest,super"; //TODO 真实实现
    }
}
