package com.army.ardiary.jwt;

import io.jsonwebtoken.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtProvider {
    private String secretKey = "army"; //토큰 체크 시, 필요한 암호키: 외부에 노출되지않게 주의.
    private long tokenValikdTime = 1000L*60*60*24; //토큰 만료기간 24시간으로 설정 (Millisecond 단위)

    //토큰 생성 후 반환하는 메서드
    public String createToken(String email){

        //토큰의 키가 되는 subject를 중복되지않는 고유한 값인 email로 지정
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();

        String jwt = Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setClaims(claims)
                .setIssuedAt(now) //생성일 설정
                .setExpiration(new Date(now.getTime()+tokenValikdTime)) //만료일 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) //서명 시, 사용되는 알고리즘: HS256
                .compact();

        return jwt; //토큰 생성
    }

   //토큰의 유효성을 확인해주는 메소드
   public boolean validateToken(String token){
        try{ //문제없는 경우
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        }catch(ExpiredJwtException e){ //토큰이 만료된 경우
            System.out.println("Token Expried");
            return false;
        }catch(JwtException e){//토큰이 변조된 경우
            System.out.println("Token Error");
            return false;
        }
    }

    //토큰에 담긴 정보(claim)을 반환하는 메소드.
    public Claims getJwtContents(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token).getBody();
        return claims;
    }

}
