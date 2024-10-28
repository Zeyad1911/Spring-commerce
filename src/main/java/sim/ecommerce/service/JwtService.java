package sim.ecommerce.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
@Service
public class JwtService {
    private static final String SECRET = System.getenv("JWT_SECRET");

    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final long expiration = 840000;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256,key)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder().
                setSigningKey(key)
                .build()
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();

    }
}
