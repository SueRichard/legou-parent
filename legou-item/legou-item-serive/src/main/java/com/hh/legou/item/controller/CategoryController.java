package com.hh.legou.item.controller;

import com.hh.legou.core.controller.BaseController;
import com.hh.legou.item.po.Category;
import com.hh.legou.item.service.ICategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 02/09/2023 17:57
 */
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController<ICategoryService, Category> {
    @ApiOperation(value = "根据ids查询names", notes = "根据ids查询名称列表")
    @GetMapping("/names")
    public ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids") List<Long> ids) {
        List<String> names = service.selectNamesByIds(ids);
        if (names == null || names.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(names);
    }
}
