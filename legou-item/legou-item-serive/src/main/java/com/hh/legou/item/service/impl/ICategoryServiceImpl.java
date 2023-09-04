package com.hh.legou.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hh.legou.core.service.impl.CrudServiceImpl;
import com.hh.legou.item.po.Category;
import com.hh.legou.item.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 02/09/2023 17:41
 */
@Service
public class ICategoryServiceImpl extends CrudServiceImpl<Category> implements ICategoryService {
    @Override
    public List<Category> list(Category entity) {
        QueryWrapper<Category> queryWrapper = Wrappers.query();
        if (StringUtils.isNotEmpty(entity.getTitle())) {
            queryWrapper.like("title_", entity.getTitle());
        }
        if (null != entity.getParentId()) {
            queryWrapper.eq("parent_id_", entity.getParentId());
        }
        if (null != entity.getIsRoot() && entity.getIsRoot().equals(1)) {
            queryWrapper.isNull("parent_id_");
        }
        return getBaseMapper().selectList(queryWrapper);
    }
}
