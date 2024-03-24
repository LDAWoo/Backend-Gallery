package com.example.gardenedennft.jwt;

import com.example.gardenedennft.artist.entity.Artist;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService{

    private static final String SECRET_KEY = "6KGMeQrk6AGC6xJMBeqWwkY/udp6ALKljw5RTHevYlhutlKMrG4/qnIsaq5G46BJ";

    @Override
    public String extractArtist(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    @Override
    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String generateToken(Artist artist) {
        return generateToken(new HashMap<>(), artist);
    }

    @Override
    public String generateToken(Map<String, Object> extractClaims, Artist artist) {
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(artist.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Boolean isValidToken(String token, UserDetails userDetails) {
        String email = extractArtist(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return extractExpired(token).before(new Date());
    }

    @Override
    public Date extractExpired(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
