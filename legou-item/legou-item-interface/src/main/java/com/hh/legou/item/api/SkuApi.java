package com.hh.legou.item.api;

import com.hh.legou.item.po.Sku;
import com.hh.legou.item.po.Spu;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 23/09/2023 10:09
 */
@RequestMapping("/sku")
public interface SkuApi {
    @ApiOperation(value = "查询spu对应sku", notes = "根据spuId查询sku集合")
    @RequestMapping("/select-skus-by-spuid/{id}")
    List<Sku> selectSkusBySpuId(@PathVariable("id") Long SpuId);

    @ApiOperation(value = "加载", notes = "根据id加载")
    @GetMapping("/edit/{id}")
    Sku edit(@PathVariable Long id);

    @ApiOperation(value = "减库存", notes = "下单减库存，能减再减")
    @PostMapping("/decr-count")
    void decrCount(@RequestParam("num") Integer num, @RequestParam("skuId") Long skuId);//通过feign调用controller，如果有多个参数，必须加@RequestParam(对应controller，即SkuController也需要加)，否则报异常：方法有太多参数
}
