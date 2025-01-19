//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "your-256-bit-secret-your-256-bit-secret";
    private static final long EXPIRATION_TIME = 3600000L;
    private final Key key = Keys.hmacShaKeyFor("your-256-bit-secret-your-256-bit-secret".getBytes());

    public JwtUtil() {
    }

    public String generateToken(String email) {
        return Jwts.builder().setSubject(email).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 3600000L)).signWith(this.key, SignatureAlgorithm.HS256).compact();
    }

    public String validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);
            return ((Claims)claims.getBody()).getSubject();
        } catch (IllegalArgumentException | JwtException var3) {
            return null;
        }
    }
}
