package ch.hearc.ig.odi.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.ws.rs.core.UriInfo;

public class Token {

  public static String issueToken(String email, Key key, UriInfo uriInfo) {
   // Key key = keyGenerator.generateKey();
    return Jwts.builder()
        .setSubject(email)
        .setIssuer(uriInfo.getAbsolutePath().toString())
        .setIssuedAt(new Date())
        .setExpiration(toDate(LocalDateTime.now().plusDays(1L)))
        .signWith(SignatureAlgorithm.HS512, key)
        .compact();
  }

  private static Date toDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

}
