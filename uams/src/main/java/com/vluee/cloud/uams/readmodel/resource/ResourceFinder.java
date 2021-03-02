package com.vluee.cloud.uams.readmodel.resource;

import com.vluee.cloud.commons.ddd.annotations.domain.Finder;
import com.vluee.cloud.uams.core.resources.domain.ApiResource;

import java.util.List;

@Finder
public interface ResourceFinder {
    public List<ApiResource> findAll();
}
