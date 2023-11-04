package com.hh.legou.item.controller;

import com.hh.legou.core.controller.BaseController;
import com.hh.legou.item.po.SpecParam;
import com.hh.legou.item.service.ISpecParamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 09/09/2023 12:06
 */
@RestController
@RequestMapping("/param")
public class SpecParamController extends BaseController<ISpecParamService, SpecParam> {

    @ApiOperation(value = "查询", notes = "根据实体条件查询参数")
    @PostMapping("/select-param-by-entity")
    public List<SpecParam> selectSpecParamApi(@RequestBody SpecParam entity) {
        return service.list(entity);
    }
}
