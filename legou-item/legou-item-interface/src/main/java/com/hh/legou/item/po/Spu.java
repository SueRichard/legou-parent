package com.hh.legou.item.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hh.legou.core.po.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 10/09/2023 13:55
 */
@Data
@TableName("spu_")
public class Spu extends BaseEntity {

    /**
     * 品牌id
     */
    @TableField("brand_id_")
    private Long brandId;

    /**
     * 一级分类
     */
    @TableField("cid1_")
    private Long cid1;

    /**
     * 二级分类
     */
    @TableField("cid2_")
    private Long cid2;

    /**
     * 三级分类
     */
    @TableField("cid3_")
    private Long cid3;

    /**
     * 标题
     */
    @TableField("title_")
    private String title;

    /**
     * 子标题
     */
    @TableField("sub_title_")
    private String subTitle;

    /**
     * 是否上架，静态化用
     */
    @TableField("saleable_")
    private Boolean saleable;

    /**
     * 是否有效，逻辑删除用
     */
    @TableField("valid_")
    private Boolean valid;

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
     * Spu详情对象
     * 一个spu对应一个spuDetail
     */
    @TableField(exist = false)
    private SpuDetail spuDetail;

    /**
     * sku集合对象
     * 一个spu对应多个sku
     */
    @TableField(exist = false)
    private List<Sku> skus;

    /**
     * 品牌名称，查询时显示
     */
    @TableField(exist = false)
    private String brandName;

    /**
     * 分类名称，查询时显示
     */
    @TableField(exist = false)
    private String categoryName;

}
