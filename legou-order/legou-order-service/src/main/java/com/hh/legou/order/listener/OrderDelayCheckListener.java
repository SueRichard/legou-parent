package com.hh.legou.order.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.legou.order.po.Order;
import com.hh.legou.order.service.IOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 监听死信队列
 * 简单说：两个交换机，两个队列。
 * 先发送到延迟交换机，指定延时后发送到死信交换机，然后消费检查是否有支付，没有则取消订单，回滚库存
 *
 * @author hh
 * @version 1.0
 * @time 02/01/2024 17:20
 */
@Component
@RabbitListener(queues = "${mq.order.queue.dlx}")
public class OrderDelayCheckListener {

    @Autowired
    private IOrderService orderService;

    @RabbitHandler
    public void handlerData(String msg) {
        //获取对应的订单id，检查是否支付，没有支付则回滚库存，取消订单
        if (StringUtils.isNotEmpty(msg)) {
            Long id = Long.parseLong(msg);
            Order order = orderService.getById(id);
            //待检查是否支付

            order.setOrderStatus("3");
            orderService.updateById(order);

            //没有支付，回滚库存，取消订单
        }
    }
}
