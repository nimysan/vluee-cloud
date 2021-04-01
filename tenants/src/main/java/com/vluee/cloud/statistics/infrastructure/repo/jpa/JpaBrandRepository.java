package com.vluee.cloud.statistics.infrastructure.repo.jpa;

import com.vluee.cloud.commons.ddd.annotations.domain.DomainRepositoryImpl;
import com.vluee.cloud.commons.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;
import com.vluee.cloud.statistics.core.brands.domain.Brand;
import com.vluee.cloud.statistics.core.brands.domain.BrandRepository;

@DomainRepositoryImpl
public class JpaBrandRepository extends GenericJpaRepository<Brand> implements BrandRepository {
}
