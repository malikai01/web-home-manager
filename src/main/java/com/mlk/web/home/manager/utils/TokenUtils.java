package com.mlk.web.home.manager.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * Token 生成
 * Created by malikai on 2018-7-12.
 */
public class TokenUtils {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 签名秘钥
     */
    public static final String SECRET = "malikai";
    private static final String issuer = "www.mlkfamilymanager.com";
    private static final String subject = "malikai@hujiang.com";

    /**
     * 生成token
     * @param id 一般传入userName
     * @return
     */
    public static String createJwtToken(String id){
        long ttlMillis = System.currentTimeMillis();
        return createJwtToken(id, issuer, subject, ttlMillis);
    }

    /**
     * 生成Token
     *
     * @param id
     *            编号
     * @param issuer
     *            该JWT的签发者，是否使用是可选的
     * @param subject
     *            该JWT所面向的用户，是否使用是可选的；
     * @param ttlMillis
     *            签发时间
     * @return token String
     */
    public static String createJwtToken(String id, String issuer, String subject, long ttlMillis) {

        // 签名算法 ，将对token进行签名
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成签发时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 通过秘钥签名JWT
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        // if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();

    }

    // Sample method to validate and read the JWT
    public static Claims parseJWT(String jwt) {
        // This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
    public static boolean validateJWT(Map<String, Object> map){
        boolean flag = false;
        if(EmptyUtils.isNotEmpty(map)){
            String iss = (String) map.get("iss");
            String sub = (String) map.get("sub");
            if(iss.equals(issuer) && sub.equals(subject)){
                flag = true;
            }
        }
        return flag;
    }

    public static void main(String[] args) {
        System.out.println(TokenUtils.createJwtToken("admin"));
        System.out.println(TokenUtils.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhZG1pbiIsImlhdCI6MTUzMTM2NTgxOSwic3ViIjoibWFsaWthaUBodWppYW5nLmNvbSIsImlzcyI6Ind3dy5tbGtmYW1pbHltYW5hZ2VyLmNvbSIsImV4cCI6MzA2MjczMTYzNX0.BeyIXRQ7bu34DzLGWLW39Qw3zQBq2gR5HV581Oh1j5I"));
    }
}
