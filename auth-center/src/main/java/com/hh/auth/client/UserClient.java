package com.hh.auth.client;

import com.hh.legou.security.api.UserApi;
import com.hh.legou.security.po.Role;
import com.hh.legou.security.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 12/11/2023 14:16
 */
@FeignClient(name = "security-service", fallback = UserClient.UserClientFallback.class)
public interface UserClient extends UserApi {
    @Component
    @RequestMapping("/user-auth-fallback")
    class UserClientFallback implements UserClient {
        public static final Logger log = LoggerFactory.getLogger(UserClient.class);

        /**
         * @param userName
         * @return
         */
        @Override
        public User getByUserName(String userName) {
            log.info("异常发生，进入fallback方法");
            return null;
        }

        /**
         * @param id
         * @return
         */
        @Override
        public List<Role> selectRolesByUserId(Long id) {
            log.info("异常发生，进入fallback方法");
            return null;
        }
    }
}
