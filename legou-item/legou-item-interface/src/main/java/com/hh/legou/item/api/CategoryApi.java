package com.hh.legou.item.api;

import com.hh.legou.item.po.Category;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 分类暴露出的api接口
 *
 * @author hh
 * @version 1.0
 * @time 17/09/2023 17:21
 */
@RequestMapping("/item/category")
public interface CategoryApi {

    @ApiOperation(value = "查询分类", notes = "条件查询分类")
    @RequestMapping(value = "/list")
    List<Category> list(Category category);
}
