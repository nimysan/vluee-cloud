package com.vluee.cloud.statistics.core.brands.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;

public interface BrandRepository {
    
    public void save(Brand brand);

    public Brand load(AggregateId brandId);
}
