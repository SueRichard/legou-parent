package com.hh.legou.seckill;

import com.hh.legou.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author hh
 * @version 1.0
 * @time 04/01/2024 16:30
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableScheduling
public class SecKillApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecKillApplication.class, args);
    }

    /**
     * 雪花算法，生成id
     *
     * @return
     */
    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }
}
