package com.hh.legou.item.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hh.legou.core.service.impl.CrudServiceImpl;
import com.hh.legou.item.dao.SpecGroupDao;
import com.hh.legou.item.dao.SpecParamDao;
import com.hh.legou.item.po.SpecGroup;
import com.hh.legou.item.po.SpecParam;
import com.hh.legou.item.service.ISpecGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 06/09/2023 16:08
 */
@Service
public class SpecGroupServiceImpl extends CrudServiceImpl<SpecGroup> implements ISpecGroupService {

    @Autowired
    private SpecParamDao specParamDao;

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
    @Transactional
    public void saveGroup(Long cid, List<SpecGroup> groups) {
        //根据id删除所有的规格参数分组和规格参数项
        getBaseMapper().delete(Wrappers.<SpecGroup>query().eq("cid_", cid));
        specParamDao.delete(Wrappers.<SpecParam>query().eq("cid_", cid));

        //添加规格参数组和规格项
        for (SpecGroup group : groups) {
            getBaseMapper().insert(group);
            for (SpecParam param : group.getParams()) {
                //设置组id
                param.setGroupId(group.getId());
                specParamDao.insert(param);
            }
        }
    }
}
