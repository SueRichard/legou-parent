package com.hh.legou.order.controller;

import com.hh.legou.order.config.TokenDecode;
import com.hh.legou.order.po.OrderItem;
import com.hh.legou.order.service.ICartService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    private ICartService ICartService;

    @Autowired
    private TokenDecode tokenDecode;

    /**
     * 添加购物车
     *
     * @param id  sku id
     * @param num 商品数量
     * @return
     */
    @RequestMapping("/add")
    public ResponseEntity add(Long id, Integer num) throws IOException {
        //从 Spring security获取当前用户
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("user_name");
        ICartService.add(id, num, username);
        return ResponseEntity.ok("添加成功");
    }

    /**
     * 查询购物车数据
     *
     * @return
     */
    @RequestMapping("/list")
    public ResponseEntity<List<OrderItem>> list() throws IOException {
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("user_name");
        List<OrderItem> list = ICartService.list(username);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
