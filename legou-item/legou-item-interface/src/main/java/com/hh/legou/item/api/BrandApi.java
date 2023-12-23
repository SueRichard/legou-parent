package com.hh.legou.item.api;

import com.hh.legou.item.po.Brand;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 23/09/2023 09:01
 */
@RequestMapping("/brand")
public interface BrandApi {

    @ApiOperation(value = "根据ids查询", notes = "根据ids查询")
    @RequestMapping("/list-by-ids")
    List<Brand> selectBrandByIds(@RequestParam("ids") List<Long> ids);
}
