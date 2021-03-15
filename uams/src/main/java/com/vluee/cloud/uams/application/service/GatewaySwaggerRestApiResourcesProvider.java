package com.vluee.cloud.uams.application.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.vluee.cloud.commons.common.rest.AuthConstant;
import com.vluee.cloud.commons.ddd.annotations.domain.DomainService;
import com.vluee.cloud.uams.core.resources.domain.RestApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@DomainService
@Slf4j
public class GatewaySwaggerRestApiResourcesProvider implements RestApiResourcesProvider {
    //通过读取swagger api来做管理
    //http://localhost:8080/swagger-resources
    //http://localhost:8080/saas-uams/v2/api-docs

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${saas.gateway.url}")
    private String gatewayUrl;

    @Override
    public List<RestApi> apis() {
        List<RestApi> restApis = new ArrayList<>();
        String forObject = restTemplate.getForObject(gatewayUrl + "/swagger-resources", String.class);
        List<SwaggerResource> swaggerResources = JSONUtil.parseArray(forObject).toList(SwaggerResource.class);
        for (SwaggerResource swaggerResource : swaggerResources) {
            String url = swaggerResource.getUrl();
            String serviceName = swaggerResource.getName();
            String v2ApiDocString = restTemplate.getForObject(gatewayUrl + url, String.class);
            JSONObject docDesc = JSONUtil.parseObj(v2ApiDocString);
            JSONObject paths = docDesc.getJSONObject("paths");
            for (String path : paths.keySet()) {
                JSONObject detail = paths.getJSONObject(path);
                Set<String> strings = detail.keySet();// only fetch the first one
                String verbKey = AuthConstant.composeVerbsKey(strings.stream().collect(Collectors.toList()));
                RestApi restApi = new RestApi(verbKey, "/" + serviceName + translatePattern(path));
                restApis.add(restApi);
                log.info("The final REST API is: {}", restApi);
            }
        }
        return restApis;
    }

    private String translatePattern(final String path) {
        return Arrays.stream(path.split("/")).map(t -> {
            if (t.startsWith("{") && t.endsWith("}")) {
                return "*";
            } else {
                return t;
            }
        }).collect(Collectors.joining("/"));
    }


}
