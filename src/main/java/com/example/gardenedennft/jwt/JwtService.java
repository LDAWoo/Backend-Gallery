package com.example.gardenedennft.jwt;

import com.example.gardenedennft.artist.entity.Artist;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String extractArtist(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    Claims extractAllClaims(String token);
    Key getSignInKey();

    String generateToken(Artist artist);

    String generateToken(Map<String,Object> extractClaims, Artist artist);

    Boolean isValidToken(String token, UserDetails details);

    Boolean isTokenExpired(String token);

    Date extractExpired(String token);


}
