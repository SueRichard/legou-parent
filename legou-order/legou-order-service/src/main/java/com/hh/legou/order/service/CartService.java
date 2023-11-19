package com.hh.legou.order.service;

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
}
