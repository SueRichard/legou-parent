package com.hh.legou.order.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author hh
 * @version 1.0
 * @time 03/01/2024 09:16
 */
@Configuration
public class MqConfig {

    @Autowired
    private Environment env;

    /**
     * 延迟交换机
     *
     * @return
     */
    @Bean
    public Exchange ttlExchange() {
        return ExchangeBuilder.directExchange(env.getProperty("mq.order.exchange.ttl")).durable(true).build();
    }

    /**
     * 死信交换机
     *
     * @return
     */
    @Bean
    public Exchange dlxExchange() {
        return ExchangeBuilder.directExchange(env.getProperty("mq.order.exchange.dlx")).durable(true).build();
    }

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue dlxQueue() {
        return QueueBuilder.durable(env.getProperty("mq.order.queue.dlx")).build();
    }

    /**
     * 正常队列
     *
     * @return
     */
    @Bean
    public Queue ttlQueue() {
        return QueueBuilder.durable(env.getProperty("mq.order.queue.ttl"))
                //队列过期时间，15秒
                .withArgument("x-message-ttl", 15000)
                //指定死信交换机
                .withArgument("x-dead-letter-exchange", env.getProperty("mq.order.exchange.dlx"))
                //指定路由key，需要和绑定死信交换机的路由key一致
                .withArgument("x-dead-letter-routing-key", env.getProperty("mq.order.routing.dlx"))
                .build();
    }

    /**
     * 正常队列、交换机绑定
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding ttlBinding(@Qualifier("ttlQueue") Queue queue, @Qualifier("ttlExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(env.getProperty("mq.order.routing.ttl")).noargs();
    }

    /**
     * 绑定私信队列和死信交换机
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding dlxBinding(@Qualifier("dlxQueue") Queue queue, @Qualifier("dlxExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(env.getProperty("mq.order.routing.dlx")).noargs();
    }
}
