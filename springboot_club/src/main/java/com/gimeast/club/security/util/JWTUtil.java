package com.gimeast.club.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Log4j2
public class JWTUtil {

    private String secretKey = "dkanrmfwksk1231247089157akwrwjrdjehehlaksjkalfdjlk1248909829104890asf214328714289u";

    private long expire = 60 * 24 * 30; //만료기간:30일
    private final Key key;

    public JWTUtil() {
        System.out.println("=======JWTUtil=======");
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String content) throws Exception {

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
//                .setExpiration(Date.from(ZonedDateTime.now().plusSeconds(1).toInstant())) 토큰만료 예제
                .claim("sub", content)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateAndExtract(String tokenStr) throws Exception {
        String contentValue = null;

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(tokenStr);

            log.info(claimsJws);
            log.info(claimsJws.getBody().getClass());

            Claims claims = claimsJws.getBody();

            log.info("---------------------");

            contentValue = claims.getSubject();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            contentValue = null;
        }
        return contentValue;
    }

}
