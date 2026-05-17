package com.auditoria.microservicio_auditoria.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String llaveSecreta;

    private final Long JWT_EXPIRATION = 84600000L;

    public String generarToken(UserDetails usuario){
        System.out.println(llaveSecreta);
        return Jwts.builder()
                .subject(usuario.getUsername())
                .claim("roles",
                        usuario.getAuthorities()
                )
                .issuedAt(new Date(
                        System.currentTimeMillis()+JWT_EXPIRATION)
                )
                .signWith(getSigningKey())
                .compact();
    }

    public String extrarUsername(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(
                llaveSecreta.getBytes()
        );
    }
}
