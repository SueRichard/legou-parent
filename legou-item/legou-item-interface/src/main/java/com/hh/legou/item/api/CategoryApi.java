package com.hh.legou.item.api;

import com.hh.legou.item.po.Category;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 分类暴露出的api接口
 *
 * @author hh
 * @version 1.0
 * @time 17/09/2023 17:21
 */
@RequestMapping("/category")
public interface CategoryApi {

    @ApiOperation(value = "查询分类", notes = "条件查询分类")
    @RequestMapping(value = "/list")
    List<Category> list(Category category);

    @ApiOperation(value = "根据ids查询names", notes = "根据分类id查询名称列表")
    @GetMapping(value = "/names")
    List<String> queryNamesByIds(@RequestParam("ids") List<Long> ids);

    @ApiOperation(value = "加载", notes = "根据id加载")
    @GetMapping(value = "/edit/{id}")
    Category edit(@PathVariable Long id);
}
