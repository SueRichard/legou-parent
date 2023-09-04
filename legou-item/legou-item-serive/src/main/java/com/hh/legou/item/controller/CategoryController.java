package com.hh.legou.item.controller;

import com.hh.legou.core.controller.BaseController;
import com.hh.legou.item.po.Category;
import com.hh.legou.item.service.ICategoryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hh
 * @version 1.0
 * @time 02/09/2023 17:57
 */
@RestController
@RequestMapping("/item/category")
@CrossOrigin
public class CategoryController extends BaseController<ICategoryService, Category> {
}
