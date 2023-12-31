package com.hh.legou.seckill.task;

import com.hh.legou.common.utils.IdWorker;
import com.hh.legou.common.utils.SystemConstants;
import com.hh.legou.seckill.dao.SecKillGoodsDao;
import com.hh.legou.seckill.po.SecKillGoods;
import com.hh.legou.seckill.po.SecKillOrder;
import com.hh.legou.seckill.pojo.SecKillStatus;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 多线程下单操作
     */
    @Async
    public void creatOrder() {

        //（右）取出排队信息
        SecKillStatus secKillStatus = (SecKillStatus) redisTemplate.boundListOps(SystemConstants.SEC_KILL_USER_QUEUE_KEY).rightPop();
        //消费所有list中元素后，redis中该list就没有了
        if (secKillStatus != null) {
            Long id = secKillStatus.getGoodsId();  //商品id
            String username = secKillStatus.getUsername();  //秒杀用户
            String time = secKillStatus.getTime();  //秒杀时区

            //分布式锁
            RLock lock = redissonClient.getLock("seckillstock" + id);

            try {
                //获得分布式锁，waitTime等待锁的时间，leaseTime锁的持有时间
                lock.tryLock(20, 20, TimeUnit.SECONDS);
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
                    Thread.sleep(10000);
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
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                //释放锁
                lock.unlock();
            }
        }
    }
}
