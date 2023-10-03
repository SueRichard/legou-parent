package com.hh.legou.search.service;

import com.hh.legou.item.po.Brand;
import com.hh.legou.search.client.BrandClient;
import com.hh.legou.search.dao.GoodsDao;
import com.hh.legou.search.po.Goods;
import com.hh.legou.search.po.SearchRequest;
import com.hh.legou.search.po.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 24/09/2023 16:34
 */
@Service
public class SearchService {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private BrandClient brandClient;

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
        BoolQueryBuilder boolQueryBuilder = buildBasicQueryWithFilter(key);
        queryBuilder.withQuery(boolQueryBuilder);

        //品牌聚合查询
        String brandAggName = "brands";
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));

        //查询结果
        AggregatedPage<Goods> goodsResult = (AggregatedPage<Goods>) goodsDao.search(queryBuilder.build());

        List<Brand> brands = getBrandAgg(goodsResult, brandAggName);

        //分页数据
        long total = goodsResult.getTotalElements();
        long totalPages = goodsResult.getTotalPages();
        return new SearchResult(total, totalPages, goodsResult.getContent(), null, brands, null);
    }

    /**
     * 基础查询
     *
     * @param key
     * @return
     */
    private BoolQueryBuilder buildBasicQueryWithFilter(String key) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("all", key));
        return boolQueryBuilder;
    }

    /**
     * 品牌聚合
     *
     * @param goodsResult
     * @param brandAggName
     * @return
     */
    private List<Brand> getBrandAgg(AggregatedPage<Goods> goodsResult, String brandAggName) {
        //品牌聚合结果
        LongTerms longTerms = (LongTerms) goodsResult.getAggregation(brandAggName);
        List<Long> brandIds = new ArrayList<>();
        for (LongTerms.Bucket bucket : longTerms.getBuckets()) {
            brandIds.add(bucket.getKeyAsNumber().longValue());
        }
        List<Brand> brands = brandClient.selectBrandByIds(brandIds);
        return brands;
    }
}
