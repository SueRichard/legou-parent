package com.hh.cloud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.cloud.client.UserClient;
import com.hh.cloud.util.BCrypt;
import com.hh.cloud.util.JwtUtil;
import com.hh.legou.security.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author hh
 * @version 1.0
 * @time 09/11/2023 08:00
 */
//@RestController
//@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserClient userClient;

    @Autowired
    private ObjectMapper om;

    @RequestMapping("/login")
    public ResponseEntity login(String username, String password) throws JsonProcessingException {
        User user = userClient.getByUserName(username);
        //用户名不存在
        if (user == null) {
            return new ResponseEntity("用户名错误", HttpStatus.UNAUTHORIZED);
        }
        //校验密码是否一致
        if (BCrypt.checkpw(password, user.getPassword())) {
            //认证成功，准备令牌信息
            Map<String, Object> info = new HashMap<>();
            info.put("role", "user");
            info.put("success", "true");
            info.put("username", username);
            //颁发令牌，过期时间默一小时
            String jwt = JwtUtil.createJWT(UUID.randomUUID().toString(), om.writeValueAsString(info), null);
            return ResponseEntity.ok(jwt);
        } else {
            //密码错误
            return new ResponseEntity("密码错误", HttpStatus.UNAUTHORIZED);
        }
    }

}
