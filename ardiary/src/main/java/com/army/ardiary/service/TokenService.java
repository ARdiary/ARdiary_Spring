package com.army.ardiary.service;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
@Service
public class TokenService {

    @Value("${jwt.secretKey}")
    private String secretKey;
    private long accessValidTime = 1000L*60*60; // access 토큰 유효시간: 1시간
    private long refreshValidTime = 1000L*60*60*24; // refresh 토큰 유효시간: 24시간

    //액세스 토큰 생성 후 반환하는 메서드
    public String createToken(int id) {

        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg","HS256")
                .setSubject("Access Token")
                .claim("id", id)
                .setIssuedAt(now) //생성일 설정
                .setExpiration(new Date(now.getTime() + accessValidTime)) //만료일 설정
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) //서명 시, 사용되는 알고리즘: HS256
                .compact();
    }

    public String createRefreshToken(){

        Date now = new Date();

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();

    }

    //토큰의 유효성을 확인해주는 메소드
    public boolean validateToken(String token){

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(token).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("만료된 토큰입니다.");
            return false;
        } catch (UnsupportedJwtException e){
            System.out.println("지원되지 않는 토큰입니다.");
            return false;
        } catch (MalformedJwtException e){
            System.out.println("토큰 구성이 잘못되었습니다.");
            return false;
        } catch (JwtException e){
            System.out.println("Token Error");
            return false;
        }

    }

    //토큰에 담긴 정보(claim)을 반환하는 메소드.
    public Claims getJwtContents(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token).getBody();

        return claims;
    }

    public int findUserIdByJwt(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .get("id",Integer.class);
    }
}
