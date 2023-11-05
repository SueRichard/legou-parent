package com.hh.legou.admin.controller;

import com.hh.legou.admin.po.Dept;
import com.hh.legou.admin.service.IDeptService;
import com.hh.legou.core.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController<IDeptService, Dept> {

}
