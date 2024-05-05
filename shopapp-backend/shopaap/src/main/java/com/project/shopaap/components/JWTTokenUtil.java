package com.project.shopaap.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JWTTokenUtil {
    @Value("${jwt.expiration}")
    private long expiration;// lưu vào biên môi trường
    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(com.project.shopaap.models.User user)throws Exception
    {
        //properties => claims
        Map<String,Object> claims = new HashMap<>();
        //this.generateSecrectKey();
        claims.put("phoneNumber",user.getPhoneNumber());
        try {
            String token = Jwts.builder()
                    .setClaims(claims) // xem bên trong chứa những thứ gì?
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        }
        catch (Exception e ){
            //can use ""inject" Logger
            throw new InvalidParameterException("Cannot create jwt token, error: "+e.getMessage());

        }
    }
    private Key getSignInKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);// Keys.hmacShaKeyFor(Decoders.BASE64.decode("eaeF046zKIoyUmlCVJukLQQyhRfFanpwsKVZ0KfCUWs="))
    }
    private String generateSecrectKey(){
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];
        random.nextBytes(keyBytes);
        String secrect = Encoders.BASE64.encode(keyBytes);
        return secrect;
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims = this.extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    //check expiration
    public boolean isTokenExpired(String token){
        Date expirationDate = this.extractClaim(token,Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    public String extractPhoneNumber(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public boolean validateToken(String token, UserDetails userDetails){
        String phoneNumber = extractPhoneNumber(token);
        return (phoneNumber.equals(userDetails.getUsername()))
                && !isTokenExpired(token);
    }
}
