package com.hh.legou.seckill.controller;

import com.hh.legou.common.utils.DateUtil;
import com.hh.legou.core.controller.BaseController;
import com.hh.legou.seckill.po.SecKillGoods;
import com.hh.legou.seckill.service.ISecKillGoodsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 05/01/2024 11:16
 */
@RestController
@RequestMapping("/seckill-goods")
@CrossOrigin
public class SecKillGoodsController extends BaseController<ISecKillGoodsService, SecKillGoods> {

    @GetMapping("/menus")
    public List<Date> dateMenus() {
        List<Date> dates = DateUtil.getDateMenus();
        for (Date date : dates) {
            System.out.println(date);
        }
        return dates;
    }
}
