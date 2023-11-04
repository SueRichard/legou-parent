package com.hh.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author hh
 * @version 1.0
 * @time 04/11/2023 11:49
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * 使用ip限流，ip作为key
     *
     * @return
     */
    @Bean(name = "ipKeyResolver")
    public KeyResolver userKeyResolver() {
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                String hostAddress = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
                System.out.println(hostAddress);
                return Mono.just(hostAddress);
            }
        };
    }
}
