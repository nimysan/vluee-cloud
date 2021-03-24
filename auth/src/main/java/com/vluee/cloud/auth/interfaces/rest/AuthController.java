package com.vluee.cloud.auth.interfaces.rest;

import com.vluee.cloud.commons.common.rest.AuthConstant;
import com.vluee.cloud.commons.common.rest.CommonResult;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.Map;

/**
 * 自定义Oauth2获取令牌接口 (主要是为了自定义错误信息)
 */
@RestController
@Api(tags = "AuthController", description = "认证中心登录认证")
@RequestMapping("/oauth")
@AllArgsConstructor
public class AuthController {

    private final TokenEndpoint tokenEndpoint;

    private final AuthenticationManager authenticationManager;

    @ApiOperation("Oauth2获取token")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "grant_type", value = "授权模式", required = true),
//            @ApiImplicitParam(name = "client_id", value = "Oauth2客户端ID", required = true),
//            @ApiImplicitParam(name = "client_secret", value = "Oauth2客户端秘钥", required = true),
//            @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
//            @ApiImplicitParam(name = "username", value = "登录用户名"),
//            @ApiImplicitParam(name = "password", value = "登录密码")
//    })
    @RequestMapping(value = "/rest_token", method = RequestMethod.POST)
    public CommonResult<Oauth2TokenDto> postAccessToken(@ApiIgnore Principal principal, @ApiParam @RequestBody Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        if (principal == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(parameters.get("client_id"),
                    parameters.get("client_secret"));
            principal = authenticationManager.authenticate(authRequest);
        }
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .accessToken(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead(AuthConstant.JWT_TOKEN_PREFIX).build();

        return CommonResult.success(oauth2TokenDto);
    }

    /**
     * Oauth2获取Token返回信息封装
     */
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Builder
    public static class Oauth2TokenDto {
        @ApiModelProperty("访问令牌")
        private String accessToken;
        @ApiModelProperty("刷新令牌")
        private String refreshToken;
        @ApiModelProperty("访问令牌头前缀")
        private String tokenHead;
        @ApiModelProperty("有效时间（秒）")
        private int expiresIn;
    }
}