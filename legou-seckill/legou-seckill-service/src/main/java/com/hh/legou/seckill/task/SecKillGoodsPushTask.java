package com.hh.legou.seckill.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hh.legou.common.utils.DateUtil;
import com.hh.legou.common.utils.SystemConstants;
import com.hh.legou.seckill.dao.SecKillGoodsDao;
import com.hh.legou.seckill.po.SecKillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author hh
 * @version 1.0
 * @time 04/01/2024 16:46
 */
//@Component
public class SecKillGoodsPushTask {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SecKillGoodsDao secKillGoodsDao;

    /**
     * 定时任务，每隔10秒执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void loadGoodsPushRedis() {
        //获取当前时间后的秒杀间隔时间段5个
        List<Date> dateMenus = DateUtil.getDateMenus();
        for (Date dateMenu : dateMenus) {
            //当前时间获取到的时间段
            String extName = DateUtil.data2str(dateMenu, DateUtil.PATTERN_YYYYMMDDHH);
            QueryWrapper<SecKillGoods> query = Wrappers.<SecKillGoods>query();
            //库存大于0
            query.gt("stock_count_", 0);
            //审核通过
            query.eq("status_", 1);
            //等于db记录设置的时间段
            query.eq("date_menu_", extName);
            Set keys = redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + extName).keys();
            if (keys != null && keys.size() > 0) {
                //没有在redis缓存中的数据
                query.notIn("id_", keys);
            }
            List<SecKillGoods> secKillGoods = secKillGoodsDao.selectList(query);
            for (SecKillGoods secKillGood : secKillGoods) {
                //存入redis
                /**
                 * 格式：
                 * namespace:时间段1：
                 *   key           val
                 *  秒杀商品id1 秒杀商品记录1
                 *  秒杀商品id2 秒杀商品记录2
                 */
                redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + extName).put(secKillGood.getId(), secKillGood);
                //设置有效期（2小时）
            }
        }
    }
}
