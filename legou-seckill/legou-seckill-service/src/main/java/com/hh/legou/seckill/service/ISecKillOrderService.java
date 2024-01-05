package com.hh.legou.seckill.service;

import com.hh.legou.core.service.ICrudService;
import com.hh.legou.seckill.po.SecKillOrder;

/**
 * @author hh
 * @version 1.0
 * @time 05/01/2024 11:18
 */
public interface ISecKillOrderService extends ICrudService<SecKillOrder> {

    /**
     * 添加秒杀订单
     *
     * @param id       商品id
     * @param time     商品秒杀开始时间
     * @param username 用户登录名
     * @return
     */
    Boolean add(Long id, String time, String username);
}
