package com.vluee.cloud.tenants.interfaces.command.rest;

import com.vluee.cloud.tenants.core.tenant.service.TenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("租户操作")
@RestController
@RequestMapping("/tenants")
public class TenantCommandController {

    @Autowired
    private TenantService tenantService;

    @ApiOperation("创建租户")
    @PostMapping
    public void register(@RequestParam String tenantName) {
        tenantService.addTenant(tenantName);
    }

    @ApiOperation("修改租户名称")
    @PutMapping("{tenantId}")
    public void changeName(@PathVariable Long tenantId, @RequestParam String tenantName) {
        tenantService.changeTenantName(tenantId, tenantName);
    }

}
