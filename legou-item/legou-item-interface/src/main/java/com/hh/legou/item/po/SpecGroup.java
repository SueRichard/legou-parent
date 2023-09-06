package com.hh.legou.item.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hh.legou.core.po.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 06/09/2023 10:06
 */
@Data
@TableName("spec_group_")
@JsonIgnoreProperties(value = {"handler"})
public class SpecGroup extends BaseEntity {

    @TableField("cid_")
    private Long cid;

    @TableField("name_")
    private String name;

    @TableField(exist = false)
    private List<SpecParam> params; //当前组下的规格项，使用resultMap懒加载查询
}
