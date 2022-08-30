package com.army.ardiary.service;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
@Service
public class TokenService {

    @Value("${jwt.secretKey}")
    private String secretKey;
    private long accessValidTime = 1000L*60*60; // access 토큰 유효시간: 1시간
    private long refreshValidTime = 1000L*60*60*24; // refresh 토큰 유효시간: 24시간

    //액세스 토큰 생성 후 반환하는 메서드
    public String createToken(String id) {
        //토큰의 키가 되는 subject 를 중복되지않는 고유한 값인 id 로 지정
        Claims claims = Jwts.claims().setSubject(id);
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuedAt(now) //생성일 설정
                .setExpiration(new Date(now.getTime() + accessValidTime)) //만료일 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) //서명 시, 사용되는 알고리즘: HS256
                .compact();

    }

    public String createRefreshToken(){

        Date now = new Date();

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }

    //토큰의 유효성을 확인해주는 메소드
    public boolean validateToken(String token) {
        try { //문제없는 경우
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) { //토큰이 만료된 경우
            System.out.println("Token Expired");
            return false;
        } catch (JwtException e) {//토큰이 변조된 경우
            System.out.println("Token Error");
            return false;
        }

    }

    //토큰에 담긴 정보(claim)을 반환하는 메소드.
    public Claims getJwtContents(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token).getBody();
        return claims;

    }
}
