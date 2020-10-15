package com.imi.dsbsocket.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imi.dsbsocket.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caster on 2019/9/10.
 */
public class JwtUtils {
    /**
     * log
     */
    private static Logger log = LoggerFactory.getLogger(JwtUtils.class);

    public static String getToken(Map<String,Object> map) {
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + (24L * 60L * 60L * 1000L)); // 定義此token 多久後才失效
            JWTCreator.Builder builder  = JWT.create();
            for(String key : map.keySet()){

                if(map.get(key) instanceof String){
                    builder.withClaim(key, (String)map.get(key));
                }else if(map.get(key) instanceof Integer){
                    builder.withClaim(key, (Integer)map.get(key));
                }else if(map.get(key) instanceof Date){
                    builder.withClaim(key, (Date)map.get(key));
                }else if(map.get(key) instanceof Boolean){
                    builder.withClaim(key, (Boolean)map.get(key));
                }else if(map.get(key) instanceof Long){
                    builder.withClaim(key, (Long)map.get(key));
                }else if(map.get(key) instanceof Double){
                    builder.withClaim(key, (Double)map.get(key));
                }
            }

            builder.withJWTId(System.currentTimeMillis() + ""); // token 識別ID
            builder.withIssuer(Constants.ISSUER); // JWT token簽發者
            builder.withIssuedAt(new Date(System.currentTimeMillis()));// 定義此token 發放時間
//            builder.withNotBefore(new Date(System.currentTimeMillis() + (10 * 1000))); // 定義此token 多久後才生效

            return builder.withExpiresAt(expiresAt).sign(Algorithm.HMAC512(Constants.FRONT_SECRET_KEY));

        } catch (JWTCreationException e){
            log.error(ExceptionUtil.getStackTrace(e));
        } catch (IllegalArgumentException e) {
            log.error(ExceptionUtil.getStackTrace(e));
        }
        return token;
    }

    public static DecodedJWT verifyToken(final String token) {
        DecodedJWT jwt = null;
        try {

            Verification veriftConfig = JWT.require(Algorithm.HMAC512(Constants.FRONT_SECRET_KEY));

            veriftConfig.withIssuer(Constants.ISSUER);

            JWTVerifier verifier = veriftConfig.build();

            jwt = verifier.verify(token);
        } catch (IllegalArgumentException e) {
            log.error(ExceptionUtil.getStackTrace(e));
        } catch (SignatureVerificationException e) {
            log.error("驗簽錯誤 token 遭到修改!!!");
            log.error(ExceptionUtil.getStackTrace(e));
        } catch (TokenExpiredException e) {
            log.error("token 超過時效 或 token 尚未生效 !!!");
            log.error(ExceptionUtil.getStackTrace(e));
        } catch (InvalidClaimException e) {
            log.error("token 簽發者有誤 !!!");
            log.error(ExceptionUtil.getStackTrace(e));
        } catch (JWTDecodeException e){
            log.error("token 格式有誤 !!!");
            log.error(ExceptionUtil.getStackTrace(e));
        }
        return jwt;
    }

    public static Map<String, Object> getPayLoadDataRtMap(DecodedJWT jwt){
        try {
            String jsonStr = new String(Base64.getUrlDecoder().decode(jwt.getPayload().getBytes(StandardCharsets.UTF_8)),"UTF-8");
            return new ObjectMapper().readValue(jsonStr, HashMap.class);
        } catch (Exception e) {
            log.error(" token trans payload data fail.");
            log.error(ExceptionUtil.getStackTrace(e));
        }
        return null;
    }
}
