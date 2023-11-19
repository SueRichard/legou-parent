package com.hh.legou.order.client;

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
 * @time 19/11/2023 17:13
 */
@FeignClient(name = "item-service", fallback = SpuClient.SpuClientFallback.class)
public interface SpuClient extends SpuApi {
    @Component
    @RequestMapping("/spu-fallback2")
    class SpuClientFallback implements SpuClient {
        public static final Logger log = LoggerFactory.getLogger(SpuClientFallback.class);

        @Override
        public List<Spu> selectAll() {
            log.error("异常发生，进入fallback方法");
            return null;
        }

        @Override
        public Spu edit(Long id) {
            log.error("异常发生，进入fallback方法");
            return null;
        }
    }
}
