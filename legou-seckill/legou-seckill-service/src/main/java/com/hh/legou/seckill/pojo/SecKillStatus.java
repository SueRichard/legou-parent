package com.hh.legou.seckill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author hh
 * @version 1.0
 * @time 05/01/2024 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecKillStatus {

    /**
     * 秒杀用户名
     */
    private String username;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 秒杀状态
     * 1：排队中，2：秒杀等待支付，3：支付超时，4：秒杀失败，5：支付完成
     */
    private Integer status;

    /**
     * 秒杀商品id
     */
    private Long goodsId;

    /**
     * 应付金额
     */
    private Float money;

    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 时间段
     */
    private String time;

}
