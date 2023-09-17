package com.hh.legou.canal.client;


import com.hh.legou.item.api.CategoryApi;
import com.hh.legou.item.po.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 17/09/2023 17:24
 */
@FeignClient(name = "item-service", fallback = CategoryClient.CategoryClientFallback.class)
public interface CategoryClient extends CategoryApi {
    @Component
    @RequestMapping("/item/category-fallback")//要避免容器中重复的requestMapping
    class CategoryClientFallback implements CategoryClient {
        private static final Logger LOGGER = LoggerFactory.getLogger(CategoryClientFallback.class);

        @Override
        public List<Category> list(Category category) {
            LOGGER.info("异常，进入fallback方法");
            return null;
        }
    }
}
