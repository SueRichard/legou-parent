package com.hh.legou.item.controller;

import com.hh.legou.core.controller.BaseController;
import com.hh.legou.core.po.ResponseBean;
import com.hh.legou.item.po.Spu;
import com.hh.legou.item.service.ISpuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 10/09/2023 16:13
 */
@RestController
@RequestMapping("/spu")
public class SpuController extends BaseController<ISpuService, Spu> {
    @ApiOperation(value = "保存商品信息", notes = "保存商品信息")
    @PostMapping("/save-spu")
    public ResponseBean saveSpu(@RequestBody Spu spu) {
        ResponseBean responseBean = new ResponseBean();
        try {
            service.saveSpu(spu);
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.setSuccess(false);
            responseBean.setMsg("保存失败");
        }
        return responseBean;
    }

    @ApiOperation(value = "查询所有", notes = "查询所有")
    @GetMapping("/list-all")
    public List<Spu> selectAll() {
        return service.list(new Spu());
    }

}
