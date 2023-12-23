package com.hh.legou.order.client;

import com.hh.legou.item.api.SkuApi;
import com.hh.legou.item.po.Sku;
import com.hh.legou.item.po.Spu;
import feign.hystrix.FallbackFactory;
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

//fallback和fallbackFactory只能存在一个
@FeignClient(name = "item-service", fallbackFactory = SkuClient.SkuClientFallbackFactory.class)
//@FeignClient(name = "item-service", fallback = SkuClient.SkuClientFallback.class)
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

    /**
     * fallbackFactory 可以处理异常
     */
    @Component
    @RequestMapping("/sku-fallback-factory")
    class SkuClientFallbackFactory implements FallbackFactory<SkuClient> {
        Logger log = LoggerFactory.getLogger(SkuClientFallbackFactory.class);

        @Override
        public SkuClient create(Throwable cause) {
            cause.printStackTrace();
            log.error(cause.getMessage());
            return new SkuClient() {
                @Override
                public List<Sku> selectSkusBySpuId(Long SpuId) {
                    return null;
                }

                @Override
                public Sku edit(Long id) {
                    return null;
                }
            };
        }
    }
}
