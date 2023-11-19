package com.hh.legou.order.client;

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
 * @time 19/11/2023 17:01
 */
@FeignClient(name = "item-service", fallback = SkuClient.SkuClientFallback.class)
public interface SkuClient extends SkuApi {
    @Component
    @RequestMapping("/sku-fallback")
    class SkuClientFallback implements SkuClient {

        public static final Logger log = LoggerFactory.getLogger(SkuClientFallback.class);

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
    }
}
