package io.github.sergejsvisockis.documentservice.auth;

import io.github.sergejsvisockis.documentservice.repository.ApiKey;
import io.github.sergejsvisockis.documentservice.repository.ApiKeyRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private static final String AUTH_TOKEN_HEADER_NAME = "x-api-key";
    private static final String VALID_API_KEY = "valid-api-key";
    private static final String ENCODED_API_KEY = Base64.getEncoder().encodeToString(VALID_API_KEY.getBytes());

    private static final String INCORRECT_API_KEY_MESSAGE = "Incorrect API key passed";
    private static final String NO_API_KEY_FOUND_MESSAGE = "NO associated API key found";
    private static final String API_KEY_EXPIRED_MESSAGE = "An API key has expired";

    @Mock
    private ApiKeyRepository apiKeyRepository;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void shouldReturnAuthenticationWhenApiKeyIsValid() {
        // given
        ApiKey apiKey = new ApiKey();
        apiKey.setApiKey(ENCODED_API_KEY);
        apiKey.setExpirationDate(LocalDateTime.now().plusDays(1).toString());

        when(request.getHeader(AUTH_TOKEN_HEADER_NAME)).thenReturn(VALID_API_KEY);
        when(apiKeyRepository.findApiKey(ENCODED_API_KEY)).thenReturn(Optional.of(apiKey));

        // when
        Authentication authentication = authenticationService.getAuthentication(request);

        // then
        assertEquals(VALID_API_KEY, authentication.getPrincipal());
    }

    @Test
    void shouldThrowExceptionWhenApiKeyIsNotFound() {
        // given
        when(request.getHeader(AUTH_TOKEN_HEADER_NAME)).thenReturn(VALID_API_KEY);
        when(apiKeyRepository.findApiKey(anyString())).thenReturn(Optional.empty());

        // when & then
        BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                () -> authenticationService.getAuthentication(request));
        assertEquals(NO_API_KEY_FOUND_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenApiKeyIsNull() {
        // given
        when(request.getHeader(AUTH_TOKEN_HEADER_NAME)).thenReturn(null);

        // when & then
        BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                () -> authenticationService.getAuthentication(request));
        assertEquals(INCORRECT_API_KEY_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenApiKeyHasExpired() {
        // given
        ApiKey apiKey = new ApiKey();
        apiKey.setApiKey(ENCODED_API_KEY);
        apiKey.setExpirationDate(LocalDateTime.now().minusDays(1).toString());

        when(request.getHeader(AUTH_TOKEN_HEADER_NAME)).thenReturn(VALID_API_KEY);
        when(apiKeyRepository.findApiKey(ENCODED_API_KEY)).thenReturn(Optional.of(apiKey));

        // when & then
        BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                () -> authenticationService.getAuthentication(request));
        assertEquals(API_KEY_EXPIRED_MESSAGE, exception.getMessage());
    }
}