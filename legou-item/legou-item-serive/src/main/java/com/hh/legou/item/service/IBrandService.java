package com.hh.legou.item.service;

import com.hh.legou.core.service.ICrudService;
import com.hh.legou.item.po.Brand;
import com.hh.legou.item.po.Category;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 2023年8月19日16:48:58
 */
public interface IBrandService extends ICrudService<Brand> {
    /**
     * 通过品牌id查询对应的分类
     *
     * @param id
     * @return
     */
    List<Category> selectCategoryByBrandId(Long id);

    /**
     * 根据品牌id集合，得到品牌集合
     *
     * @param ids
     * @return
     */
    List<Brand> selectBrandByIds(List<Long> ids);
}
