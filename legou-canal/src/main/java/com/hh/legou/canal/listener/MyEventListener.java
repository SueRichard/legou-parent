package com.hh.legou.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.legou.canal.client.CategoryClient;
import com.hh.legou.item.po.Category;
import com.xpand.starter.canal.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 17/09/2023 11:33
 */
//监听canal服务，即监听数据库的变化
@CanalEventListener
public class MyEventListener {

    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 当数据被添加的时候触发
     *
     * @param eventType CanalEntry.EventType 内部类 监听的操作类型 INSERT UPDATE DELETE CREATE INDEX GRAND
     * @param rowData   CanalEntry.RowData 内部类 被修改的数据
     */
    @InsertListenPoint
    public void onEventInsert(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        //do something...
        System.out.println("添加数据的监听。。。。。。");
        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
        for (CanalEntry.Column column : afterColumnsList) {
            System.out.println(column.getName() + ":" + column.getValue());
        }
    }

    /**
     * 修改数据触发的监听
     *
     * @param eventType
     * @param rowData
     */
    @UpdateListenPoint
    public void onEventUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        // do something...
        System.out.println("修改数据监听。。。。。。");
        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
        for (CanalEntry.Column column : afterColumnsList) {
            System.out.println(column.getName() + ":" + column.getValue());
        }
    }

    /**
     * 删除数据的监听
     *
     * @param eventType
     * @param rowData
     */
    @DeleteListenPoint
    public void onEventDelete(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        //do something...
        System.out.println("删除数据监听。。。");
        //获取删除数据前的数据
        List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
        for (CanalEntry.Column column : beforeColumnsList) {
            System.out.println(column.getName() + ":" + column.getValue());
        }
    }


    /**
     * 自定义监听
     * 注解属性解释：
     * destination指明目的地，要和配置文件中一样 即canal服务中canal-server/conf/example 文件夹 本服务在docker的canal中
     * schema 要监听的库（实例）
     * table {"",""} 要监听的表，多个逗号隔开
     * eventType 要监听的类型
     *
     * @param event
     * @param rowData
     */
    @ListenPoint(destination = "example", schema = "legou", table = {"category_"}, eventType = {CanalEntry.EventType.INSERT, CanalEntry.EventType.UPDATE, CanalEntry.EventType.DELETE})
    public void onEventCustom(CanalEntry.EventType event, CanalEntry.RowData rowData) throws JsonProcessingException {
        //do something...
        System.out.println("只监听分类表。。。");
        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
        for (CanalEntry.Column column : afterColumnsList) {
            System.out.println(column.getName() + ":" + column.getValue());
        }

        //监听到分类表发生改变后，重新查询所有数据，然后存入redis
        List<Category> list = categoryClient.list(new Category());
        ObjectMapper objectMapper = new ObjectMapper();
        stringRedisTemplate.boundValueOps("cl").set(objectMapper.writeValueAsString(list));
    }
}
