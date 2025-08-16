package io.github.sergejsvisockis.documentservice.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static io.github.sergejsvisockis.documentservice.auth.AuthenticationService.INCORRECT_API_KEY_MESSAGE;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationFilterTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private AuthenticationFilter authenticationFilter;

    @Test
    void shouldAuthenticateAndContinueFilterChain() throws IOException, ServletException {
        // given
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        try (MockedStatic<SecurityContextHolder> securityContextHolderMock = mockStatic(SecurityContextHolder.class)) {
            securityContextHolderMock.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(authenticationService.getAuthentication(request)).thenReturn(authentication);

            // when
            authenticationFilter.doFilter(request, response, filterChain);

            // then
            verify(securityContext).setAuthentication(authentication);
            verify(filterChain).doFilter(request, response);
        }
    }

    @Test
    void shouldReturnUnauthorizedWhenAuthenticationFails() throws IOException {
        // given
        BadCredentialsException exception = new BadCredentialsException(INCORRECT_API_KEY_MESSAGE);
        PrintWriter writer = mock(PrintWriter.class);

        when(authenticationService.getAuthentication(request)).thenThrow(exception);
        when(response.getWriter()).thenReturn(writer);

        // when
        authenticationFilter.doFilter(request, response, filterChain);

        // then
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        verify(writer).print(anyString());
        verify(writer).flush();
        verify(writer).close();
    }
}