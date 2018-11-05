package com.bob.common.util;

import com.bob.common.constant.CONSTANT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.*;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JwtUtil {
    protected final static Logger logger = LoggerFactory.getLogger(JwtUtil.class);
        private final static String base64Secret = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";
        private final static int expiresSecond = 1000*60*60;

        public static Claims parseJWT(String jsonWebToken) {
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(DatatypeConverter.parseBase64Binary(base64Secret))
                        .parseClaimsJws(jsonWebToken).getBody();
                return claims;
            } catch (SignatureException | MalformedJwtException e) {
                logger.error("JwtUtil.parseJWT解析TOKEN错误：{}", e);
                return null;
            } catch (ExpiredJwtException e) {
                // jwt已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，如果过期则会抛出这个异常，我们可以抓住这个异常并作相关处理
                throw new IncorrectCredentialsException("token已过期，请重新登录！");
            }
        }

        public static String createJWT(String username) {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);

            //生成签名密钥
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Secret);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            //添加构成JWT的参数
            JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                    .claim(CONSTANT.LOGIN.LOGING_KEY_USERNAME, username)
                    .signWith(signatureAlgorithm, signingKey);
            //添加Token过期时间
            if (expiresSecond >= 0) {
                long expMillis = nowMillis + expiresSecond;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp).setNotBefore(now);
            }
            //生成JWT
            return builder.compact();
        }
    public static String getUserId(String jwt) {
        Claims claims = parseJWT(jwt);
        if (claims != null) {
            return (String) claims.get(CONSTANT.LOGIN.LOGING_KEY_USERNAME);
        } else {
            return null;
        }
    }
    }
