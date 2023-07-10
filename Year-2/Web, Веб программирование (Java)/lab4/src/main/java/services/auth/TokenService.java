package services.auth;

import io.jsonwebtoken.*;

import jakarta.ejb.Stateless;
import utils.KeywordKeyGenerator;

import java.security.Key;
import java.util.Optional;

@Stateless
public class TokenService {
    private final Key key = new KeywordKeyGenerator("whatever").generate();

    /**
     * @param username username to generate token for
     * @return generated token
     */
    public String generate(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    /**
     * @param token token to verify and extract username
     * @return an optional with the username if verified successfully
     */
    public Optional<String> verify(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
            return Optional.of(claimsJws.getBody().getSubject());
        } catch (JwtException e) {
            // validation failed
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
