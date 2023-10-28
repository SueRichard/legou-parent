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
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
        BoolQueryBuilder boolQueryBuilder = buildBasicQueryWithFilter(searchRequest);
        queryBuilder.withQuery(boolQueryBuilder);

        //分页
        Integer page = searchRequest.getPage() - 1; //es分页从0开始
        Integer size = searchRequest.getSize();
        queryBuilder.withPageable(PageRequest.of(page, size));

        //排序
        String sortBy = searchRequest.getSortBy();
        Boolean desc = searchRequest.getDescending();
        if(StringUtils.isNotBlank(sortBy)){
            queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(desc ? SortOrder.DESC : SortOrder.ASC));
        }

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
     * @param cid              分类id
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
            //注意这里的.keyword，精确不分词
            queryBuilder.addAggregation(AggregationBuilders.terms(name).field("specs." + name + ".keyword"));
        }

        AggregatedPage<Goods> aggs = (AggregatedPage<Goods>) this.goodsDao.search(queryBuilder.build());
        Map<String, Aggregation> stringAggregationMap = aggs.getAggregations().asMap();

        for (SpecParam sp : specParams) {
            Map<String, Object> spec = new HashMap<>();
            String name = sp.getName();
            if (stringAggregationMap.get(name) instanceof StringTerms) {
                StringTerms stringTerms = (StringTerms) stringAggregationMap.get(name);
                List<String> val = new ArrayList<>();
                for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
                    val.add(bucket.getKeyAsString());
                }
                //规格参数名称作为键值
                spec.put("k", name);
                //对应分类下规格参数聚合
                spec.put("options", val);
                specs.add(spec);
            }
        }
        return specs;
    }

    /**
     * 基础查询
     *
     * @param searchRequest
     * @return
     */
    private BoolQueryBuilder buildBasicQueryWithFilter(SearchRequest searchRequest) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("all", searchRequest.getKey()));

        //过滤条件
        BoolQueryBuilder filterQueryBuilder = QueryBuilders.boolQuery();
        for (Map.Entry<String, String> entry : searchRequest.getFilter().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            //只有规则参数需要spec.前缀和.keyword后缀
            if (!("cid3").equals(key) && !"brandId".equals(key)) {
                key = "specs." + key + ".keyword";
            }
            //使用termQuery进行过滤
            filterQueryBuilder.must(QueryBuilders.termQuery(key, value));
        }
        return boolQueryBuilder.filter(filterQueryBuilder);
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
