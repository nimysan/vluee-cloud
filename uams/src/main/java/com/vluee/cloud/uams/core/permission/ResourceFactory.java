package com.vluee.cloud.uams.core.permission;

/**
 * 负责维护创建resource
 */
public class ResourceFactory {

    //domain 依赖外部元素了
    public Resource createApiResource(RestApi restApi, String name, String description) {
        //validation rule
        Resource resource = new Resource(Resource.ResourceType.API, name, description);
        return resource;
    }
}
