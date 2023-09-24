package com.hh.legou.search.po;

import com.hh.legou.item.po.Brand;
import com.hh.legou.item.po.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author hh
 * @version 1.0
 * @time 24/09/2023 16:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {

    /**
     * 总行数
     */
    private Long total;

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 当前页数据
     */
    private List items;

    /**
     * 分类
     */
    private List<Category> categories;

    /**
     * 品牌
     */
    private List<Brand> brands;

    /**
     * 规格参数
     */
    private List<Map<String, Object>> specs;
}
