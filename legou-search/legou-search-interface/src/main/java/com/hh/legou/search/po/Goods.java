package com.hh.legou.search.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author hh
 * @version 1.0
 * @time 22/09/2023 13:57
 */
@Data
@Document(indexName = "goods_legou_3", type = "docs_legou_3", shards = 1, replicas = 0)
public class Goods {

    /**
     * spu id
     */
    @Id
    private Long id;

    /**
     * 用来搜索的字段
     * 包括标题，分类，品牌
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String all;

    /**
     * 卖点
     */
    @Field(type = FieldType.Keyword, index = false)
    private String subTitle;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 一级分类
     */
    private Long cid1;

    /**
     * 二级分类
     */
    private Long cid2;

    /**
     * 三级分类
     */
    private Long cid3;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 价格
     */
    private List<Long> price;

    /**
     * sku信息
     * json格式
     */
    @Field(type = FieldType.Keyword, index = false)
    private String skus;

    /**
     * 可搜索的规格参数
     * key:参数名
     * value: 参数值
     * map里面不可设置索引了，除非这里的object是对象
     */
    private Map<String, Object> specs;
}
