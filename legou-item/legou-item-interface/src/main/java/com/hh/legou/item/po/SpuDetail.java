package com.hh.legou.item.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hh.legou.core.po.BaseEntity;
import lombok.Data;

/**
 * @author hh
 * @version 1.0
 * @time 10/09/2023 13:56
 */
@Data
@TableName("spu_detail_")
public class SpuDetail extends BaseEntity {

    /**
     * 实体编号（唯一标识）
     * 这里的id不可以自增，需要保持和spu一致的id
     * 父类中使用的自增字段，不适用
     */
    @TableId(value = "id_", type = IdType.INPUT)
    protected Long id;

    /**
     * 商品描述
     */
    @TableField("description_")
    private String description;

    /**
     * 商品特殊规格名称及可选值模版
     */
    @TableField("special_spec_")
    private String specialSpec;

    /**
     * 商品全局规格参数
     */
    @TableField("generic_spec_")
    private String genericSpec;

    /**
     * 包装清单
     */
    @TableField("packing_list_")
    private String packingList;

    /**
     * 售后服务
     */
    @TableField("after_service_")
    private String afterService;

}
