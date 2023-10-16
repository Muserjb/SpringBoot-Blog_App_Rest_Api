package com.springboot.blog.security;

import com.springboot.blog.exception.BlogAPIException;
import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecrete;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    // generate token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecrete)
                .compact();

        return token;
    }

    //get username from the token
    public String getUsernameFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecrete)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


    // validate jwt token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecrete).parseClaimsJws(token);
            return true;
        }catch (SignatureException s){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "invalid jwt signature");
        }catch (MalformedJwtException m){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "invalid jwt token");
        }catch (ExpiredJwtException e){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "expired jwt token");
        }catch (UnsupportedJwtException un){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "unsupported jwt token");
        }catch (IllegalArgumentException ill){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Jwt claims string is Empty");
        }


    }


}
