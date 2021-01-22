package com.vluee.cloud.gateway.spring.controller;

import com.vluee.cloud.gateway.interfaces.common.CommonResult;
import com.vluee.cloud.gateway.interfaces.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 网关异常通用处理器，只作用在webflux 环境下 , 优先级低于 {@link ResponseStatusExceptionHandler} 执行
 */
@Slf4j
public class JsonErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    /**
     * Create a new {@code DefaultErrorWebExceptionHandler} instance.
     *
     * @param errorAttributes    the error attributes
     * @param resourceProperties the resources configuration properties
     * @param errorProperties    the error configuration properties
     * @param applicationContext the current application context
     */
    public JsonErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        boolean includeStackTrace = isIncludeStackTrace(request, MediaType.ALL);
        Map<String, Object> error = getErrorAttributes(request, true);
        CommonResult<Object> message = CommonResult.failed(ResultCode.FAILED, (String) error.get("message"));
        Map<String, Object> outputError = new HashMap<>();
        outputError.put("code", message.getCode());
        outputError.put("message", message.getMessage());
        // 404 错误需要特别处理一下 .......
        return ServerResponse.status(getHttpStatus(error)).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(outputError));
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }


}