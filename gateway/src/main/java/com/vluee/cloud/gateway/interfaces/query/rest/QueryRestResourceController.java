package com.vluee.cloud.gateway.interfaces.query.rest;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@AllArgsConstructor
@Slf4j
public class QueryRestResourceController {

    private final RouteLocator routeLocator;

    private final WebClient webClient;

    @GetMapping("/resources/gateway/apis")
    public Flux<RestResourceForGrant> exploreRestResource() {
        return routeLocator.getRoutes().map(t -> t.getUri().getHost()).filter(t -> t.equalsIgnoreCase("saas-uams")).flatMap(route -> fetchWebEndpoints(route)).filter(Objects::nonNull).flatMapIterable(t -> t.convertForGrants());
    }

    private Flux<SimpleRestResource> fetchWebEndpoints(String serviceInstanceName) {
        String url = "http://localhost:8080/" + serviceInstanceName + "/actuator/mappings";
        log.info("Fetch serviceInstanceName {}", url);
        return webClient.get().uri(url).accept(MediaType.APPLICATION_JSON).exchange().flatMapMany(t -> parseBody(t));
    }

    private Flux<SimpleRestResource> parseBody(ClientResponse clientResponse) {
        if (clientResponse.statusCode().is2xxSuccessful()) {
            return clientResponse.bodyToMono(String.class).map(result -> {
                return this.parseMappings(result);
            }).flatMapMany(Flux::fromIterable);
        } else {
            return Flux.empty();
        }
    }

    private List<SimpleRestResource> parseMappings(String actuatorMappingInput) {
        JSONObject jsonObject = JSONUtil.parseObj(actuatorMappingInput);
        JSONObject contexts = jsonObject.getJSONObject("contexts");
        Set<String> strings = contexts.keySet();
        List<SimpleRestResource> restResources = new ArrayList<>();
        for (String key : strings) {
            if (key.equalsIgnoreCase("bootstrap")) {
                continue;
            }
            JSONObject mappingContext = contexts.getJSONObject(key);
            if (mappingContext.containsKey("mappings")) {
                JSONArray servlets = mappingContext.getByPath("mappings.dispatcherServlets.dispatcherServlet", JSONArray.class);
                if (servlets != null) {
                    List<JSONObject> jsonObjects = servlets.toList(JSONObject.class);
                    for (JSONObject object : jsonObjects) {
                        JSONArray jsonArray = object.getByPath("details.requestMappingConditions.methods", JSONArray.class);
                        List<String> methods = JSONUtil.toList(jsonArray, String.class);

                        JSONArray patternArray = object.getByPath("details.requestMappingConditions.patterns", JSONArray.class);
                        List<String> patterns = JSONUtil.toList(patternArray, String.class);
                        if (methods != null && patterns != null) {
                            restResources.add(SimpleRestResource.builder().urls(patterns).methods(methods).build());
                        }
                    }
                }
            }
        }

        return restResources;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String utf8 = IoUtil.read(new FileInputStream("c:\\temp\\q1.json"), "UTF8");
        JSONObject jsonObject = JSONUtil.parseObj(utf8);
        JSONObject contexts = jsonObject.getJSONObject("contexts");
        Set<String> strings = contexts.keySet();
        List<SimpleRestResource> restResources = new ArrayList<>();
        for (String key : strings) {
            if (key.equalsIgnoreCase("bootstrap")) {
                continue;
            }
            JSONObject mappingContext = contexts.getJSONObject(key);
            if (mappingContext.containsKey("mappings")) {
                JSONArray servlets = mappingContext.getByPath("mappings.dispatcherServlets.dispatcherServlet", JSONArray.class);
                if (servlets != null) {
                    List<JSONObject> jsonObjects = servlets.toList(JSONObject.class);
                    for (JSONObject object : jsonObjects) {
                        JSONArray jsonArray = object.getByPath("details.requestMappingConditions.methods", JSONArray.class);
                        List<String> methods = JSONUtil.toList(jsonArray, String.class);

                        JSONArray patternArray = object.getByPath("details.requestMappingConditions.patterns", JSONArray.class);
                        List<String> patterns = JSONUtil.toList(patternArray, String.class);
                        if (methods != null && patterns != null) {
                            restResources.add(SimpleRestResource.builder().urls(patterns).methods(methods).build());
                        }
                    }
                }
            }
        }

        log.info(JSONUtil.toJsonPrettyStr(jsonObject));
    }
}
