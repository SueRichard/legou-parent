package com.hh.legou.seckill.service.impl;

import com.hh.legou.common.utils.SystemConstants;
import com.hh.legou.core.service.impl.CrudServiceImpl;
import com.hh.legou.seckill.po.SecKillGoods;
import com.hh.legou.seckill.service.ISecKillGoodsService;
import com.hh.legou.seckill.service.ISecKillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 05/01/2024 11:18
 */
@Service
public class SecKillGoodsServiceImpl extends CrudServiceImpl<SecKillGoods> implements ISecKillGoodsService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<SecKillGoods> list(String key) {
        return redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + key).values();
    }
}
