package com.hh.legou.search.dao;

import com.hh.legou.search.po.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author hh
 * @version 1.0
 * @time 24/09/2023 11:14
 */
public interface GoodsDao extends ElasticsearchRepository<Goods, Long> {
    //spring-data 规范：传入实体类，spring-data 所操作的主键类型，增删改查spring-data就会自己写好
}
