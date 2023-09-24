package com.hh.legou.search;

import com.hh.legou.search.po.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hh
 * @version 1.0
 * @time 24/09/2023 11:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class ElasticSearchTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testCreateIndex() {
        //创建索引库
        this.elasticsearchTemplate.createIndex(Goods.class);
        //配置映射
        this.elasticsearchTemplate.putMapping(Goods.class);
    }
}