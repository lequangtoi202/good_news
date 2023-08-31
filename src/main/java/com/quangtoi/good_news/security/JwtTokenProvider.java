package com.quangtoi.good_news.security;

import com.quangtoi.good_news.dto.JwtResponse;
import com.quangtoi.good_news.exception.GoodNewsApiException;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider implements Serializable {
    @Autowired
    private UserRepository userRepository;
    @Value("${app.jwt-secret}")
    private String JwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long JwtExpirationDate;

    private static final long REFRESH_TOKEN_EXPIRATION_DATE = 30 * 24 * 60 * 60 * 1000;

    // generate JWT token
    public JwtResponse generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);
        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + JwtExpirationDate);

        String accessToken = Jwts.builder()
                .setSubject(username)
                .claim("email", user.getEmail())
                .claim("full_name", user.getFullName())
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(username)
                .claim("email", user.getEmail())
                .claim("full_name", user.getFullName())
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + REFRESH_TOKEN_EXPIRATION_DATE))
                .signWith(key())
                .compact();

        JwtResponse jwtResponse = new JwtResponse(accessToken, refreshToken, "Bearer");


        return jwtResponse;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(JwtSecret)
        );
    }

    // get username from JWT token
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        return username;
    }

    // validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new GoodNewsApiException(HttpStatus.BAD_REQUEST, "Expired JWT Token");
        } catch (MalformedJwtException e) {
            throw new GoodNewsApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
        } catch (UnsupportedJwtException e) {
            throw new GoodNewsApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            throw new GoodNewsApiException(HttpStatus.BAD_REQUEST, "JWT Claims string is empty");
        }
    }

    public boolean isTokenExpired(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        Date expirationDate = claims.getExpiration();
        Date currentDate = new Date();

        return expirationDate.before(currentDate);
    }

    public JwtResponse refreshToken(String refreshToken) {
        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + JwtExpirationDate);
        if (validateToken(refreshToken)) {
            try {
                String username = getUsername(refreshToken);
                User user = userRepository.findByUsername(username);
                String accessToken = Jwts.builder()
                        .setSubject(username)
                        .claim("email", user.getEmail())
                        .claim("full_name", user.getFullName())
                        .setIssuedAt(new Date())
                        .setExpiration(expireDate)
                        .signWith(key())
                        .compact();

                JwtResponse jwtResponse = new JwtResponse(accessToken, refreshToken, "Bearer");
                return jwtResponse;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("Refresh token is invalid");
        }
        return null;
    }

}
