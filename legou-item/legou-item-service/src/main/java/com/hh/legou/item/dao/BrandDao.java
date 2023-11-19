package com.hh.legou.item.dao;

import com.hh.legou.core.dao.ICrudDao;
import com.hh.legou.item.po.Brand;
import com.hh.legou.item.po.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 2023年8月19日16:20:42
 */
public interface BrandDao extends ICrudDao<Brand> {
    //由于继承的ICrudDao中已经有selectByPage方法，所以这里不用在写，只需要在resource中对应文件夹配置对应的BrandDao.xml即可

    /**
     * 通过品牌id查询对应分类
     *
     * @param id
     * @return
     */
    public List<Category> selectCategoryByBrandId(Long id);

    int deleteCategoryByBrandId(Long id);

    /**
     * 插入category_brand_ 表
     *
     * @param categoryId
     * @param brandId
     * @return
     */
    int insertCategoryAndBrand(@Param("categoryId") Long categoryId, @Param("brandId") Long brandId);
}
