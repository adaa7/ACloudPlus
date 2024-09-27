package com.adaa7.common.utils;

import com.adaa7.common.exception.AccountNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtUtil {
    //私钥 生成签名的时候使用的秘钥secret，一般可以从本地配置文件中读取
    private final static String secret = "123456789";

    /**
     * 创建jwt
     */
    public static String createJwtToken(Integer id){

        // Header
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");


        //Payload
        Map<String,Object> claims = new HashMap<String,Object>();

        //自定义数据，根据业务需要添加
        claims.put("id",id);

        //生成jwt
        JwtBuilder jwtBuilder = Jwts.builder()
                .setHeader(map)         // 添加Header信息
                .setClaims(claims)      // 添加Payload信息
                .setId(UUID.randomUUID().toString()) // 设置jti：是JWT的唯一标识
                .setIssuedAt(new Date())       // 设置iat: jwt的签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 10000)) // 设置exp：jwt过期时间，3600秒=1小时
                .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))//设置签名：通过签名算法和秘钥生成签名
                ;
        return jwtBuilder.compact();
    }

    /**
     * 从jwt中获取 Payload 信息
     */
    public static Claims getClaimsFromJwt(String jwt) {
        Claims claims = null;
        claims = Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(jwt).getBody();
        return claims;
    }

}
