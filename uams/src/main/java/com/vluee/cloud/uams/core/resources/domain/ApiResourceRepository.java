package com.vluee.cloud.uams.core.resources.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;

public interface ApiResourceRepository {

    public void save(ApiResource resource);

    public ApiResource load(AggregateId id);
}
