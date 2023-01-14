package com.hua.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
/**
 * In that class we could generate a JWT key for the reset password mechanism.
 * The system used the JWT to unidentified the user which request the reset of password.
 * @author      John Nikolaou
 */
@Service
public class JwtUtil {

	@Value("${security.jwt.secret.key}")
    private String SECRET_KEY;

    /**
     * With that function we could get the username of the token claims
     *
     * @param token the token of the user
     * @return the username of the user
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    /**
     * With that function we could get the expiration date of the token claims
     *
     * @param token the token of the user
     * @return the expiration date of the token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * That function used to filter out a specific claim
     *
     * @param token the token of the user
     * @param claimsResolver the item type we want to extract
     * @return the item we extract
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     *  That function used to extract all claims
     *
     * @param token the token of the user
     * @return all the claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Make a check if the JWT token hadn't expired and the user could proceed
     *
     * @param token the token of the user
     * @return true if token hadn't expire
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * that function generate a new JWT token
     *
     * @param username of the user
     * @return the new JWT token
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * that function build a new JWT token
     * @param claims the claims of the token
     * @param subject the username of the user
     * @return the new token
     */
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    /**
     * that function used to validate the JWT tokens
     *
     * @param token  the token of the user
     * @return return true if the token had expire
     */
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
    
}