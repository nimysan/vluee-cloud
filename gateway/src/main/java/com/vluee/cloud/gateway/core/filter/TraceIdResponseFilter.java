package com.vluee.cloud.gateway.core.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//@Component
public class TraceIdResponseFilter implements GlobalFilter, Ordered {

	@Override
	public int getOrder() {
		return -3;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpResponse originalResponse = exchange.getResponse();
		DataBufferFactory bufferFactory = originalResponse.bufferFactory();
		ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse);
		// 将Request中的traceId加入到response里面去
		decoratedResponse.getHeaders().add(RequestDecorateFilter.X_TRACE_ID,
				exchange.getRequest().getHeaders().getFirst(RequestDecorateFilter.X_TRACE_ID));
		return chain.filter(exchange.mutate().response(decoratedResponse).build()); // replace response with decorator
	}

}
