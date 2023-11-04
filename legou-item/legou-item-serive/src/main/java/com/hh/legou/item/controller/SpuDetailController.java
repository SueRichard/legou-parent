package com.hh.legou.item.controller;

import com.hh.legou.core.controller.BaseController;
import com.hh.legou.item.po.SpuDetail;
import com.hh.legou.item.service.ISpuDetailService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hh
 * @version 1.0
 * @time 10/09/2023 16:13
 */
@RestController
@RequestMapping("/spu-detail")
public class SpuDetailController extends BaseController<ISpuDetailService, SpuDetail> {
}
