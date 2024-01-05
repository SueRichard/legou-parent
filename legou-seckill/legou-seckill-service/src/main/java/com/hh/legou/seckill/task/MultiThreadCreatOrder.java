package com.hh.legou.seckill.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author hh
 * @version 1.0
 * @time 05/01/2024 16:02
 */
@Component
public class MultiThreadCreatOrder {

    /**
     * 多线程下单操作
     */
    @Async
    public void creatOrder() {
        try {
            System.out.println("准备下单。。。。。。");
            Thread.sleep(20000);
            System.out.println("下单完成。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
