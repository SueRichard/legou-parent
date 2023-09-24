package com.hh.legou.search.po;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hh
 * @version 1.0
 * @time 24/09/2023 16:03
 */
@Data
public class SearchRequest {

    /**
     * 搜索关键字
     */
    private String key;

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 排序字段
     */
    private String sortBy;

    /**
     * 升降序
     */
    private Boolean descending;

    /**
     * 规格参数 过滤
     * eg. cpu核数 -> 4核
     */
    private Map<String, String> filter = new HashMap<String, String>();

    /**
     * 每页大小
     */
    public static final Integer DEFAULT_SIZE = 20;

    /**
     * 默认页
     */
    public static final Integer DEFAULT_PAGE = 1;

    public Integer getPage() {
        if (page == null) {
            return DEFAULT_PAGE;
        }
        return Math.max(DEFAULT_PAGE, page);
    }

    /**
     * 返回固定每页大小
     *
     * @return
     */
    public Integer getSize() {
        return DEFAULT_SIZE;
    }

}
