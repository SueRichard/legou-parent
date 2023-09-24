package com.hh.legou.item.api;

import com.hh.legou.item.po.Spu;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 24/09/2023 10:20
 */
@RequestMapping("item/spu")
public interface SpuApi {
    @ApiOperation(value = "查询所有", notes = "查询所有spu")
    @GetMapping("/list-all")
    List<Spu> selectAll();
}
