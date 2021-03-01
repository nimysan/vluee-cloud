package com.vluee.cloud.uams.interfaces.query.rest;

import com.vluee.cloud.uams.application.UamsApplicationService;
import com.vluee.cloud.uams.core.role.domain.CRole;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UamsController {

    private final UamsApplicationService uamsApplicationService;

    /**
     * 查询 某个用户在某个客户端得角色列表
     *
     * @param username
     * @param clientId
     * @return
     */
    @GetMapping("/users/{username}/clients/{clientId}/roles")
    public String getUserRoles(@PathVariable String username, @PathVariable String clientId) {
        return "admin,guest,super"; //TODO 真实实现
    }

    @GetMapping("roles")
    public Iterable<CRole> getRoles() {
        return uamsApplicationService.listRoles();
    }

}
