package com.hh.legou.item.service.impl;

import com.hh.legou.core.service.impl.CrudServiceImpl;
import com.hh.legou.item.dao.BrandDao;
import com.hh.legou.item.po.Brand;
import com.hh.legou.item.po.Category;
import com.hh.legou.item.service.IBrandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 2023年8月19日15:57:46
 */
@Service
public class BrandServiceImpl extends CrudServiceImpl<Brand> implements IBrandService {
    /**
     * 通过品牌id查询所属分类
     *
     * @param id
     * @return
     */
    @Override
    public List<Category> selectCategoryByBrandId(Long id) {
        return ((BrandDao) getBaseMapper()).selectCategoryByBrandId(id);
    }

    /**
     * 除了保存品牌实体，还需要更新分类数据
     * 注意此处需要事务！！！
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(Brand entity) {
        //存品牌信息
        boolean result = super.saveOrUpdate(entity);
        //删除原有品牌和分类关联
        ((BrandDao) getBaseMapper()).deleteCategoryByBrandId(entity.getId());
        //插入新的品牌和分配关联
        Long[] categoryIds = entity.getCategoryIds();
        if (categoryIds != null) {
            for (Long categoryId : categoryIds) {
                ((BrandDao) getBaseMapper()).insertCategoryAndBrand(categoryId, entity.getId());
            }
        }
        return result;
    }
}
