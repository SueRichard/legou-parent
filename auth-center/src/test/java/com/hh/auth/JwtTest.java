package com.hh.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateCrtKey;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author hh
 * @version 1.0
 * @time 18/11/2023 16:06
 */
/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class JwtTest {

    /**
     * 查看秘钥对命令
     * 对应秘钥文件路径执行：keytool -list -rfc --keystore hh.jks | openssl x509 -inform pem -pubkey
     */

    /**
     * 使用私钥生成令牌
     *
     * @throws JsonProcessingException
     */
    @Test
    public void testCreateJwt() throws JsonProcessingException {
        //存储秘钥的工厂对象 访问密钥库密码hh0000
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("hh.jks"), "hh0000".toCharArray());
        //密钥对（公钥 -> 私钥）秘钥密码hh1234
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("hh", "hh1234".toCharArray());
        //私钥
        RSAPrivateCrtKey privateKey = (RSAPrivateCrtKey) keyPair.getPrivate();
        //自定义payload信息
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("id", 1);
        tokenMap.put("name", "sue");
        tokenMap.put("role", "admin,user");
        //使用工具类，通过私钥颁发jwt令牌
        Jwt jwt = JwtHelper.encode(new ObjectMapper().writeValueAsString(tokenMap), new RsaSigner(privateKey));
        String token = jwt.getEncoded();
        System.out.println(token);
    }

    /**
     * 使用公钥校验令牌
     */
    @Test
    public void testVerify() {
        //上面生成的令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYWRtaW4sdXNlciIsIm5hbWUiOiJzdWUiLCJpZCI6MX0.QsP4jwbeaiyyMrdhwLHc6QXOrEPSOKdncpD6AJb9dVNoqQ73TLGo3VkR-_L0HeC8laBJE2WEkBMV27LIrUueAik8k2HXfsIDUzpOuNQlO_JCdiiTrn52YQ1YzkmEysD5ZTw3AH8QQe0MS37eR81Y2s34pLIn9evVt0Z3gWckhDUMVi9D2DeM88NlVx6nNwOwFV6e1cQqnnrr9wK56Cp-SvxNCM_rLYrbu0H0pK_LCGTxnv6fprfyB64lgZ1nJ5LYpk7Pp3P9Xu3GOtQyGmQx5ZI-2s009vnd-ihEQ54op5YGhJ_dEx7uT2o9ewL2-rFuzSD4BDaATDzItN7ZNSphHA";
        //公钥，这里的换行可以不要 hh的公钥
        String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoTT/OfbabBjeJpAEhb9H\n" +
                "bk0kKbOaHsOTNy0ogIZ7gxEwcOGDz7Ec4y1vl2zDRkGfodx9yIxExIJS12WhsAZc\n" +
                "RHgqPnl5H3stXgfu7+AHtShQAnMtwCmTqME9v+8j95XL9JliL9GRsK7WCa9kB51S\n" +
                "QsH+rwWSMufGD6IZtscLVfmV+2izk0ez2CPiQNudcRShlZ9iUKEG3IFZXjZeP+wQ\n" +
                "NkxePxqKSW7VkPhCy/kk6sK0zvJtgd/U4Uvubyuo3FILJ/liN4SyFSWqYhNWjguA\n" +
                "HuCUxYG+LKR0AdWeA11ZDpenCB6fT6t0t2U15HknG9fW8AEUVSoTMpKTe7xT/7QQ\n" +
                "SwIDAQAB\n" +
                "-----END PUBLIC KEY-----";
        //校验令牌
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
        String claims = jwt.getClaims();
        System.out.println(claims);
    }

    /**
     * 使用私钥生成令牌
     *
     * @throws JsonProcessingException
     */
    @Test
    public void testCreateAdminJwt() throws JsonProcessingException {
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
        System.out.println(token);
    }

    /**
     * 使用公钥校验令牌
     */
    @Test
    public void getUsernameByToken() {
        //上面生成的令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MDMzMzYxOTAsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9NQU5BR0VSIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiJhNTQ3MjMxNi02YjllLTQ1NmQtODIyZC0xMWYzNDA5YTRmZmUiLCJjbGllbnRfaWQiOiJjbGllbnQiLCJzY29wZSI6WyJyZWFkIl19.mbOJDyuWtJ4rWZHH8_Wb7htcjiBQhPINcZlkW5ysbvifXHbpFfa8FOdI2LSY5HvzkYn6mXi9lQ735ymswOcBpp9Zxa8ix_G_0wNy0njM-tgE8YHjDP9FkEjsC-C28Bl1EuolERqQrQLcaFpKk1Z0NnBIoZFQCSuzrnKq7zHvMP3TFNDG86U6mKMAaoPAAUjSXjWRKJR-I6stO_WN25Kos_1b1mmIHiRpu34yLshgjZRicUAF83-utIrIdLPFv2fs8v-0-VAP_78-xapgqWD3AT6kZ4_aqrCTIQYtUrzaqTNCT-1USlV1EE1L9xiQKnPYii6oxn7I0JoNx9O9zOpJIQ";
        //公钥，这里的换行可以不要 kaikeba的公钥
        String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwtYpjt7NtpS1B51x6PUK\n" +
                "7ryvKySK4VQi7KUCGBm6kisErNM+FwdgKMbpQxTtWoYyXfQsWwuhBW45+uF+Z5DU\n" +
                "DaLtHlMV55eA5fkGLFZ1F9ppZC+2Etsy1CyPqA0Mx8R0/HbMB1no4KTlQpqST7Jj\n" +
                "CdtwLWqUd68zDlfToIsWB1fHuYHbH/DCGUBmZb+16805/SjWkYvj3B6F+WJ8Gm47\n" +
                "/OJBH+wo7k4GWZ7OXdMcNnYWMyBfa4abjo7cxjoHL2fDanS6And4Sh3cZEJde4Wg\n" +
                "XsEktvR/EaZR7CeQzwzOg47+5cCcFSYgmVfpDyLsBnFkG3WFs/qZ3yPzy+DQKLIF\n" +
                "2wIDAQAB\n" +
                "-----END PUBLIC KEY-----";
        //校验令牌
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
        String claims = jwt.getClaims();
        System.out.println(claims);
        try {
            Map<String, String> map = new ObjectMapper().readValue(claims, Map.class);
            String username = map.get("user_name");
            System.out.println(username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}