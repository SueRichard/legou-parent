package com.hh.legou.search.client;

import com.hh.legou.item.api.SpecParamApi;
import com.hh.legou.item.po.SpecParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 24/09/2023 10:57
 */
@FeignClient(name = "item-service", fallback = SpecParamClient.SpecParamClientFallback.class)
public interface SpecParamClient extends SpecParamApi {
    @Component
    @RequestMapping("/item/param-fallback")
    class SpecParamClientFallback implements SpecParamClient {
        public static final Logger log = LoggerFactory.getLogger(SpecParamClientFallback.class);

        /**
         * @param entity
         * @return
         */
        @Override
        public List<SpecParam> selectSpecParamApi(SpecParam entity) {
            log.error("异常发生，进入fallback方法");
            return null;
        }
    }
}
