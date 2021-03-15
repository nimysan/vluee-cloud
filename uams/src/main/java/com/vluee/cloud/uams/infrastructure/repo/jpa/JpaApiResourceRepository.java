package com.vluee.cloud.uams.infrastructure.repo.jpa;

import com.vluee.cloud.commons.ddd.annotations.domain.DomainRepositoryImpl;
import com.vluee.cloud.commons.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;
import com.vluee.cloud.uams.core.resources.domain.ApiResource;
import com.vluee.cloud.uams.core.resources.domain.ApiResourceRepository;

import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

@DomainRepositoryImpl
public class JpaApiResourceRepository extends GenericJpaRepository<ApiResource> implements ApiResourceRepository {
    @Override
    public List<ApiResource> loadByUrl(String url) {
        Query query = entityManager.createQuery("from ApiResource ar where ar.restApi.url=:url");
        query.setParameter("url", url);
        return query.getResultList();
    }
}
