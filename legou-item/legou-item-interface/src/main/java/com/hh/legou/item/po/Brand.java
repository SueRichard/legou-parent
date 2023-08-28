package com.hh.legou.item.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hh.legou.core.po.BaseEntity;
import lombok.Data;

/**
 * @author hh
 * @version 1.0
 * @time 2023年8月19日16:13:12
 */
@Data
@TableName("brand_")
public class Brand extends BaseEntity {
    @TableField("name_")
    private String name;  //名称

    @TableField("image_")
    private String image;  //图片

    @TableField("letter_")
    private String letter;  //首字母

    @TableField(exist = false)
    private Long[] categoryIds; //瞬时属性，品牌所属分类
}
