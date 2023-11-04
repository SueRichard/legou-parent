package com.hh.legou.page.client;

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
 * @time 28/10/2023 14:37
 */
@FeignClient(name = "item-service", contextId = "p4", fallback = SpuDetailClient.SpuDetailClientFallback.class)
public interface SpuDetailClient extends SpuDetailApi {
    @Component
    @RequestMapping("/spu-detail-fallback2")
    class SpuDetailClientFallback implements SpuDetailClient {
        public static final Logger log = LoggerFactory.getLogger(SpuDetailClientFallback.class);

        /**
         * @param id
         * @return
         */
        @Override
        public SpuDetail edit(Long id) {
            log.info("异常，进入fallback方法");
            return null;
        }
    }
}
