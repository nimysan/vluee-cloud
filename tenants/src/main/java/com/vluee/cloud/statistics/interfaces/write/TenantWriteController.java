package com.vluee.cloud.statistics.interfaces.write;

import com.vluee.cloud.commons.common.string.StringUtils;
import com.vluee.cloud.commons.cqrs.command.Gate;
import com.vluee.cloud.statistics.application.commands.AddBrandCommand;
import com.vluee.cloud.statistics.application.commands.AddChildBrandCommand;
import com.vluee.cloud.statistics.application.commands.AddTenantCommand;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Transactional
public class TenantWriteController {

    private final Gate gate;

    /**
     * 创建租户
     *
     * @param tenantName
     */
    @ApiOperation(value = "创建租户", notes = "客户可以自行申请创建一个租户，租户创建后需要待审核后生效", tags = {"租户"})
    @PostMapping("/tenants")
    public void registerTenant(@RequestParam String tenantName) {
        gate.dispatch(new AddTenantCommand(tenantName));
    }

    @ApiOperation(value = "登记品牌", notes = "登记酒店品牌和子品牌", tags = {"品牌"})
    @PostMapping("/tenants/{tenantId}/brands")
    public void registerBrand(@PathVariable String tenantId, @RequestParam String brandName, @RequestParam(required = false) String parentId, @RequestParam String brandCode) {
        if (StringUtils.isNotEmpty(parentId)) {
            gate.dispatch(new AddChildBrandCommand(tenantId, parentId, brandName, brandCode));
        } else {
            gate.dispatch(new AddBrandCommand(tenantId, brandName, brandCode));
        }
    }

}
