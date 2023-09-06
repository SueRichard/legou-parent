package com.hh.legou.item.service;

import com.hh.legou.core.service.ICrudService;
import com.hh.legou.item.po.SpecGroup;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 06/09/2023 16:01
 */
public interface ISpecGroupService extends ICrudService<SpecGroup> {

    /**
     * 根据前端传递的规格参数列表，保存规格参数
     *
     * @param cid    分类id
     * @param groups 前端传递的分组列表 参考格式：[{ cid : 1, name : '', params:[...]},...]
     */
    void saveGroup(Long cid, List<SpecGroup> groups);
}
