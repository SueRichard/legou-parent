package com.hh.legou.page.client;

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
 * @time 28/10/2023 14:05
 */
//contextId作用：
//比如有item-service服务，该服务中有很多个接口，但不想都写在一个类中，就用contextId作区分
//否则需要在pom中配置 allow-bean-definition-overriding: true
@FeignClient(name = "item-service", contextId = "p1", fallback = CategoryClient.CategoryClientFallback.class)
public interface CategoryClient extends CategoryApi {
    @Component
    @RequestMapping("/category-fallback2")//避免容器中的requestMapping重复
    class CategoryClientFallback implements CategoryClient {
        public static final Logger log = LoggerFactory.getLogger(CategoryClientFallback.class);

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
