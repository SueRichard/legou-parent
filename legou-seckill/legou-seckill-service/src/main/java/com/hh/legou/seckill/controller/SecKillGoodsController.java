package com.hh.legou.seckill.controller;

import com.hh.legou.common.utils.DateUtil;
import com.hh.legou.core.controller.BaseController;
import com.hh.legou.seckill.po.SecKillGoods;
import com.hh.legou.seckill.service.ISecKillGoodsService;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 获取指定时区的所有秒杀商品
     * http://localhost:9011/seckill-goods/list/2024010510
     *
     * @param time 时区
     * @return
     */
    @RequestMapping("/list/{time}")
    public List<SecKillGoods> list(@PathVariable("time") String time) {
        return service.list(time);
    }

    /**
     * 根据时区和id查询秒杀商品
     * http://localhost:9011/seckill-goods/one?time=2024010512&id=5
     *
     * @param time
     * @param id
     * @return
     */
    @RequestMapping("/one")
    public SecKillGoods one(String time, Long id) {
        return service.one(time, id);
    }
}
