package com.tourman.app.security;

import com.tourman.app.domains.entities.RefreshToken;
import com.tourman.app.domains.entities.User;
import com.tourman.app.repositories.RefreshTokenRepository;
import com.tourman.app.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtTokenProvider{
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${app.jwt-secret}")
    private String SECRET_KEY;

    @Value("${app.jwt-expiration-miliseconds}")
    private long EXPIRATION_TIME;

    @Value("${app.jwt-refresh-expiration-miliseconds}")
    private long REFRESH_EXPIRATION_TIME;

    public JwtTokenProvider(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    private Key getSecretKey(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(SECRET_KEY)
        );
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        String roles = userDetails.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.joining(","));
        claims.put("roles", roles);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .setSubject(userDetails.getUsername())
                .signWith(getSecretKey())
                .compact();

    }

    public String getUsernameFromToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private boolean isExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isExpired(token));
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsSolver) {
        final Claims claims = extractAllClaims(token);
        return claimsSolver.apply(claims);
    }

    public String getRoleFromToken(String token) {
        return extractClaims(token, claims -> claims.get("roles", String.class));
    }

    public String generateRefreshToken(UserDetails userDetails) {
        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(getSecretKey())
                .compact();

        User user = userRepository.findUserByEmailOrPhone(userDetails.getUsername(),userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        refreshTokenRepository.deleteByUser(user); // Xóa token cũ nếu có
        RefreshToken refreshToken = new RefreshToken(token, user, new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME));
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
