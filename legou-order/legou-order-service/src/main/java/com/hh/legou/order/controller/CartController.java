package com.hh.legou.order.controller;

import com.hh.legou.order.po.OrderItem;
import com.hh.legou.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 23/12/2023 15:10
 */
@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加购物车
     *
     * @param id  sku id
     * @param num 商品数量
     * @return
     */
    @RequestMapping("/add")
    public ResponseEntity add(Long id, Integer num) {
        //从 Spring security获取当前用户
        String username = "hh";
        cartService.add(id, num, username);
        return ResponseEntity.ok("添加成功");
    }

    /**
     * 查询购物车数据
     *
     * @return
     */
    @RequestMapping("/list")
    public ResponseEntity<List<OrderItem>> list() {
        String username = "hh";
        List<OrderItem> list = cartService.list(username);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
