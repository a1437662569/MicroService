package com.lifengdi.gateway.config.sentinel;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 这里就是gateway的请求被限制后做的异常自定义处理
 */
public class GatewayHandler implements BlockRequestHandler {

    private static final String DEFAULT_BLOCK_MSG_PREFIX = "Blocked by Sentinel: ";

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        return ServerResponse.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(buildErrorResult(request.getPath().value())));
    }


    private ErrorResult buildErrorResult(Throwable ex) {
        return new ErrorResult("500", "system error of ex" + ex);
    }


    private ErrorResult buildErrorResult(String path) {
        return new ErrorResult("500", "system error of path" + path);
    }


    private static class ErrorResult {
        private final String returnCode;
        private final String errorMessage;

        public ErrorResult(String returnCode, String errorMessage) {
            this.returnCode = returnCode;
            this.errorMessage = errorMessage;
        }

        public String getReturnCode() {
            return returnCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}