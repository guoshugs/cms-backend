package com.leadnews.common.util;

import com.leadnews.common.constants.SC;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @version 1.0
 * @description 标题
 * @package com.leadnews.common.util
 */
public class AppJwtUtil {
    // TOKEN的有效期一秒（S），原本是1s
    private static final long TOKEN_TIME_OUT =  100 * 24 * 60 * 60; // long防止越界
    // 加密KEY
    private static final String TOKEN_ENCRY_KEY = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY";
    // 最小刷新间隔(S)
    private static final int REFRESH_TIME = 300;

    // 生产ID

    public static String createToken(Map<String, Object> claimMaps) {
        // claimMaps 载荷内容
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(currentTime))  //签发时间
                .setSubject("system")  //说明
                .setIssuer("heima") //签发者信息
                .setAudience("app")  //接收用户
                .compressWith(CompressionCodecs.GZIP)  //数据压缩方式，如果想要测试解码，需要把压缩方式注释
                .signWith(SignatureAlgorithm.HS512, generalKey()) //加密方式
                //过期一个小时
                .setExpiration(new Date(currentTime + TOKEN_TIME_OUT * 1000))  //过期时间戳
                .addClaims(claimMaps) //cla信息
                .compact();
    }


    public static String createToken(Long id) {
        // 载荷
        Map<String, Object> claimMaps = new HashMap<>();
        claimMaps.put("id", id);
        return createToken(claimMaps);
    }

    /**
     * 获取token中的claims信息
     *
     * @param token
     * @return
     */
    private static Jws<Claims> getJws(String token) {
        return Jwts.parser()
            .setSigningKey(generalKey())
            .parseClaimsJws(token);
    }

    /**
     * 获取payload body信息
     *
     * @param token
     * @return
     */
    public static Claims getClaimsBody(String token) {
        return getJws(token).getBody();
    }

    /**
     * 获取hearder body信息
     *
     * @param token
     * @return
     */
    public static JwsHeader getHeaderBody(String token) {
        return getJws(token).getHeader();
    }

    /**
     * 是否过期
     *
     * @param token
     * @return 1 有效  0 无效  2 已过期
     */
    public static int verifyToken(String token) {
        try {
            Claims claims = AppJwtUtil.getClaimsBody(token);
            return SC.JWT_OK;
        } catch (ExpiredJwtException ex) {
            return SC.JWT_EXPIRE;
        } catch (Exception e) {
            return SC.JWT_FAIL;
        }
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(TOKEN_ENCRY_KEY.getBytes());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static void main(String[] args) {
       /* Map map = new HashMap();
        map.put("id","11");*/
        String token = AppJwtUtil.createToken(1102L);
        System.out.println(token);

        // 验证token, 获取载荷
        // claims就是一个map
        Claims claims = AppJwtUtil.getClaimsBody(token);
        System.out.println(claims.get("id", Long.class)); //1102

        // 验证超时
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = AppJwtUtil.verifyToken(token);
        System.out.println(i); //1
        Claims claims2 = AppJwtUtil.getClaimsBody(token);
        System.out.println(claims2); // {jti=8fbdd40c-d3bb-4760-8c88-844dda0b461e, iat=1680703476, sub=system, iss=heima, aud=app, exp=1680704476, id=1102}

        Integer integer = AppJwtUtil.verifyToken("dsafafsa");
        System.out.println(integer); //0

    }
}
