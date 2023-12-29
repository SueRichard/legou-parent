package com.hh.legou.page.client;

import com.hh.legou.item.api.SkuApi;
import com.hh.legou.item.po.Sku;
import com.hh.legou.item.po.Spu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 28/10/2023 14:41
 */
@FeignClient(name = "item-service", contextId = "p2", fallback = SkuClient.SkuClientFallback.class)
public interface SkuClient extends SkuApi {
    @Component
    @RequestMapping("/sku-fallback2")
    class SkuClientFallback implements SkuClient {
        public static final Logger log = LoggerFactory.getLogger(SkuClientFallback.class);

        /**
         * @param SpuId
         * @return
         */
        @Override
        public List<Sku> selectSkusBySpuId(Long SpuId) {
            log.error("异常发生，进入fallback方法");
            return null;
        }

        @Override
        public Sku edit(Long id) {
            log.error("异常发生，进入fallback方法");
            return null;
        }

        @Override
        public void decrCount(Integer num, Long skuId) {
            log.error("异常发生，进入fallback方法");
        }
    }
}
