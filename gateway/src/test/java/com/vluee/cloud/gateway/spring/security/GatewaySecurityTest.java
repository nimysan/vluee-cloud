package com.vluee.cloud.gateway.spring.security;

import com.vluee.cloud.gateway.core.rbac.AuthorizeResource;
import com.vluee.cloud.gateway.core.rbac.AuthorizeResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.UUID;

@SpringBootTest
@AutoConfigureWebTestClient
@Slf4j
public class GatewaySecurityTest {


    @Autowired
    private AuthorizeResourceRepository rbacResourceRepository;

    @Autowired
    private GatewayAuthorizationManager rbacResourceService;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    void CleanRules(){
        //删除所有
        rbacResourceRepository.findAll().toStream().forEach(t-> rbacResourceRepository.delete(Mono.just(t.getId())).block());
        rbacResourceService.updateAuthorizations().block();
        log.info("----- clean operation ------");
    }

    @Test
    @DisplayName("/jue是测试永远无法获得授权的资源")
    void jueTest() {
        webTestClient.get().uri("/jue").exchange().expectStatus().isUnauthorized();
    }

    @Test
    @DisplayName("OPTIONS可以直接被访问")
    void optionsRequest() {
        webTestClient.options().uri("/options_test").exchange().expectStatus().isOk();
    }


    @DisplayName("根节点不允许未授权访问")
    @Test
    void exampleTest(@Autowired WebTestClient webClient) {
        webClient.get().uri("/").exchange().expectStatus().isUnauthorized();
    }

    @DisplayName("Swagger相关资源允许未授权访问")
    @Test
    void verifySwaggerPermitAll() {
        webTestClient.get().uri("/swagger-ui.html").exchange().expectStatus().isOk();
    }

    @DisplayName("验证配置授权后，正常获取授权")
    @Test
    @WithMockUser(authorities = {"manager"})
    void verifyGetResourceAuthorized() {
        webTestClient.get().uri("/task/123").exchange().expectStatus().isForbidden();
        AuthorizeResource build = AuthorizeResource.builder().id(mockId()).verb("GET").url("/task/123").authorities(Arrays.asList("manager", "task")).build();
        rbacResourceRepository.save(Mono.just(build)).block();
        //force refresh authorization
        rbacResourceService.updateAuthorizations().block();
        webTestClient.get().uri("/task/123").exchange().expectStatus().isOk();
    }

    @DisplayName("验证无论HTTP动词为空，正常通配授权")
    @Test
    @WithMockUser(authorities = {"manager"})
    void verifyGrantWithoutHttpVerb() {
        webTestClient.get().uri("/task/123").exchange().expectStatus().isForbidden();
        AuthorizeResource build = AuthorizeResource.builder().id(mockId()).url("/task/123").authorities(Arrays.asList("manager", "task")).build();
        rbacResourceRepository.save(Mono.just(build)).block();
        //force refresh authorization
        rbacResourceService.updateAuthorizations().block();
        webTestClient.get().uri("/task/123").exchange().expectStatus().isOk();
    }

    @DisplayName("验证任务创建授权")
    @Test
    @WithMockUser(authorities = {"manager"})
    void verifyTaskCreateGrant() {
        webTestClient.post().uri("/task").exchange().expectStatus().isForbidden();
        AuthorizeResource build = AuthorizeResource.builder().id(mockId()).verb("POST").url("/task").authorities(Arrays.asList("task_creator")).build();
        rbacResourceRepository.save(Mono.just(build)).block();
        //force refresh authorization
        rbacResourceService.updateAuthorizations().block();
        webTestClient.post().uri("/task").exchange().expectStatus().isForbidden();

        build.setAuthorities(Arrays.asList("manager"));
        rbacResourceRepository.save(Mono.just(build)).block();
        //force refresh authorization
        rbacResourceService.updateAuthorizations().block();
        webTestClient.post().uri("/task").exchange().expectStatus().isOk();
    }

    @DisplayName("验证单个授权更新并生效")
    @Test
    @WithMockUser(authorities = {"manager"})
    void verifySingleRbacResource(){
        webTestClient.post().uri("/task").exchange().expectStatus().isForbidden();
        AuthorizeResource build = AuthorizeResource.builder().id(mockId()).verb("POST").url("/task").authorities(Arrays.asList("task_creator")).build();
        rbacResourceRepository.save(Mono.just(build)).block();
        //force refresh authorization
        rbacResourceService.handleRefresh();
        webTestClient.post().uri("/task").exchange().expectStatus().isForbidden();

        build.setAuthorities(Arrays.asList("manager"));
        rbacResourceRepository.save(Mono.just(build)).block();
        //force refresh authorization
        rbacResourceService.updateAuthorization(build.getId()).block();
        webTestClient.post().uri("/task").exchange().expectStatus().isOk();
    }

    private StatusAssertions postTaskOperation() {
        return webTestClient.post().uri("/task").exchange().expectStatus();
    }

    String mockId() {
        return UUID.randomUUID().toString();
    }

}
