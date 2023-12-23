package com.hh.legou.item.api;

import com.hh.legou.item.po.SpuDetail;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hh
 * @version 1.0
 * @time 24/09/2023 10:31
 */
@RequestMapping("/spu-detail")
public interface SpuDetailApi {
    @ApiOperation(value = "加载", notes = "根据id加载")
    @GetMapping("/edit/{id}")
    SpuDetail edit(@PathVariable Long id);
}
