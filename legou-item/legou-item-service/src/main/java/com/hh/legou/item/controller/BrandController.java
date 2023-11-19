package com.hh.legou.item.controller;

import com.hh.legou.core.controller.BaseController;
import com.hh.legou.item.po.Brand;
import com.hh.legou.item.po.Category;
import com.hh.legou.item.service.IBrandService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 2023年8月19日16:57:05
 */
@RestController
//由于前端使用了网关，而后端没有，所以这里路径手动加item
//已加入网关，移除/item前缀和@CrossOrigin注解
@RequestMapping("/brand")
public class BrandController extends BaseController<IBrandService, Brand> {
    @Override
    public void afterEdit(Brand entity) {
        /*//此处休眠用来测试nginx统一ip限流
        System.out.println("开始休眠");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("停止休眠");*/
        //加载的时候需要把id查询出来
        List<Category> categories = service.selectCategoryByBrandId(entity.getId());
        Long[] ids = new Long[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            ids[i] = categories.get(i).getId();
        }
        entity.setCategoryIds(ids);
    }

    @ApiOperation(value = "根据id查询品牌", notes = "根据id查询品牌")
    @RequestMapping("/list-by-ids")
    public List<Brand> selectBrandByIds(@RequestParam("ids") List<Long> ids) {
        return service.selectBrandByIds(ids);
    }
}
