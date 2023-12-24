package com.hh.legou.order.controller;

import com.hh.legou.core.controller.BaseController;
import com.hh.legou.core.service.ICrudService;
import com.hh.legou.order.po.Order;
import com.hh.legou.order.service.IOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hh
 * @version 1.0
 * @time 24/12/2023 17:21
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController<IOrderService, Order> {
}
