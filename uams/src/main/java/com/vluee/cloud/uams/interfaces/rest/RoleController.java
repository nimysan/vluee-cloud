package com.vluee.cloud.uams.interfaces.rest;

import com.vluee.cloud.uams.core.uams.service.UamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private UamsService uamsService;

    @PostMapping
    public void createRole(@RequestParam String tenantId, @RequestParam String roleName) {
        uamsService.createRole(tenantId, roleName);
    }
}
