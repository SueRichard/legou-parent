package com.hh.legou.search;


import com.hh.legou.item.po.Spu;
import com.hh.legou.search.client.SpuClient;
import com.hh.legou.search.dao.GoodsDao;
import com.hh.legou.search.po.Goods;
import com.hh.legou.search.service.IndexSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hh
 * @version 1.0
 * @time 24/09/2023 15:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class EsLoadDataTest {

    @Autowired
    private IndexSearchService indexSearchService;

    @Autowired
    private SpuClient spuClient;

    @Autowired
    private GoodsDao goodsDao;

    @Test
    public void loadData() {
        List<Spu> spus = spuClient.selectAll();

        //spus转成goods
        List<Goods> goods = spus.stream().map(spu -> this.indexSearchService.buildGoods(spu)).collect(Collectors.toList());

        //导入索引库
        goodsDao.saveAll(goods);
    }
}
