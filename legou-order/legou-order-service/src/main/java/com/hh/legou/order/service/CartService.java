package com.hh.legou.order.service;

import com.hh.legou.order.po.OrderItem;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 19/11/2023 18:35
 */
public interface CartService {
    /**
     * 添加商品到购物车
     *
     * @param skuId    商品id
     * @param num      商品数量
     * @param username 用户名，从登录令牌获取
     */
    void add(Long skuId, Integer num, String username);

    /**
     * 从redis中查询当前用户的购物车数据
     *
     * @param username
     * @return
     */
    List<OrderItem> list(String username);
}
