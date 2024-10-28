package sim.ecommerce.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
@Service
public class JwtService {
    private static final String SECRET = System.getenv("JWT_SECRET");
    private final static byte [] secret = SECRET.getBytes();

    private static final Key key = Keys.hmacShaKeyFor(secret);
    private static final long expiration = 840000;


    public String generateToken(String username) {
        System.out.println("secret key is " + SECRET);
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256,key)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }

    public Boolean isValid(String token) {
        try {
            var tokenValidating = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            System.out.println("it is the following: "+tokenValidating);
            return true;
        } catch (SignatureException | ExpiredJwtException  | MalformedJwtException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
