package com.hh.legou.order.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hh.legou.core.po.BaseEntity;
import lombok.Data;

/**
 * @author hh
 * @version 1.0
 * @time 24/12/2023 15:44
 */
@Data
@TableName("address_")
public class Address extends BaseEntity {

    /**
     * 用户名
     */
    @TableField("username_")
    private String username;

    /**
     * 省
     */
    @TableField("province_")
    private String provinceId;

    /**
     * 市
     */
    @TableField("city_")
    private String cityId;

    /**
     * 区
     */
    @TableField("area_")
    private String areaId;

    /**
     * 电话
     */
    @TableField("phone_")
    private String phone;

    /**
     * 详细地址
     */
    @TableField("address_")
    private String address;

    /**
     * 联系人
     */
    @TableField("contact_")
    private String contact;

    /**
     * 是否默认
     * 1默认 0否
     */
    @TableField("is_default_")
    private String isDefault;

    /**
     * 别名
     */
    @TableField("alias_")
    private String alias;
}
