package com.hh.legou.search.client;

import com.hh.legou.item.api.SkuApi;
import com.hh.legou.item.po.Sku;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 23/09/2023 10:13
 */
@FeignClient(name = "item-service", fallback = SkuClient.SkuClientFallback.class)
public interface SkuClient extends SkuApi {
    @Component
    @RequestMapping("/item/sku-fallback")
    class SkuClientFallback implements SkuClient {
        public static final Logger log = LoggerFactory.getLogger(SkuClientFallback.class);

        @Override
        public List<Sku> selectSkusBySpuId(Long SpuId) {
            log.info("异常发生，进入fallback方法");
            return null;
        }
    }
}
