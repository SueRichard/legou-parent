package com.hh.legou.order.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hh.legou.core.po.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author hh
 * @version 1.0
 * @time 19/11/2023 17:27
 */
@Data
@TableName("order_")
public class Order extends BaseEntity {

    /**
     * 采用雪花算法，获得主键
     */
    @TableId(value = "id_", type = IdType.INPUT)
    protected Long id;

    /**
     * 数量合计
     */
    @TableField("total_num_")
    private Long totalNum;

    /**
     * 金额合计
     */
    @TableField("total_money_")
    private Long totalMoney;

    /**
     * 优惠金额
     */
    @TableField("pre_money_")
    private Long preMoney;

    /**
     * 邮费
     */
    @TableField("post_fee_")
    private Long postFee;

    /**
     * 实付金额
     */
    @TableField("pay_money_")
    private Long payMoney;

    /**
     * 支付类型，1、在线支付、0 货到付款
     */
    @TableField("pay_type_")
    private String payType;

    /**
     * 订单创建时间
     */
    @TableField("create_time_")
    private Date createTime;

    /**
     * 订单更新时间
     */
    @TableField("update_time_")
    private Date updateTime;

    /**
     * 付款时间
     */
    @TableField("pay_time_")
    private Date payTime;

    /**
     * 发货时间
     */
    @TableField("consign_time_")
    private Date consignTime;

    /**
     * 交易完成时间
     */
    @TableField("end_time_")
    private Date endTime;

    /**
     * 交易关闭时间
     */
    @TableField("close_time_")
    private Date closeTime;

    /**
     * 物流名称
     */
    @TableField("shipping_name_")
    private String shippingName;

    /**
     * 物流单号
     */
    @TableField("shipping_code_")
    private String shippingCode;

    /**
     * 用户名称
     */
    @TableField("username_")
    private String username;

    /**
     * 买家留言
     */
    @TableField("buyer_message_")
    private String buyerMessage;

    /**
     * 是否评价
     */
    @TableField("buyer_rate_")
    private String buyerRate;

    /**
     * 收货人
     */
    @TableField("receiver_contact_")
    private String receiverContact;

    /**
     * 收货人手机
     */
    @TableField("receiver_mobile_")
    private String receiverMobile;

    /**
     * 收货人地址
     */
    @TableField("receiver_address_")
    private String receiverAddress;

    /**
     * 订单来源：1:web，2：app，3：微信公众号，4：微信小程序 5 H5手机页面
     */
    @TableField("source_type_")
    private String sourceType;

    /**
     * 交易流水号
     */
    @TableField("transaction_id_")
    private String transactionId;

    /**
     * 订单状态,0:未完成,1:已完成，2：已退货，3：已取消
     */
    @TableField("order_status_")
    private String orderStatus;

    /**
     * 支付状态,0:未支付，1：已支付，2：支付失败
     */
    @TableField("pay_status_")
    private String payStatus;

    /**
     * 发货状态,0:未发货，1：已发货，2：已收货
     */
    @TableField("consign_status_")
    private String consignStatus;

    /**
     * 是否删除
     */
    @TableField("is_delete_")
    private String isDelete;

}
