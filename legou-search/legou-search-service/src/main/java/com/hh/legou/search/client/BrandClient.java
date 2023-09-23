package com.hh.legou.search.client;

import com.hh.legou.item.api.BrandApi;
import com.hh.legou.item.po.Brand;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 23/09/2023 09:05
 */
@FeignClient(name = "item-service", fallback = BrandClient.BrandClientFallback.class)
public interface BrandClient extends BrandApi {

    @Component
    @RequestMapping("/item/brand-fallback")
    class BrandClientFallback implements BrandClient {

        private static final Logger log = LoggerFactory.getLogger(BrandClientFallback.class);

        @Override
        public List<Brand> selectBrandByIds(List<Long> ids) {
            log.info("异常发生，进入fallback方法");
//            return Collections.emptyList();
            return null;
        }
    }
}
