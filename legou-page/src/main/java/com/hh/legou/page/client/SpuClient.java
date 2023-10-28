package com.hh.legou.page.client;

import com.hh.legou.item.api.SpuApi;
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
 * @time 28/10/2023 14:18
 */

@FeignClient(name = "item-service", contextId = "p3", fallback = SpuClient.SpuClientFallback.class)
public interface SpuClient extends SpuApi {
    @Component
    @RequestMapping("/spu-fallback2")
    class SpuClientFallback implements SpuClient {
        public static final Logger log = LoggerFactory.getLogger(SpuClientFallback.class);

        /**
         * @return
         */
        @Override
        public List<Spu> selectAll() {
            log.info("发生异常，进入fallback方法");
            return null;
        }

        /**
         * @param id
         * @return
         */
        @Override
        public Spu edit(Long id) {
            log.info("发生异常，进入fallback方法");
            return null;
        }
    }
}
