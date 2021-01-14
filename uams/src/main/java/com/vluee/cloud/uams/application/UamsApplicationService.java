package com.vluee.cloud.uams.application;

import com.vluee.cloud.commons.common.uams.RestResourceDescriptor;

import javax.validation.constraints.NotNull;

/**
 * DDD application service
 */
public class UamsApplicationService {

    public String getRoles(String username, String clientId) {
        return "admin,guest";
    }

    private RestResourceDescriptor apiPermission;

    /**
     * 提交API类型的资源。 比如Gateway, 可以提交REST　API的访问请求资源
     *
     * @param verb                对应 Http动词
     * @param resourceUrl         REST Resource
     * @param name                名称
     * @param description         描述
     * @param resourceContributor 资源贡献者
     */
    public void submitApiResource(@NotNull final String verb, @NotNull String resourceUrl, @NotNull String name, String description, @NotNull String resourceContributor) {

    }

    /**
     * 提供通配方法
     *
     * @param resourceUrl
     * @param name
     * @param description
     */
    public void submitApiResource(String resourceUrl, String name, String description, String resourceContributor) {

    }
}
