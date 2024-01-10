package com.green.greengram4.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram4.common.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
// @Component는 bean 등록
@RequiredArgsConstructor
public class
JwtTokenProvider {

    private final ObjectMapper om; // jackson 라이브러리에 있음

    private final AppProperties appProperties;
    /*  private final String headerSchemeName;
        private final String tokenType;
        private final String secret; */
    private Key key;

     /* public JwtTokenProvider(@Value("${springboot.jwt.secret}") String secret,
                            @Value("${springboot.jwt.header-scheme-name}") String headerSchemeName,
                            @Value("${springboot.jwt.token-type}") String tokenType
    ) {
        this.secret = secret;
        this.headerSchemeName = headerSchemeName;
        this.tokenType = tokenType;
        log.info("secret: {}", secret);
    } */

    @PostConstruct
    // bean등록이 되어야 하고, init 호출전에 di부터 전부 이뤄짐, 그리고 호출
    // 메소드 호출전에 미리 짜놓는 annotation
    public void init() {
        log.info("secret: {}", appProperties.getJwt().getSecret());
        byte[] keyBytes = Decoders.BASE64.decode(appProperties.getJwt().getSecret());
        log.info("keyBytes: {}", keyBytes);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(MyPrincipal myPrincipal) {
        return generateToken(myPrincipal, appProperties.getJwt().getAccessTokenExpiry());
    }

    public String generateRefreshToken(MyPrincipal principal) {
        return generateToken(principal, appProperties.getJwt().getRefreshTokenExpiry());
    }
    private String generateToken(MyPrincipal principal, long tokenValidMs) {
        Date now = new Date();
        return Jwts.builder()
                .claims(createClaims(principal))
                // 이건 한국판 .issuedAt(now)
                .issuedAt(new Date(System.currentTimeMillis()))
                // 이건 미국판
                .expiration(new Date(System.currentTimeMillis() + tokenValidMs))
                .signWith(this.key) // 이것으로 암호화
                .compact();
    }

    private Claims createClaims(MyPrincipal principal) {
        try {
            String json = om.writeValueAsString(principal);

            return Jwts.claims()
                    .add("user", json)
                    .build();
        } catch(Exception e) {
            return null;
        }
    }


    public String resolveToken(HttpServletRequest req) {
        String auth = req.getHeader(appProperties.getJwt().getHeaderSchemeName());
        // ip 주소가 뭐쓰는지, body에 뭐담겨져있는지 등등 header에 담겨있는건 전부 들어있음
        // header에 들어있는 데이터 중에서 headerSchemeName 값을 달라는 뜻

        if (auth == null) {
            return null;
        }

        if (auth.startsWith(appProperties.getJwt().getTokenType())) { // boolean
            return auth.substring(appProperties.getJwt().getTokenType().length()).trim();
        }
        return null;
        // 리턴타입이 없으면 startsWith를 쓸수 없음
        // substring은 문자열 자르기
        // 양쪽에 있는 공백만 제거됨
    }

    public boolean isValidateToken(String token) {
        try {
            // 만료기간이 현재시간보다 전이라면 false, 만료기간이 현재시간보다 후면 true
            // 만료기간이 현재시간보다 후라면 true, 현재시간이 만료기간보다 전이면 false
            return !getAllClaims(token).getExpiration().before(new Date());
        } catch(Exception e) {
            return false;
        }
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = getUserDetailsFromToken(token);
        return userDetails == null
               ? null
               : new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private UserDetails getUserDetailsFromToken(String token) {
        try {
            Claims claims = getAllClaims(token);
            String json = (String) claims.get("user");
            MyPrincipal myPrincipal = om.readValue(json, MyPrincipal.class);
            return MyUserDetails.builder()
                    .myPrincipal(myPrincipal)
                    .build();
        } catch(Exception e) {
            return null;
        }
    }


}
