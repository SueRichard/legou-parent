package com.hh.legou.item.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hh.legou.core.po.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author hh
 * @version 1.0
 * @time 10/09/2023 13:56
 */
@Data
@TableName("sku_")
public class Sku extends BaseEntity {

    @TableField("spu_id_")
    private Long spuId;

    @TableField("title_")
    private String title;

    @TableField("images_")
    private String images;

    @TableField("price_")
    private Long price;

    /**
     * 商品特殊规格键值对
     */
    @TableField("own_spec_")
    private String ownSpec;

    /**
     * 商品特殊规格下标
     */
    @TableField("indexes_")
    private String indexes;

    /**
     * 是否有效，逻辑删除用
     */
    @TableField("enable_")
    private Boolean enable;

    /**
     * 创建时间
     */
    @TableField("create_time_")
    private Date createTime;

    /**
     * 最后修改时间
     */
    @TableField("last_update_time_")
    private Date lastUpdateTime;

    /**
     * 库存
     */
    @TableField("stock_")
    private Integer stock;

}
