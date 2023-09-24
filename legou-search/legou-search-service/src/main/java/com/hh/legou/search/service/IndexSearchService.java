package com.hh.legou.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hh.legou.common.utils.JsonUtils;
import com.hh.legou.item.po.Sku;
import com.hh.legou.item.po.SpecParam;
import com.hh.legou.item.po.Spu;
import com.hh.legou.item.po.SpuDetail;
import com.hh.legou.search.client.CategoryClient;
import com.hh.legou.search.client.SkuClient;
import com.hh.legou.search.client.SpecParamClient;
import com.hh.legou.search.client.SpuDetailClient;
import com.hh.legou.search.dao.GoodsDao;
import com.hh.legou.search.po.Goods;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author hh
 * @version 1.0
 * @time 24/09/2023 14:22
 */
@Service
public class IndexSearchService {
    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SkuClient skuClient;

    @Autowired
    private SpuDetailClient spuDetailClient;

    @Autowired
    private SpecParamClient specParamClient;

    @Autowired
    private GoodsDao goodsDao;

    public Goods buildGoods(Spu spu) {
        //all
        List<String> names = categoryClient.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        String all = spu.getTitle() + " " + StringUtils.join(names, " ");

        //skus
        List<Sku> skus = skuClient.selectSkusBySpuId(spu.getId());
        List<Long> price = new ArrayList<Long>();
        List<Map<String, Object>> skuList = new ArrayList<>();
        for (Sku sku : skus) {
            price.add(sku.getPrice());
            Map<String, Object> skuMap = new HashMap<>();
            skuMap.put("id", sku.getId());
            skuMap.put("title", sku.getTitle());
            skuMap.put("image", StringUtils.isBlank(sku.getImages()) ? "" : sku.getImages().split(",")[0]);
            skuMap.put("price", sku.getPrice());
            skuList.add(skuMap);
        }

        //specs
        Map<String, Object> specs = new HashMap<>();
        SpuDetail spuDetail = spuDetailClient.edit(spu.getId());

        //通用规格参数
        Map<String, String> genericMap = JsonUtils.parseMap(spuDetail.getGenericSpec(), String.class, String.class);

        //特有规格参数
        Map<String, List<String>> specialMap = JsonUtils.nativeRead(spuDetail.getSpecialSpec(), new TypeReference<Map<String, List<String>>>() {
        });

        //查询分类对应的规格参数
        SpecParam specParam = new SpecParam();
        specParam.setCid(spu.getCid3());
        specParam.setSearching(true);
        List<SpecParam> specParams = specParamClient.selectSpecParamApi(specParam);
        for (SpecParam param : specParams) {
            String name = param.getName();
            Object value = null;
            if (param.getGeneric()) {
                //通用参数
                value = genericMap.get(name);
                if (param.getNumeric()) {
                    value = this.chooseSegment(value.toString(), param);
                }
            } else {
                //特有规格参数
                value = specialMap.get(name);
            }
            if (null == value) {
                value = "其他";
            }
            specs.put(name, value);
        }
        //查询相关数据存入goods
        Goods goods = new Goods();
        goods.setId(spu.getId());
        //如果要加品牌，写一个brandClient，根据id查品牌
        goods.setAll(all);
        goods.setSubTitle(spu.getSubTitle());
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setPrice(price);
        goods.setSkus(JsonUtils.serialize(skuList));
        goods.setSpecs(specs);

        return goods;
    }

    private String chooseSegment(String value, SpecParam param) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        for (String segment : param.getSegments().split(",")) {
            String[] segs = segment.split("-");
            //获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if (segs.length == 2) {
                end = NumberUtils.toDouble(segs[1]);
            }
            //判断是否在范围内
            if (val >= begin && val < end) {
                if (segs.length == 1) {
                    result = segs[0] + param.getUnit() + "以上";
                } else if (begin == 0) {
                    result = segs[1] + param.getUnit() + "以下";
                } else {
                    result = segment + param.getUnit();//eg. 10-12英寸
                }
                break;
            }
        }
        return result;
    }

    /**
     * 根据id删除索引
     *
     * @param id
     */
    public void deleteIndex(Long id) {
        goodsDao.deleteById(id);
    }
}
