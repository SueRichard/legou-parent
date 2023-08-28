package com.hh.legou.item.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hh.legou.core.po.BaseTreeEntity;
import lombok.Data;

/**
 * @author hh
 * @version 1.0
 * @time 2023年8月19日15:57:46
 */
@Data
@TableName("category_")
public class Category extends BaseTreeEntity {

    @TableField("is_parent_")
    private Boolean isParent = false;//是否为父节点

    @TableField(exist = false)
    private Integer isRoot = 0;//值=1，查询根节点条件

    public String getLabel() {
        return this.getTitle();
    }
}
