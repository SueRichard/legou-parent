package com.hh.legou.search.service;

import com.hh.legou.search.dao.GoodsDao;
import com.hh.legou.search.po.Goods;
import com.hh.legou.search.po.SearchRequest;
import com.hh.legou.search.po.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

/**
 * @author hh
 * @version 1.0
 * @time 24/09/2023 16:34
 */
@Service
public class SearchService {

    @Autowired
    private GoodsDao goodsDao;

    public SearchResult search(SearchRequest searchRequest) {
        String key = searchRequest.getKey();
        if (null == key) {
            return null;
        }
        //构建本地查询对象
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //设置返回的查询字段
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "subTitle", "skus"}, null));
        //构建布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("all", key));
        queryBuilder.withQuery(boolQueryBuilder);

        //查询结果
        Page<Goods> goodsResult = goodsDao.search(queryBuilder.build());
        //分页数据
        long total = goodsResult.getTotalElements();
        long totalPages = goodsResult.getTotalPages();
        return new SearchResult(total, totalPages, goodsResult.getContent(), null, null, null);
    }
}
