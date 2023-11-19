package com.hh.legou.item.api;

import com.hh.legou.item.po.Sku;
import com.hh.legou.item.po.Spu;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 23/09/2023 10:09
 */
@RequestMapping("/item/sku")
public interface SkuApi {
    @ApiOperation(value = "查询spu对应sku", notes = "根据spuId查询sku集合")
    @RequestMapping("/select-skus-by-spuid/{id}")
    List<Sku> selectSkusBySpuId(@PathVariable("id") Long SpuId);

    @ApiOperation(value = "加载", notes = "根据id加载")
    @GetMapping("/edit/{id}")
    Sku edit(@PathVariable Long id);
}
