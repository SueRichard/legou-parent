package com.hh.legou.seckill.controller;

import com.hh.legou.core.controller.BaseController;
import com.hh.legou.seckill.po.SecKillOrder;
import com.hh.legou.seckill.service.ISecKillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hh
 * @version 1.0
 * @time 05/01/2024 15:33
 */
@RestController
@RequestMapping("/seckill-order")
@CrossOrigin
public class SecKillOrderController extends BaseController<ISecKillOrderService, SecKillOrder> {

    @Autowired
    private ISecKillOrderService orderService;

    /**
     * 秒杀下单
     *
     * @param id       秒杀商品id
     * @param time     秒杀区间
     * @param username 用户登录名
     * @return
     */
    @RequestMapping("/add")
    public ResponseEntity add(Long id, String time, String username) {
        try {
            boolean success = orderService.add(id, time, username);
            if (success) {
                return ResponseEntity.ok("抢单成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity("抢单失败", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
