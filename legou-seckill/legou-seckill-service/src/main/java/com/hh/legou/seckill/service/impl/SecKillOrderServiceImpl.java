package com.hh.legou.seckill.service.impl;

import com.hh.legou.common.utils.IdWorker;
import com.hh.legou.common.utils.SystemConstants;
import com.hh.legou.core.service.impl.CrudServiceImpl;
import com.hh.legou.seckill.dao.SecKillGoodsDao;
import com.hh.legou.seckill.po.SecKillGoods;
import com.hh.legou.seckill.po.SecKillOrder;
import com.hh.legou.seckill.pojo.SecKillStatus;
import com.hh.legou.seckill.service.ISecKillOrderService;
import com.hh.legou.seckill.task.MultiThreadCreatOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author hh
 * @version 1.0
 * @time 05/01/2024 11:19
 */
@Service
public class SecKillOrderServiceImpl extends CrudServiceImpl<SecKillOrder> implements ISecKillOrderService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MultiThreadCreatOrder multiThreadCreateOrder;

    @Override
    public Boolean add(Long id, String time, String username) {
        SecKillStatus secKillStatus = new SecKillStatus(username, new Date(), 1, id, time);
        //保证公平性，队列削峰的一种方式
        //将秒杀排队信息，leftPush存入redis的list队列中，左压右取
        redisTemplate.boundListOps(SystemConstants.SEC_KILL_USER_QUEUE_KEY).leftPush(secKillStatus);
        //多线程抢单
        multiThreadCreateOrder.creatOrder();
        return true;
    }
}
