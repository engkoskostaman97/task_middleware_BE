package com.belajar.jwt.belajar.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.belajar.jwt.belajar.jwt.dto.AuthRequest;
import com.belajar.jwt.belajar.jwt.dto.AuthResponse;
import com.belajar.jwt.belajar.jwt.model.User;
import com.belajar.jwt.belajar.jwt.repository.UserRepository;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secret}") // Inject secret key from application.properties
    private String secretKey;

    private final long expirationTime = 86400000; // 1 day in milliseconds

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Generate a secure key for signing JWT tokens
    private Key getSigningKey() {
        // Ensure the secret key is at least 256 bits (32 bytes)
        if (secretKey.length() < 32) {
            throw new IllegalArgumentException("Secret key must be at least 32 characters long");
        }
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public void register(AuthRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email sudah terdaftar");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }
    

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = generateToken(user);
            return new AuthResponse(user.getId(), token);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Use the secure key here
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Use the secure key for validation
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
