package com.hh.legou.item.controller;

import com.hh.legou.core.controller.BaseController;
import com.hh.legou.item.po.Sku;
import com.hh.legou.item.service.ISkuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 10/09/2023 16:12
 */
@RestController
@RequestMapping("/sku")
public class SkuController extends BaseController<ISkuService, Sku> {
    @ApiOperation(value = "查询spu对应sku", notes = "根据spuId查询sku集合")
    @GetMapping("/select-skus-by-spuid/{id}")
    public List<Sku> selectSkusBySpuId(@PathVariable("id") Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        return service.list(sku);
    }

    @PostMapping("/decr-count")
    public void decrCount(@RequestParam("num") Integer num, @RequestParam("skuId") Long skuId) {
        service.decrCount(num, skuId);
    }
}
