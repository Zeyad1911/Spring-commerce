package sim.ecommerce.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
@Service
public class JwtService {
    private static final String SECRET = System.getenv("JWT_SECRET");
    private final static byte [] secret = SECRET.getBytes();

    private static final Key key = Keys.hmacShaKeyFor(secret);
    private static final long expiration = 24 * 60 * 60 * 1000;


    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256,SECRET.getBytes())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }

        public Boolean isValid(String token) {
            try {
                Jwts.parserBuilder()
                        .setSigningKey(SECRET.getBytes())
                        .build()
                        .parseClaimsJws(token);
                return true;
            } catch (ExpiredJwtException e) {
                System.out.println("Token has expired");
            } catch (SignatureException e) {
                System.out.println("Signature validation failed");
            } catch (MalformedJwtException e) {
                System.out.println("Token is malformed");
            }
            return false;
        }
}
