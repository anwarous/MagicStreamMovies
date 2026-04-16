package com.covoiturage.security;
import io.jsonwebtoken.*;import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;import java.nio.charset.StandardCharsets;import java.util.*;
@Component
public class JwtUtil {
  @Value("${app.jwt.secret}") private String secret;
  @Value("${app.jwt.expiration-ms}") private long expirationMs;
  private SecretKey key(){return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));}
  public String generateToken(String subject){Date now=new Date();return Jwts.builder().subject(subject).issuedAt(now).expiration(new Date(now.getTime()+expirationMs)).signWith(key()).compact();}
  public String extractSubject(String token){return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().getSubject();}
  public boolean isValid(String token){try{extractSubject(token);return true;}catch(Exception e){return false;}}
}
