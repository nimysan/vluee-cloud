package com.vluee.cloud.tenants.interfaces.write;

import com.vluee.cloud.commons.cqrs.command.Gate;
import com.vluee.cloud.tenants.application.commands.AddBrandCommand;
import com.vluee.cloud.tenants.application.commands.AddTenantCommand;
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
    @PostMapping("/tenants")
    public void registerTenant(@RequestParam String tenantName) {
        gate.dispatch(new AddTenantCommand(tenantName));
    }

    @PostMapping("/tenants/{tenantId}/brands")
    public void registerBrand(@PathVariable String tenantId, @RequestParam String brandName) {
        gate.dispatch(new AddBrandCommand(tenantId, brandName));
    }
}
