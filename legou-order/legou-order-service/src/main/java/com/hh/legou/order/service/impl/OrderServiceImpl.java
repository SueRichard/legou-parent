package com.hh.legou.order.service.impl;

import com.hh.legou.core.service.impl.CrudServiceImpl;
import com.hh.legou.order.po.Order;
import com.hh.legou.order.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * @author hh
 * @version 1.0
 * @time 24/12/2023 17:20
 */
@Service
public class OrderServiceImpl extends CrudServiceImpl<Order> implements IOrderService {

    @Override
    public void add(Order order) {

    }
}
