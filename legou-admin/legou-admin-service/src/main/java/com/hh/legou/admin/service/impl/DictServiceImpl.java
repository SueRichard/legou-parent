package com.hh.legou.admin.service.impl;

import com.hh.legou.admin.po.Dict;
import com.hh.legou.admin.service.IDictService;
import com.hh.legou.core.service.impl.CrudServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @file DictService.java
 * @Copyright (C) http://www.lxs.com
 * @author hh
 * @email lxosng77@163.com
 * @date 2018/7/13
 */
@Service
public class DictServiceImpl extends CrudServiceImpl<Dict> implements IDictService {
    //自定义sql在映射文件
    //也可以写在这里，比如DeptService
}
