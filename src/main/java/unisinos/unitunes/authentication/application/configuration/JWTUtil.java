package unisinos.unitunes.authentication.application.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class JWTUtil {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  public String generateToken(String email) {
    return Jwts.builder()
      .setSubject(email)
      .setExpiration(new Date(System.currentTimeMillis() + expiration))
      .signWith(SignatureAlgorithm.HS512, secret.getBytes())
      .compact();
  }

  public boolean validToken(String token) {
    Claims claims = getClaims(token);
    if (Objects.nonNull(claims)) {
      String username = claims.getSubject();
      Date expirationDate = claims.getExpiration();
      Date now = new Date(System.currentTimeMillis());
      if (Objects.nonNull(username) && Objects.nonNull(expirationDate) && now.before(expirationDate)) {
        return true;
      }
    }
    return false;
  }

  public String getUsername(String token) {
    Claims claims = getClaims(token);
    if (Objects.nonNull(claims)) {
      return claims.getSubject();
    }
    return null;
  }

  private Claims getClaims(String token) {
    try {
      return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
    }
    catch (Exception e) {
      return null;
    }
  }
}
