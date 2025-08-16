package io.github.sergejsvisockis.documentservice.auth;

import io.github.sergejsvisockis.documentservice.repository.ApiKey;
import io.github.sergejsvisockis.documentservice.repository.ApiKeyRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Base64;

import static io.github.sergejsvisockis.documentservice.auth.AuthenticationService.API_KEY_EXPIRED_MESSAGE;
import static io.github.sergejsvisockis.documentservice.auth.AuthenticationService.INCORRECT_API_KEY_MESSAGE;
import static io.github.sergejsvisockis.documentservice.auth.AuthenticationService.NO_API_KEY_FOUND_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private static final String AUTH_TOKEN_HEADER_NAME = "x-api-key";
    private static final String VALID_API_KEY = "valid-api-key";
    private static final String ENCODED_API_KEY = Base64.getEncoder().encodeToString(VALID_API_KEY.getBytes());

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
        when(apiKeyRepository.findApiKey(ENCODED_API_KEY)).thenReturn(apiKey);

        // when
        Authentication authentication = authenticationService.getAuthentication(request);

        // then
        assertEquals(VALID_API_KEY, authentication.getPrincipal());
    }

    @Test
    void shouldThrowExceptionWhenApiKeyIsNotFound() {
        // given
        when(request.getHeader(AUTH_TOKEN_HEADER_NAME)).thenReturn(VALID_API_KEY);
        when(apiKeyRepository.findApiKey(ENCODED_API_KEY)).thenReturn(null);

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
        ApiKey apiKey = Mockito.mock(ApiKey.class);
        when(apiKey.getExpirationDate()).thenReturn(LocalDateTime.now().minusDays(1).toString());
        when(request.getHeader(AUTH_TOKEN_HEADER_NAME)).thenReturn(VALID_API_KEY);
        when(apiKeyRepository.findApiKey(ENCODED_API_KEY)).thenReturn(apiKey);


        // when & then
        BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                () -> authenticationService.getAuthentication(request));
        assertEquals(API_KEY_EXPIRED_MESSAGE, exception.getMessage());
    }
}