package com.hh.legou.order.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.legou.order.service.IOrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author hh
 * @version 1.0
 * @time 02/01/2024 17:20
 */
@Component
@RabbitListener(queues = "${mq.pay.queue.order}")
public class OrderPayStatusListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IOrderService orderService;

    @RabbitHandler
    public void handlerData(String msg) {
        //反序列化消息数据
        Map<String, String> map = null;
        try {
            map = objectMapper.readValue(msg, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (map != null) {
            if ("TRADE_SUCCESS".equals(map.get("trade_status"))) {
                orderService.updatePayStatus(map.get("out_trade_no"), map.get("trade_no"));
            } else {
                //删除订单，支付失败，回滚库存
            }

        }
    }
}
