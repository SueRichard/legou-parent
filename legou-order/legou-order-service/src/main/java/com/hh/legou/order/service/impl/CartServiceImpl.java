package com.hh.legou.order.service.impl;

import com.hh.legou.item.po.Sku;
import com.hh.legou.item.po.Spu;
import com.hh.legou.order.client.SkuClient;
import com.hh.legou.order.client.SpuClient;
import com.hh.legou.order.service.ICartService;
import com.hh.legou.order.po.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 19/11/2023 18:43
 */
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private SkuClient skuClient;

    @Autowired
    private SpuClient spuClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void add(Long skuId, Long num, String username) {
        if (num <= 0) {
            redisTemplate.boundHashOps("Cart_" + username).delete(skuId);
            return;
        }
        //根据id获取sku
        Sku sku = skuClient.edit(skuId);
        if (sku != null) {
            //获取spu
            Spu spu = spuClient.edit(sku.getSpuId());

            //数据存储到order_template
            OrderItem orderItem = new OrderItem();
            orderItem.setCategoryId1(spu.getCid1());
            orderItem.setCategoryId2(spu.getCid2());
            orderItem.setCategoryId3(spu.getCid3());
            orderItem.setSpuId(spu.getId());
            orderItem.setSkuId(skuId);
            //商品名称
            orderItem.setName(sku.getTitle());
            //商品单价
            orderItem.setPrice(sku.getPrice());
            //购买数量
            orderItem.setNum(num);
            //单价*数量
            orderItem.setPayMoney(orderItem.getNum() * orderItem.getPrice());
            //商品图片
            orderItem.setImage(sku.getImages());

            /**
             *存储到redis中
             * hash类型：username作为命名空间（namespace）的一部分，key（skuId） -> orderItem（商品）
             */
            redisTemplate.boundHashOps("Cart_" + username).put(skuId, orderItem);
        }
    }

    @Override
    public List<OrderItem> list(String username) {
        return redisTemplate.boundHashOps("Cart_" + username).values();
    }
}
