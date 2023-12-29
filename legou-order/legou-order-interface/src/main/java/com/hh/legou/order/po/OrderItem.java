package com.hh.legou.order.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hh.legou.core.po.BaseEntity;
import lombok.Data;

/**
 * @author hh
 * @version 1.0
 * @time 19/11/2023 17:32
 */
@Data
@TableName("order_item_")
public class OrderItem extends BaseEntity {

    /**
     * 采用雪花算法，获得主键
     */
    @TableId(value = "id_", type = IdType.INPUT)
    protected Long id;

    /**
     * 1级分类
     */
    @TableField("category_id1_")
    private Long categoryId1;

    /**
     * 2级分类
     */
    @TableField("category_id2_")
    private Long categoryId2;

    /**
     * 3级分类
     */
    @TableField("category_id3_")
    private Long categoryId3;

    /**
     * SPU_ID
     */
    @TableField("spu_id_")
    private Long spuId;

    /**
     * SKU_ID
     */
    @TableField("sku_id_")
    private Long skuId;

    /**
     * 订单ID
     */
    @TableField("order_id_")
    private Long orderId;

    /**
     * 商品名称
     */
    @TableField("name_")
    private String name;

    /**
     * 单价
     */
    @TableField("price_")
    private Long price;

    /**
     * 数量
     */
    @TableField("num_")
    private Integer num;

    /**
     * 总金额
     */
    @TableField("money_")
    private Long money;

    /**
     * 实付金额
     */
    @TableField("pay_money_")
    private Long payMoney;

    /**
     * 图片地址
     */
    @TableField("image_")
    private String image;

    /**
     * 重量
     */
    @TableField("weight_")
    private Long weight;

    /**
     * 运费
     */
    @TableField("post_fee_")
    private Long postFee;

    /**
     * 是否退货,0:未退货，1：已退货
     */
    @TableField("is_return_")
    private String isReturn;
}
