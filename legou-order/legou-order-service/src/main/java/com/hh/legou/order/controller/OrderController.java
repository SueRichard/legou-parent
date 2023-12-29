package com.hh.legou.order.controller;

import com.hh.legou.core.controller.BaseController;
import com.hh.legou.core.service.ICrudService;
import com.hh.legou.order.config.TokenDecode;
import com.hh.legou.order.po.Order;
import com.hh.legou.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author hh
 * @version 1.0
 * @time 24/12/2023 17:21
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController<IOrderService, Order> {

    @Autowired
    private TokenDecode tokenDecode;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Order order) throws IOException {
        String username = tokenDecode.getUserInfo().get("user_name");
        order.setUsername(username);
        service.add(order);
        return ResponseEntity.ok("添加成功");
    }
}
