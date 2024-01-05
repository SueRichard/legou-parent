package com.hh.legou.seckill.task;

import com.hh.legou.common.utils.IdWorker;
import com.hh.legou.common.utils.SystemConstants;
import com.hh.legou.seckill.dao.SecKillGoodsDao;
import com.hh.legou.seckill.po.SecKillGoods;
import com.hh.legou.seckill.po.SecKillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author hh
 * @version 1.0
 * @time 05/01/2024 16:02
 */
@Component
public class MultiThreadCreatOrder {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SecKillGoodsDao seckillGoodsDao;

    /**
     * 多线程下单操作
     */
    @Async
    public void creatOrder(Long id, String time, String username) {
        //获取秒杀商品信息
        SecKillGoods goods = (SecKillGoods) redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + time).get(id);

        //如果没有库存，直接抛出异常
        if (goods == null || goods.getStockCount() <= 0) throw new RuntimeException("已售罄");

        //将秒杀商品存入redis
        SecKillOrder secKillOrder = new SecKillOrder();
        secKillOrder.setId(idWorker.nextId());
        secKillOrder.setSecKillId(id);
        secKillOrder.setMoney(goods.getCostPrice());
        secKillOrder.setUserId(username);
        secKillOrder.setCreateTime(new Date());
        secKillOrder.setStatus("0");

        //模拟下单耗时操作

        try {
            System.out.println("*********开始模拟下单操作************");
            Thread.sleep(20000);
            System.out.println("*********结束模拟下单操作************");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        /**
         *秒杀订单
         * hash -> namespace:SecKillOrder
         *              key           value
         *              username   secKillOrder
         */
        redisTemplate.boundHashOps(SystemConstants.SEC_KILL_ORDER_KEY).put(username, secKillOrder);

        //库存递减
        goods.setStockCount(goods.getStockCount() - 1);

        if (goods.getStockCount() <= 0) {
            //存库
            seckillGoodsDao.updateById(goods);
            //删除redis中的秒杀商品
            redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + time).delete(id);
        } else {
            //同步redis中的库存
            redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + time).put(id, goods);
        }
    }
}
