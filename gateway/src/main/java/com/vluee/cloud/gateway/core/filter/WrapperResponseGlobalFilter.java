package com.vluee.cloud.gateway.core.filter;

import cn.hutool.json.JSONUtil;
import com.vluee.cloud.gateway.interfaces.common.CommonResult;
import com.vluee.cloud.gateway.spring.controller.CustomExceptionHandlerConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;


/**
 * 处理统一的返回结果需要分两种情况
 * <p/>
 * 1. proxy的请求
 * <p>
 * <p/>
 * 2. gateway自身的controller的请求
 *
 * @see org.springframework.cloud.gateway.filter.GlobalFilter
 *
 * @see org.springframework.cloud.gateway.filter.NettyRoutingFilter
 *
 * @see CustomExceptionHandlerConfiguration
 */
@Component
@Slf4j
public class WrapperResponseGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        // -1 is response write filter, must be called before that
        return -2;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //忽略 swagger 文档相关url
        if (exchange.getRequest().getURI().getPath().contains("/v2/api-docs")) {
            return chain.filter(exchange);
        }
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        // probably should reuse buffers
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        //释放掉内存
                        DataBufferUtils.release(dataBuffer);
                        String s = new String(content, Charset.forName("UTF-8"));
                        //尽量返回直接json格式， 如果无法正常解析的， 则返回直接字符串
                        Object data = null;
                        try {
                            data = JSONUtil.parse(s);
                        } catch (Exception e) {
                            data = s;
                        }
                        byte[] uppedContent = JSONUtil.toJsonStr(CommonResult.success(data)).getBytes(Charset.forName("UTF-8"));
                        return bufferFactory.wrap(uppedContent); //TODO /sample/simple 不正常，需要处理
                    }));
                }
                // if body is not a flux. never got there.
                return super.writeWith(body);
            }
        };
        // replace response with decorator
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }
}
