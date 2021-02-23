package com.vluee.cloud.uams.core.permission;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import org.springframework.data.repository.CrudRepository;

public interface GrantRepository extends CrudRepository<Grant, AggregateId> {
}
