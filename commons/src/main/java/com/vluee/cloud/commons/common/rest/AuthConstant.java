package com.vluee.cloud.commons.common.rest;

import com.google.common.base.Joiner;
import org.springframework.http.HttpMethod;
import org.springframework.util.PathMatcher;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 权限相关常量定义
 * Created by macro on 2020/6/19.
 */
public final class AuthConstant {

    /**
     * JWT存储权限前缀
     */
    public static String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT存储权限属性
     */
    public static String AUTHORITY_CLAIM_NAME = "authorities";

    /**
     * Redis缓存权限规则key
     */
    public static String RESOURCE_ROLES_MAP_KEY = "auth:resourceRolesMap";

    public static String USER_ROLES_MAP_KEY = "auth:userRolesMap";

    public static String API_ROLES_MAP_KEY = "auth:apiRolesMap";


    /**
     * 认证信息Http请求头
     */
    public static String JWT_TOKEN_HEADER = "Authorization";

    /**
     * JWT令牌前缀
     */
    public static String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * 用户信息Http请求头
     */
    public static String USER_TOKEN_HEADER = "user";

    public static String apiCacheKey(String verb, String urlPattern) {
        return new StringBuilder().append(verb).append(" ").append(urlPattern).toString();
    }

    public static boolean isMatched(String cacheKey, HttpMethod httpMethod, String url, PathMatcher pathMatcher) {
        try {
            String[] split = cacheKey.split("\\s+");
            return pathMatcher.match(split[1], url.toLowerCase()) && isVerbMatch(split[0], httpMethod);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 组装支持多个方法的缓存key
     **/
    public static String composeVerbsKey(List<String> verbs) {
        verbs.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });
        return Joiner.on(",").join(verbs);
    }

    private static boolean isVerbMatch(String keyVerb, HttpMethod method) {
        return Arrays.stream(keyVerb.split(",")).anyMatch(t -> t.equalsIgnoreCase(method.name()));
    }

    private static boolean isMatch(String keyUrl, String requestUrl, PathMatcher pathMatcher) {
        return pathMatcher.match(keyUrl, requestUrl);
    }
}
