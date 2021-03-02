package com.vluee.cloud.uams.readmodel.jpa;

import com.vluee.cloud.commons.ddd.support.domain.FinderImpl;
import com.vluee.cloud.uams.core.permission.ApiResource;
import com.vluee.cloud.uams.readmodel.resource.ResourceFinder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@FinderImpl
public class JpaResourceFinder implements ResourceFinder {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ApiResource> findAll() {
        return entityManager.createQuery("from ApiResource").getResultList();
    }
}
