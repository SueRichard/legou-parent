package com.hh.legou.search.client;

import com.hh.legou.item.api.SpuApi;
import com.hh.legou.item.po.Spu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 24/09/2023 10:23
 */
@FeignClient(name = "item-service", fallback = SpuClient.SpuClientFallback.class)
public interface SpuClient extends SpuApi {

    @Component
    @RequestMapping("item/spu-fallback")
    class SpuClientFallback implements SpuClient {
        public static final Logger log = LoggerFactory.getLogger(SpuClientFallback.class);

        @Override
        public List<Spu> selectAll() {
            log.error("异常发生，进入fallback方法");
            return null;
        }

        /**
         * @param id
         * @return
         */
        @Override
        public Spu edit(Long id) {
            log.error("异常发生，进入fallback方法");
            return null;
        }
    }

}
