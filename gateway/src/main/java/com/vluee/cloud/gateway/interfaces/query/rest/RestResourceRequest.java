package com.vluee.cloud.gateway.interfaces.query.rest;

import org.springframework.http.HttpMethod;

/**
 * Rest资源访问请求
 */
public interface RestResourceRequest {

    public HttpMethod getMethod();

    public String getUrl();

}
