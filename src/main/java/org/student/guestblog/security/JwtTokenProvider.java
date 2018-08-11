package org.student.guestblog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.student.guestblog.model.User;

@Log
@Component
public class JwtTokenProvider {

  @Value("${security.jwt.secret}")
  private String jwtSecret;

  @Value("${security.jwt.expiration}")
  private int jwtExpiration;

  public String generateToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("roles", user.getRoles());
    claims.put("enable", user.isEnabled());
    return generateToken(claims, user.getUsername());
  }

  public String generateToken(Map<String, Object> claims, String username) {
    Date createdDate = new Date();
    Date expiryDate = new Date(createdDate.getTime() + jwtExpiration * 1000);

    return Jwts.builder()
      .setClaims(claims)
      .setSubject(username)
      .setIssuedAt(createdDate)
      .setExpiration(expiryDate)
      .signWith(SignatureAlgorithm.HS512, jwtSecret)
      .compact();
  }

  public Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
  }

  public String getUsernameFromToken(String token) {
    Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

    return claims.getSubject();
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException ex) {
      log.severe("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      log.severe("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      log.severe("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      log.severe("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      log.severe("JWT claims string is empty.");
    }
    return false;
  }
}
