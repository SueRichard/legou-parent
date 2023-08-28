package com.hh.legou.item.controller;

import com.hh.legou.core.controller.BaseController;
import com.hh.legou.item.po.Brand;
import com.hh.legou.item.po.Category;
import com.hh.legou.item.service.IBrandService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 2023年8月19日16:57:05
 */
@RestController
//由于前端使用了网关，而后端没有，所以这里路径手动加item
@RequestMapping("/item/brand")
@CrossOrigin
public class BrandController extends BaseController<IBrandService, Brand> {
    @Override
    public void afterEdit(Brand entity) {
        //加载的时候需要把id查询出来
        List<Category> categories = service.selectCategoryByBrandId(entity.getId());
        Long[] ids = new Long[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            ids[i] = categories.get(i).getId();
        }
        entity.setCategoryIds(ids);
    }
}
