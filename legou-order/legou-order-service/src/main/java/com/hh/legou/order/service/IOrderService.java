package com.hh.legou.order.service;

import com.hh.legou.core.service.ICrudService;
import com.hh.legou.order.po.Order;

/**
 * @author hh
 * @version 1.0
 * @time 24/12/2023 17:17
 */
public interface IOrderService extends ICrudService<Order> {

    /**
     * 添加订单
     * @param order
     */
    void add(Order order);
}
