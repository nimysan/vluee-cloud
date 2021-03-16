package com.vluee.cloud.tenants.application.commands.handler;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.cqrs.command.handler.CommandHandler;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.tenants.application.commands.AddBrandCommand;
import com.vluee.cloud.tenants.core.brands.domain.Brand;
import com.vluee.cloud.tenants.core.brands.domain.BrandRepository;
import lombok.AllArgsConstructor;

@ApplicationService
@AllArgsConstructor
public class AddBrandCommandHandler implements CommandHandler<AddBrandCommand, Void> {

    private final BrandRepository brandRepository;

    @Override
    public Void handle(AddBrandCommand command) {
        Brand brand = new Brand(AggregateId.generate(), new AggregateId(command.getTenantId()), command.getBrandName());
        brandRepository.save(brand);
        return null;
    }
}
