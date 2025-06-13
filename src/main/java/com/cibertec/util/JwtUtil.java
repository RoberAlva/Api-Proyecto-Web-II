package com.cibertec.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret:mySecretKey123456789012345678901234567890}")
    private String secret;

    @Value("${jwt.expiration:3600000}") // 1 hora por defecto
    private Long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String correo, String tipoUsuario, Long codigoUsuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("tipoUsuario", tipoUsuario);
        claims.put("codigoUsuario", codigoUsuario);
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(correo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getCorreoFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public String getTipoUsuarioFromToken(String token) {
        return (String) getClaimsFromToken(token).get("tipoUsuario");
    }

    public Long getCodigoUsuarioFromToken(String token) {
        return ((Number) getClaimsFromToken(token).get("codigoUsuario")).longValue();
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, String correo) {
        final String tokenCorreo = getCorreoFromToken(token);
        return (tokenCorreo.equals(correo) && !isTokenExpired(token));
    }
}