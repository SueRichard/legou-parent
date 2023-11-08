package com.hh.cloud;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hh
 * @version 1.0
 * @time 08/11/2023 15:33
 */
public class TestJwt {

    /**
     * jwt生成
     */
    @Test
    public void createJwt() {
        long l1 = System.currentTimeMillis();//设置过期时间
        long l2 = l1 + 35000;
        JwtBuilder builder = Jwts.builder()
                .setId("123456")//唯一编号？存储在payload
                .setSubject("jwt使用声明")//主题？存储在payload
                .setIssuedAt(new Date())//签发时间
                .setExpiration(new Date(l2))//过期时间
                .claim("role", "admin")//自定义载荷
                //第一个参数是签名的加密算法，采用hs256加密，第二个参数是秘钥，秘钥有长度限制（6位），否则报空指针异常
                .signWith(SignatureAlgorithm.HS256, "hldhlg");
        //自定义载荷
        Map<String, Object> map = new HashMap<>();
        map.put("address", "tl");
        map.put("name", "h2");
        builder.addClaims(map);
        System.out.println(builder.compact());
    }

    /**
     * jwt解析
     */
    @Test
    public void testParseJwt() {
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NTYiLCJzdWIiOiJqd3Tkvb_nlKjlo7DmmI4iLCJpYXQiOjE2OTk0MzI0NjAsImV4cCI6MTY5OTQzMjQ5NSwicm9sZSI6ImFkbWluIiwiYWRkcmVzcyI6InRsIiwibmFtZSI6ImgyIn0.4rgvyw8o3HDcPWk6CFWhMZMB-4oOfjzNgX4t41j4zEg";
        Claims claims = Jwts.parser()
                .setSigningKey("hldhlg")
                //注意方法名
                .parseClaimsJws(jwt)
                .getBody();
        System.out.println(claims);
        //获取自定义载荷
        System.out.println(claims.get("role"));
        //获取标准载荷
        System.out.println(claims.getSubject());
    }

}
