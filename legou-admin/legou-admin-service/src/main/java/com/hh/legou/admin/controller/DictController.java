package com.hh.legou.admin.controller;

import com.hh.legou.admin.po.Dict;
import com.hh.legou.admin.service.IDictService;
import com.hh.legou.core.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dict")
public class DictController extends BaseController<IDictService, Dict> {


}
