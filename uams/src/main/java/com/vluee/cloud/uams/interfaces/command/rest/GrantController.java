package com.vluee.cloud.uams.interfaces.command.rest;

import com.vluee.cloud.uams.application.UamsApplicationService;
import com.vluee.cloud.uams.core.resource.service.RestResourceService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GrantController {

    private final RestResourceService restResourceService;

    private final UamsApplicationService applicationService;

    @ApiOperation(value = "给资源分配可访问角色", notes = "给类型为REST得资源分配可访问的角色", tags = {"授权管理"})
    @PostMapping("/grant/resources/{resourceId}/roles/{roleId}")
    public void grantRestResource(@PathVariable Long resourceId, @PathVariable Long roleId) {
        applicationService.grant(resourceId, roleId);
    }

    @ApiOperation(value = "注册REST资源", notes = "注册可管理的REST资源", tags = {"资源管理"})
    @PostMapping("/resources/rest")
    public void registerRestResource(@RequestParam String verb, @RequestParam String urlPattern, @RequestParam(required = false) String name) {
        restResourceService.createResource(verb, urlPattern, name);
    }

}
