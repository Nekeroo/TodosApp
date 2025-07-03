package com.ynov.todosapp.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class JwtTokenProviderTest {
    private JwtTokenProvider jwtTokenProvider;
    private final String secret = "NzYzMjM2NTMxNzYxNDQ1MjA2NTgxNzI1Njk0MTUzNjU1NjU5Njk2ODcyNjMzNzU1MTYxNjE3OTUzNTM3NzU3";

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider(secret);
    }

    @Test
    @DisplayName("generateToken doit générer un JWT valide et récupérable")
    void testGenerateAndParseToken() {
        String username = "user";
        List<String> roles = List.of("USER");
        String token = jwtTokenProvider.generateToken(username, roles);
        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));
        assertEquals(username, jwtTokenProvider.getUsernameFromJWT(token));
    }

    @Test
    @DisplayName("validateToken doit retourner false pour un token invalide")
    void testValidateTokenInvalid() {
        String invalidToken = "invalid.token.value";
        assertFalse(jwtTokenProvider.validateToken(invalidToken));
    }

    @Test
    @DisplayName("getJwtFromRequest doit extraire le token Bearer")
    void testGetJwtFromRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer mytoken");
        String token = jwtTokenProvider.getJwtFromRequest(request);
        assertEquals("mytoken", token);
    }

    @Test
    @DisplayName("getJwtFromRequest retourne null si pas de header")
    void testGetJwtFromRequestNoHeader() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(null);
        String token = jwtTokenProvider.getJwtFromRequest(request);
        assertNull(token);
    }

    @Test
    @DisplayName("validateToken returns false for token with invalid signature (SecurityException)")
    void testValidateTokenInvalidSignature() {
        JwtTokenProvider otherProvider = new JwtTokenProvider("NzYzMjM2NTMxNzYxNDQ1MjA2NTgxNzI1Njk0MTUzNjU1NjU5Njk2ODcyNjMzNzU1MTYxNjE3OTUzNTM3NzU3oeoeoeooeoe");
        String token = otherProvider.generateToken("user", List.of("USER"));
        assertFalse(jwtTokenProvider.validateToken(token));
    }

    @Test
    @DisplayName("validateToken returns false for malformed token (MalformedJwtException)")
    void testValidateTokenMalformed() {
        // Not a JWT format
        String malformedToken = "thisisnotajwt";
        assertFalse(jwtTokenProvider.validateToken(malformedToken));
    }

    @Test
    @DisplayName("validateToken returns false for expired token (ExpiredJwtException)")
    void testValidateTokenExpired() throws InterruptedException {
        // Generate a token with a very short expiration
        JwtTokenProvider shortLivedProvider = new JwtTokenProvider(secret) {
            @Override
            public String generateToken(String username, List<String> roles) {
                long now = System.currentTimeMillis();
                return io.jsonwebtoken.Jwts.builder()
                        .setSubject(username)
                        .claim("roles", roles)
                        .setIssuedAt(new java.util.Date(now))
                        .setExpiration(new java.util.Date(now + 500)) // 0.5 second
                        .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(secret.getBytes()))
                        .compact();
            }
        };
        String token = shortLivedProvider.generateToken("user", List.of("USER"));
        Thread.sleep(600); // Wait for expiration
        assertFalse(jwtTokenProvider.validateToken(token));
    }

    @Test
    @DisplayName("validateToken returns false for unsupported token (UnsupportedJwtException)")
    void testValidateTokenUnsupported() {
        // Create a token with an unsupported format (e.g., plain string with dots)
        String unsupportedToken = "header.payload.signature.extra"; // Too many segments
        assertFalse(jwtTokenProvider.validateToken(unsupportedToken));
    }

    @Test
    @DisplayName("validateToken returns false for empty claims (IllegalArgumentException)")
    void testValidateTokenEmptyClaims() {
        assertFalse(jwtTokenProvider.validateToken(""));
        assertFalse(jwtTokenProvider.validateToken(null));
    }
}
