package org.oz.association_boot.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Component
@Log4j2
public class JWTUtil {

    private static String key = "fj92#@$%!4wjw4+#%@%+---f4w4f44+4+f42v.,/,32@!#T@F$!F";

    public String createToken(Map<String, Object> valueMap, int min) {

        SecretKey key = null;

        try{
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        return Jwts.builder().header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                .issuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .expiration((Date.from(ZonedDateTime.now()
                                    .plusMinutes(min).toInstant())))
                .claims(valueMap)
                .signWith(key)
                .compact();
    }

    public Map<String, Object> validateToken(String token){

        SecretKey key = null;

        try{
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        Claims claims = Jwts.parser().verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims;
    }

//    public static String getAdminNameFromJWT() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // JwtAuthenticationToken인지 확인
//        if (authentication instanceof JwtAuthenticationToken) {
//            Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
//            // JWT Claims에서 "adminName"을 추출
//            return (String) jwt.getClaims().get("adminName");
//        }
//
//        return null; // JWT 인증이 아니거나, adminName이 없을 경우
//    }
}
