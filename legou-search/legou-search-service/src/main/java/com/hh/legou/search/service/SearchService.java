package com.hh.legou.search.service;

import com.hh.legou.item.po.Brand;
import com.hh.legou.item.po.Category;
import com.hh.legou.item.po.SpecParam;
import com.hh.legou.search.client.BrandClient;
import com.hh.legou.search.client.CategoryClient;
import com.hh.legou.search.client.SpecParamClient;
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
import java.util.Map;

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

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SpecParamClient specParamClient;

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

        //品牌聚合查询
        String categoryAggName = "categories";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));

        //查询结果
        AggregatedPage<Goods> goodsResult = (AggregatedPage<Goods>) goodsDao.search(queryBuilder.build());

        //聚合结果
        List<Brand> brands = getBrandAgg(goodsResult, brandAggName);
        List<Category> categories = getCategoryAgg(categoryAggName, goodsResult);

        //统计规格参数
        List<Map<String, Object>> specs = null;
        if (categories.size() == 1) {
            specs = getSpecs(categories.get(0).getId(), boolQueryBuilder);
        }
        //分页数据
        long total = goodsResult.getTotalElements();
        long totalPages = goodsResult.getTotalPages();
        return new SearchResult(total, totalPages, goodsResult.getContent(), categories, brands, specs);
    }

    /**
     * 统计规格参数
     * 当分类结果为1行时，统计规格参数
     * 根据分类查询当前分类用于搜索的规格参数
     * 创建NativeSearchQueryBuilder，使用一样的查询key
     * 把可搜索的规格参数，依次添加到聚合
     * 处理规格参数的结果 k: 搜索的参数名 options：聚合结果的数组
     *
     * @param cid
     * @param boolQueryBuilder
     * @return
     */
    private List<Map<String, Object>> getSpecs(Long cid, BoolQueryBuilder boolQueryBuilder) {
        List<Map<String, Object>> specs = new ArrayList<>();
        SpecParam specParam = new SpecParam();
        specParam.setId(cid);
        specParam.setSearching(true);
        List<SpecParam> specParams = specParamClient.selectSpecParamApi(specParam);
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //聚合之前先查询，只有符合条件的数据才应该被查询出来
        queryBuilder.withQuery(boolQueryBuilder);

        //循环添加聚合
        for (SpecParam sp : specParams) {
            String name = sp.getName();
            queryBuilder.addAggregation(AggregationBuilders.terms(name).field("specs." + name + ".keyword"));
        }

        AggregatedPage<Goods> aggs = (AggregatedPage<Goods>) this.goodsDao.search(queryBuilder.build());
        Map<String, Aggregation> stringAggregationMap = aggs.getAggregations().asMap();

        return specs;
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

    /**
     * 获取品牌分类
     *
     * @param categoryAggName
     * @param goodsResult
     * @return
     */
    private List<Category> getCategoryAgg(String categoryAggName, AggregatedPage<Goods> goodsResult) {
        LongTerms longTerms = (LongTerms) goodsResult.getAggregation(categoryAggName);
        List<Long> categoryIds = new ArrayList<>();
        for (LongTerms.Bucket bucket : longTerms.getBuckets()) {
            categoryIds.add(bucket.getKeyAsNumber().longValue());
        }
        List<String> names = this.categoryClient.queryNamesByIds(categoryIds);
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            Category category = new Category();
            category.setId(categoryIds.get(i));
            category.setTitle(names.get(i));
            categories.add(category);
        }
        return categories;
    }
}
