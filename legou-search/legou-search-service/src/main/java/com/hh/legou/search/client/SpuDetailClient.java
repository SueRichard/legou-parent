package com.hh.legou.search.client;

import com.hh.legou.item.api.SpuDetailApi;
import com.hh.legou.item.po.SpuDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hh
 * @version 1.0
 * @time 24/09/2023 10:35
 */
@FeignClient(name = "item-service", fallback = SpuDetailClient.SpuDetailClientFallback.class)
public interface SpuDetailClient extends SpuDetailApi {
    @Component
    @RequestMapping("/item/spu-detail-fallback")
    class SpuDetailClientFallback implements SpuDetailClient {

        public static final Logger log = LoggerFactory.getLogger(SpuDetailClientFallback.class);

        /**
         * @param id
         * @return
         */
        @Override
        public SpuDetail edit(Long id) {
            log.error("异常发生，进入fallback方法");
            return null;
        }
    }
}
