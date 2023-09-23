package com.hh.legou.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hh.legou.core.service.impl.CrudServiceImpl;
import com.hh.legou.item.po.Category;
import com.hh.legou.item.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hh
 * @version 1.0
 * @time 02/09/2023 17:41
 */
@Service
public class CategoryServiceImpl extends CrudServiceImpl<Category> implements ICategoryService {
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

    /**
     * 通过ids查询分类名称集合
     *
     * @param ids id集合
     * @return 根据id查询到的名字
     */
    @Override
    public List<String> selectNamesByIds(List<Long> ids) {
        QueryWrapper<Category> queryWrapper = Wrappers.<Category>query().in("id_", ids);
        return getBaseMapper().selectList(queryWrapper).stream().map(item -> item.getTitle()).collect(Collectors.toList());
    }
}
