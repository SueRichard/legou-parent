package com.hh.legou.item.controller;

import com.hh.legou.core.controller.BaseController;
import com.hh.legou.core.po.ResponseBean;
import com.hh.legou.item.po.SpecGroup;
import com.hh.legou.item.service.ISpecGroupService;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 09/09/2023 11:57
 */
@RestController
@RequestMapping("/group")
public class SpecGroupController extends BaseController<ISpecGroupService, SpecGroup> {

    @ApiOperation(value = "保存规格参数", notes = "保存规格参数")
    @PostMapping("/save-group")
    public ResponseBean saveGroup(@RequestBody List<SpecGroup> specGroups) {
        ResponseBean responseBean = new ResponseBean();
        try {
            if (!CollectionUtils.isEmpty(specGroups)) {
                service.saveGroup(specGroups.get(0).getCid(), specGroups);
                //responseBean初始化默认成功，这里无需赋值
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.setSuccess(false);
            responseBean.setMsg("保存失败");
        }
        return responseBean;
    }
}
