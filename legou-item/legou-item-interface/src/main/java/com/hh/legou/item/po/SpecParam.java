package com.hh.legou.item.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hh.legou.core.po.BaseEntity;
import lombok.Data;

/**
 * @author hh
 * @version 1.0
 * @time 06/09/2023 10:06
 */
@Data
@TableName("spec_param_")
public class SpecParam extends BaseEntity {

    @TableField("cid_")
    private Long cid;

    @TableField("group_id_")
    private Long groupId;

    @TableField("name_")
    private String name;

    @TableField("numeric_")
    private Boolean numeric;

    @TableField("unit_")
    private String unit;

    @TableField("generic_")
    private Boolean generic;

    @TableField("searching_")
    private Boolean searching;

    @TableField("segments_")
    private String segments;

}
