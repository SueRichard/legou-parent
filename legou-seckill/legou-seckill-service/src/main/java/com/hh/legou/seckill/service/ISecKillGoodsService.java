package com.hh.legou.seckill.service;

import com.hh.legou.core.service.ICrudService;
import com.hh.legou.seckill.po.SecKillGoods;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 05/01/2024 11:17
 */
public interface ISecKillGoodsService extends ICrudService<SecKillGoods> {

    /**
     * 获取指定时间对应的秒杀商品列表
     *
     * @param key 秒杀时区
     * @return
     */
    List<SecKillGoods> list(String key);
}
