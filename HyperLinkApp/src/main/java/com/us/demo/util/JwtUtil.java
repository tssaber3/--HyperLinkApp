package com.us.demo.util;

import com.us.demo.entity.User;
import com.us.demo.security.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;

/**
 * @author xiaorui
 */
public class JwtUtil {

    // 设置公钥和秘钥
    private static final String SECRET_KEY = "abc1234";
    private static final String PUBLIC_KEY = "abc1234";

    /**
     * 生成jwt
     * @param ttlMillis  过期时间
     * @param user  对用户进行jwt生成
     * @return 生成的jwt
     */
    public static String createJwt(long ttlMillis, JwtUser user) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成jwt的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 创建plyload私有声明
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("password", user.getPassword());
        claims.put("public_key", PUBLIC_KEY);


        // 创建签发人
        String subject = user.getUsername();

        // 创建jwt
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, SECRET_KEY);
        if (ttlMillis > 0) {
            long expMillis = ttlMillis + nowMillis;
            Date expTime = new Date(expMillis);
            // 设置过期时间
            builder.setExpiration(expTime);
        }
        return builder.compact();
    }


    /**
     * 生成jwt
     * @param ttlMinute  过期时间 以分钟做单位
     * @param user  对用户进行jwt生成
     * @return 生成的jwt
     */
    public static String createJwt(int ttlMinute , User user)
    {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        String token = "";
        Calendar nowtime = Calendar.getInstance();
        //当前时间
        Date now = nowtime.getTime();


        // 创建plyload私有声明
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("public_key", PUBLIC_KEY);

        //创建签发人
        String subject = user.getUsername();

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, SECRET_KEY);
        //加时间是以分钟作为单位
        if(ttlMinute > 0)
        {
            nowtime.add(Calendar.MINUTE,ttlMinute);
            Date exp = nowtime.getTime();
            // 设置过期时间
            builder.setExpiration(exp);
        }
        return builder.compact();
    }
    /**
     * token解密
     *
     * @param token 传入token并进行解密
     * @return  解密后的plyload主体
     */
    public static Claims parseJWT(String token) {
        String key = SECRET_KEY;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 校验token
     * @param token 传入token并通过公钥认证他
     * @return  是否校验通过
     */
    public static boolean isVerify(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token).getBody();
            if (claims.get("public_key").equals(PUBLIC_KEY)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
