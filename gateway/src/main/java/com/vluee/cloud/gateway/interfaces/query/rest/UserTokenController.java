package com.vluee.cloud.gateway.interfaces.query.rest;

import com.vluee.cloud.gateway.interfaces.common.CommonResult;
import com.vluee.cloud.gateway.interfaces.outbound.feign.OAuth2Feign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;

@RestController
@AllArgsConstructor
@Api(value = "网关本身管理", tags = {"网关管理"})
public class UserTokenController {

    private final OAuth2Feign oAuth2Feign;

    /**
     * 正常不使用该接口， 建议相关方直接使用auth-server的认证接口
     *
     * @param exchange
     * @return
     */
    @ApiIgnore
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
    @ApiOperation(value = "通用用户名和密码获取网关认证token", response = CommonResult.class)
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