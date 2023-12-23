package com.hh.auth.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateCrtKey;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成管理员令牌
 *
 * @author hh
 * @version 1.0
 * @time 23/12/2023 16:55
 */
public class AdminToken {
    /**
     * 使用私钥生成令牌
     *
     * @throws JsonProcessingException
     */
    public static String adminToken() throws JsonProcessingException {
        //存储秘钥的工厂对象 访问密钥库密码hh0000
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("kaikeba.jks"), "kaikeba".toCharArray());
        //密钥对（公钥 -> 私钥）秘钥密码hh1234
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("kaikeba", "kaikeba".toCharArray());
        //私钥
        RSAPrivateCrtKey privateKey = (RSAPrivateCrtKey) keyPair.getPrivate();
        //自定义payload信息
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("user_name", "admin");
        tokenMap.put("authorities", new String[]{"ROLE_ADMIN"});
        tokenMap.put("client_id", "client");
        //使用工具类，通过私钥颁发jwt令牌
        Jwt jwt = JwtHelper.encode(new ObjectMapper().writeValueAsString(tokenMap), new RsaSigner(privateKey));
        String token = jwt.getEncoded();
        return token;
    }
}
