package com.hh.legou.order.service.impl;

import com.hh.legou.common.utils.IdWorker;
import com.hh.legou.core.service.impl.CrudServiceImpl;
import com.hh.legou.order.client.SkuClient;
import com.hh.legou.order.client.UserClient;
import com.hh.legou.order.dao.IOrderItemDao;
import com.hh.legou.order.po.Order;
import com.hh.legou.order.po.OrderItem;
import com.hh.legou.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 24/12/2023 17:20
 */
@Service
public class OrderServiceImpl extends CrudServiceImpl<Order> implements IOrderService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private IOrderItemDao orderItemDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SkuClient skuClient;

    @Autowired
    private UserClient userClient;

    @Override
    public void add(Order order) {
        //1.添加订单主表数据
        order.setId(idWorker.nextId());
        //2.循环购物车，添加订单明细数据
        List<OrderItem> cartList = redisTemplate.boundHashOps("Cart_" + order.getUsername()).values();
        Long totalNum = 0l;//订单总数量
        Long totalMoney = 0l;//订单总金额
        for (OrderItem orderItem : cartList) {
            //计算总数相关
            totalNum += orderItem.getNum();
            totalMoney += orderItem.getPayMoney();

            orderItem.setId(idWorker.nextId());//订单选项id
            orderItem.setOrderId(order.getId());//订单id
            orderItem.setIsReturn("0");//未退货
            orderItemDao.insert(orderItem);

            //3.调用商品微服务减库存
            skuClient.decrCount(orderItem.getNum(), orderItem.getSkuId());
        }
        order.setTotalNum(totalNum);//设置总数量
        order.setTotalMoney(totalMoney);//设置总金额
        order.setPayMoney(totalMoney);//设置实付金额
        order.setCreateTime(new Date());
        order.setUpdateTime(order.getCreateTime());
        order.setOrderStatus("0");//未完成
        order.setPayStatus("0");//未支付
        order.setConsignStatus("0");//未发货
        order.setIsDelete("0");

        getBaseMapper().insert(order);

        //4.增加用户积分
        userClient.addPoint(10l, order.getUsername());

        //5.删除redis购物车数据
        redisTemplate.delete("Cart_" + order.getUsername());
    }

    @Override
    public void updatePayStatus(String outTradeNo, String tradeNo) {
        Order order = getBaseMapper().selectById(Long.parseLong(outTradeNo));
        order.setUpdateTime(new Date());
        order.setPayTime(new Date());
        order.setPayStatus("1");
        order.setOrderStatus("1");
        order.setTransactionId(tradeNo);
        getBaseMapper().updateById(order);
    }
}
