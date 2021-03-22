package com.vluee.cloud.tenants.application.commands.handler;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.cqrs.command.handler.CommandHandler;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.tenants.application.commands.AddTenantCommand;
import com.vluee.cloud.tenants.core.tenants.domain.Tenant;
import com.vluee.cloud.tenants.core.tenants.domain.TenantRepository;
import lombok.AllArgsConstructor;

@ApplicationService
@AllArgsConstructor
public class AddTenantCommandHandler implements CommandHandler<AddTenantCommand, Void> {

    private final TenantRepository tenantRepository;

    @Override
    public Void handle(AddTenantCommand command) {
        Tenant tenant = new Tenant(AggregateId.generate(), command.getTenantName());
        tenantRepository.save(tenant);
        return null;
    }
}
