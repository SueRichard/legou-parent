package com.hh.legou.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hh.legou.core.service.impl.CrudServiceImpl;
import com.hh.legou.item.po.SpecParam;
import com.hh.legou.item.service.ISpecParamService;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 09/09/2023 11:34
 */
@Service
public class SpecParamService extends CrudServiceImpl<SpecParam> implements ISpecParamService {
    @Override
    public List<SpecParam> list(SpecParam entity) {
        QueryWrapper<SpecParam> queryWrapper = Wrappers.<SpecParam>query();
        //根据分类id查询规格参数
        if (entity.getCid() != null) {
            queryWrapper.eq("cid_", entity.getCid());
        }
        if (entity.getSearching() != null) {
            queryWrapper.eq("searching_", entity.getSearching());
        }
        return getBaseMapper().selectList(queryWrapper);
    }
}
