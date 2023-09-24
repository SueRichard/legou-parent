package com.hh.legou.item.api;

import com.hh.legou.item.po.SpecParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 24/09/2023 10:52
 */
@RequestMapping("/item/param")
public interface SpecParamApi {
    @ApiOperation(value = "查询", notes = "根据实体条件查询参数")
    @PostMapping(value = "/select-param-by-entity", consumes = "application/json")//注意这里是json
    List<SpecParam> selectSpecParamApi(@RequestBody SpecParam entity);
}
