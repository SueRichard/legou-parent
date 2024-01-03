package com.hh.legou.seckill.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hh.legou.core.po.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author HH
 * @version 1.0
 * @time 2024/01/04  Thu  00:23
 */
@Data
@TableName("seckill_order_")
public class SecKillOrder extends BaseEntity {

    /**
     * 秒杀商品id
     */
    @TableField("seckill_id_")
    private Long secKillId;

    /**
     * 支付金额
     */
    @TableField("money_")
    private String money;

    /**
     * 用户id
     */
    @TableField("user_id_")
    private String userId;

    /**
     * 卖家id
     */
    @TableField("seller_id_")
    private String sellerId;

    /**
     * 创建时间
     */
    @TableField("create_time_")
    private Date createTime;

    /**
     * 支付时间
     */
    @TableField("pay_time_")
    private Date payTime;

    /**
     * 状态
     * 0：未支付，1：已支付
     */
    @TableField("status_")
    private String status;

    /**
     * 收货人地址
     */
    @TableField("receiver_address_")
    private String receiverAddress;

    /**
     * 收货人电话
     */
    @TableField("receiver_mobile_")
    private String receiverMobile;

    /**
     * 收货人
     */
    @TableField("receiver_")
    private String receiver;

    /**
     * 交易流水
     */
    @TableField("transaction_id_")
    private String transactionId;

}
