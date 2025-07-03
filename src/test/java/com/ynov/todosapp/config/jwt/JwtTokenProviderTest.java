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
}
