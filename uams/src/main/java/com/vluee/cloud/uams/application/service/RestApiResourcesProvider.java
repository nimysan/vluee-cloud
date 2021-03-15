package com.vluee.cloud.uams.application.service;

import com.vluee.cloud.uams.core.resources.domain.RestApi;

import java.util.List;

public interface RestApiResourcesProvider {
    List<RestApi> apis();
}
