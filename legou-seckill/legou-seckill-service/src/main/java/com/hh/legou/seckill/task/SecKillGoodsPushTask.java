package com.hh.legou.seckill.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author hh
 * @version 1.0
 * @time 04/01/2024 16:46
 */
@Component
public class SecKillGoodsPushTask {

    /**
     * 定时任务，每隔5秒执行一次
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void loadGoodsPushRedis() {
        System.out.println("hh task demo");
    }
}
