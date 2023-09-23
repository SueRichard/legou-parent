package com.hh.legou.item.service;

import com.hh.legou.core.service.ICrudService;
import com.hh.legou.item.po.Category;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 02/09/2023 17:40
 */
public interface ICategoryService extends ICrudService<Category> {

    /**
     * 通过ids查询分类名称集合
     *
     * @param ids id集合
     * @return 根据id查询到的名字
     */
    List<String> selectNamesByIds(List<Long> ids);
}
