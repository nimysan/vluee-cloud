package com.vluee.cloud.statistics.application.commands.handler;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.cqrs.command.handler.CommandHandler;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.statistics.application.commands.AddChildBrandCommand;
import com.vluee.cloud.statistics.core.brands.domain.Brand;
import com.vluee.cloud.statistics.core.brands.domain.BrandRepository;
import lombok.AllArgsConstructor;

@ApplicationService
@AllArgsConstructor
public class AddChildBrandCommandHandler implements CommandHandler<AddChildBrandCommand, Void> {

    private final BrandRepository brandRepository;

    @Override
    public Void handle(AddChildBrandCommand command) {
        String parentBrandId = command.getParentBrandId();
        Brand parentBrand = brandRepository.load(new AggregateId(parentBrandId));
        Brand brand = new Brand(AggregateId.generate(), new AggregateId(command.getTenantId()), parentBrand, command.getBrandName());
        parentBrand.addChildBrand(brand);
        brandRepository.save(brand);
        return null;
    }
}
