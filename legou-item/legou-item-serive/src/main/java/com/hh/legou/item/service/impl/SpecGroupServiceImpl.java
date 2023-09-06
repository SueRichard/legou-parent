package com.hh.legou.item.service.impl;

import com.hh.legou.core.service.ICrudService;
import com.hh.legou.core.service.impl.CrudServiceImpl;
import com.hh.legou.item.dao.SpecGroupDao;
import com.hh.legou.item.po.SpecGroup;
import com.hh.legou.item.service.ISpecGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 06/09/2023 16:08
 */
@Service
public class SpecGroupServiceImpl extends CrudServiceImpl<SpecGroup> implements ISpecGroupService {
    /**
     * @param entity
     * @return
     */
    @Override
    public List<SpecGroup> list(SpecGroup entity) {
        return ((SpecGroupDao) getBaseMapper()).selectList(entity);
    }

    /**
     * 根据前端传递的规格参数列表，保存规格参数
     *
     * @param cid    分类id
     * @param groups 前端传递的分组列表 参考格式：[{ cid : 1, name : '', params:[...]},...]
     */
    @Override
    public void saveGroup(Long cid, List<SpecGroup> groups) {
         //根据id删除所有的规格参数分组和规格参数项
    }
}
