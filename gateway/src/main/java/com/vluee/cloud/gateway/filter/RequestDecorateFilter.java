package com.vluee.cloud.gateway.filter;

import com.vluee.cloud.gateway.filter.trace.TraceIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 *
 * 自动给请求加上X-Trace-Id header 方便后续追踪
 * 
 * @author SeanYe
 *
 */
@Order
//@Component
public class RequestDecorateFilter implements WebFilter {
	public static final String X_TRACE_ID = "X-Trace-Id";

	@Autowired
	private TraceIdService longIdService;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest requestWithApiVersionHeader = exchange.getRequest().mutate()
				.header(X_TRACE_ID, longIdService.nextId().toString()).build();
		return chain.filter(exchange.mutate().request(requestWithApiVersionHeader).build());
	}
}
