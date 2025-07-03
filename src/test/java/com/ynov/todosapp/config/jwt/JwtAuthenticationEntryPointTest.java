package com.ynov.todosapp.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("test")
class JwtAuthenticationEntryPointTest {

    @Test
    @DisplayName("commence() doit envoyer une erreur 401 Unauthorized")
    void testCommenceSendsUnauthorized() throws IOException {
        JwtAuthenticationEntryPoint entryPoint = new JwtAuthenticationEntryPoint();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException authException = mock(AuthenticationException.class);

        entryPoint.commence(request, response, authException);

        verify(response, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
