package com.vluee.cloud.tenants.interfaces.query.rest;

import com.vluee.cloud.tenants.core.tenant.service.TenantService;
import com.vluee.cloud.tenants.interfaces.query.vo.TenantVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("租户查询操作")
@RestController
@RequestMapping("/tenants")
public class TenantQueryController {

    @Autowired
    private TenantService tenantService;

    @ApiOperation("根据租户名称查询租户")
    @GetMapping
    public TenantVo loadTenant(@RequestParam String tenantName) {
        return TenantVo.from(tenantService.getByName(tenantName));
    }
}
