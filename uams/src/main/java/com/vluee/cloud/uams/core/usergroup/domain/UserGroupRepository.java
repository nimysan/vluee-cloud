package com.vluee.cloud.uams.core.usergroup.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import org.springframework.data.repository.CrudRepository;

public interface UserGroupRepository extends CrudRepository<UserGroup, AggregateId> {

}
