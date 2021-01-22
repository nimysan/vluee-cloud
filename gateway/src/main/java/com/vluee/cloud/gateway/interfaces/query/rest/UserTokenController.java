package com.vluee.cloud.gateway.interfaces.query.rest;

import com.vluee.cloud.gateway.interfaces.common.CommonResult;
import com.vluee.cloud.gateway.interfaces.outbound.feign.OAuth2Feign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@RestController
@AllArgsConstructor
public class UserTokenController {

    private final OAuth2Feign oAuth2Feign;

    @ApiOperation("Oauth2获取token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", value = "授权模式", required = true),
            @ApiImplicitParam(name = "client_id", value = "Oauth2客户端ID", required = true),
            @ApiImplicitParam(name = "client_secret", value = "Oauth2客户端秘钥", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
            @ApiImplicitParam(name = "username", value = "登录用户名"),
            @ApiImplicitParam(name = "password", value = "登录密码")
    })
    @PostMapping("/auth/token")
    public Mono<CommonResult> authForToken(ServerWebExchange exchange) {
        Mono<MultiValueMap<String, String>> formData = exchange.getFormData();
        return formData.map(data -> oAuth2Feign.fetchToken(data.toSingleValueMap())).map(t -> CommonResult.success(t));
    }

    /**
     * 获取访问网关的access_token
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/auth/token/gateway")
    public Mono<CommonResult> gatewayProxyToken(@RequestParam String username, @RequestParam String password) {
        java.util.Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("client_id", "wxapp");
        map.put("client_secret", "F8rQuFW71AHb");
        map.put("grant_type", "password");
        return Mono.just(CommonResult.success(oAuth2Feign.fetchToken(map)));
    }
}