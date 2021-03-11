package com.vluee.cloud.gateway.spring.security.authorization;

import com.vluee.cloud.gateway.interfaces.query.rest.RestResourceRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpMethod;
import org.springframework.web.server.ServerWebExchange;

@AllArgsConstructor
@Builder
public class ServerWebExchangeDelegate implements RestResourceRequest {

    private final ServerWebExchange webExchange;

    @Override
    public HttpMethod getMethod() {
        return webExchange.getRequest().getMethod();
    }

    @Override
    public String getUrl() {
        return webExchange.getRequest().getURI().getPath();
    }

    public String getApiKey() {
        return new StringBuilder().append(getMethod()).append(" ").append(getUrl()).toString();
    }
}
