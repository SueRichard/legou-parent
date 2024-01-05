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

    @Override
    public SecKillGoods one(String time, Long id) {
        //注意这里从redis取值，需要和存入的类型一致，这里存入的是Long，如果这参数id里是String类型那么获取不到
        return (SecKillGoods) redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + time).get(id);
    }
}
