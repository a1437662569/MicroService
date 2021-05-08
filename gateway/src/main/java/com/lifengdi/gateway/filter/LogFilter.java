package com.lifengdi.gateway.filter;

import com.lifengdi.gateway.constant.OrderedConstant;
import com.lifengdi.gateway.log.CacheServerHttpRequestDecorator;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author: Li Fengdi
 * @date: 2020-03-17 18:17
 *  Combined Global Filter and GatewayFilter Ordering
 *  https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#gateway-combined-global-filter-and-gatewayfilter-ordering
 *  这里其实就是配置一个过滤链而已： filter chain
 */
//@Component
public class LogFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        CacheServerHttpRequestDecorator cacheServerHttpRequestDecorator = new CacheServerHttpRequestDecorator(exchange.getRequest());

        return chain.filter(exchange.mutate().request(cacheServerHttpRequestDecorator).build());
    }

    @Override
    public int getOrder() {
        return OrderedConstant.LOGGING_FILTER;
    }
}
