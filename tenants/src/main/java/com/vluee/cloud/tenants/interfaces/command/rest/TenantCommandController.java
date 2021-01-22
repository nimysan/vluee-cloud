package com.vluee.cloud.tenants.interfaces.command.rest;

import com.vluee.cloud.tenants.core.tenant.service.TenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api("租户操作")
@RestController
@RequestMapping("/tenants")
@AllArgsConstructor
public class TenantCommandController {

    private final TenantService tenantService;

    @ApiOperation(value = "创建租户", tags = {"租户"})
    @PostMapping
    public void register(@RequestParam String tenantName) {
        tenantService.addTenant(tenantName);
    }

    @ApiOperation(value = "更新租户名称", tags = {"租户"})
    @PutMapping("{tenantId}")
    public void changeName(@PathVariable Long tenantId, @RequestParam String tenantName) {
        tenantService.changeTenantName(tenantId, tenantName);
    }

}
