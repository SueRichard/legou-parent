package com.hh.legou.search.client;

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
 * @time 23/09/2023 09:47
 */
@FeignClient(name = "item-service", fallback = CategoryClient.CategoryClientFallback.class)
public interface CategoryClient extends CategoryApi {
    @Component
    @RequestMapping("/item/category-fallback")
    class CategoryClientFallback implements CategoryClient {
        private static final Logger log = LoggerFactory.getLogger(CategoryClientFallback.class);

        /**
         * @param category
         * @return
         */
        @Override
        public List<Category> list(Category category) {
            log.info("异常发生，进入fallback方法");
            return null;
        }

        /**
         * @param ids
         * @return
         */
        @Override
        public List<String> queryNamesByIds(List<Long> ids) {
            log.info("异常发生，进入fallback方法");
            return null;
        }

        /**
         * @param id
         * @return
         */
        @Override
        public Category edit(Long id) {
            log.info("异常发生，进入fallback方法");
            return null;
        }
    }
}
