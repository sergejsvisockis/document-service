package io.github.sergejsvisockis.documentservice.auth;

import io.github.sergejsvisockis.documentservice.repository.ApiKey;
import io.github.sergejsvisockis.documentservice.repository.ApiKeyRepository;
import io.github.sergejsvisockis.documentservice.utils.ApiKeyUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.time.LocalDateTime;
import java.util.Base64;

import static io.github.sergejsvisockis.documentservice.auth.AuthenticationService.API_KEY_EXPIRED_MESSAGE;
import static io.github.sergejsvisockis.documentservice.auth.AuthenticationService.AUTH_TOKEN_HEADER_NAME;
import static io.github.sergejsvisockis.documentservice.auth.AuthenticationService.INCORRECT_API_KEY_MESSAGE;
import static io.github.sergejsvisockis.documentservice.auth.AuthenticationService.NO_API_KEY_FOUND_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private static final String KEY_ID = "test-key-id";
    private static final String API_KEY = "test-api-key";
    private static final String VALID_HEADER_API_KEY = KEY_ID + "." + API_KEY;
    private static final String HASHED_API_KEY = "hashedApiKey123";
    private static final String ENCODED_SALT = Base64.getEncoder().encodeToString("test-salt".getBytes());
    private static final byte[] DECODED_SALT = "test-salt".getBytes();

    @Mock
    private ApiKeyRepository apiKeyRepository;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AuthenticationService authenticationService;

    private ApiKey validApiKey;

    @BeforeEach
    void setUp() {
        validApiKey = new ApiKey();
        validApiKey.setApiKeyId(KEY_ID);
        validApiKey.setApiKey(HASHED_API_KEY);
        validApiKey.setSalt(ENCODED_SALT);
        validApiKey.setExpirationDate(LocalDateTime.now().plusDays(1).toString());
    }

    @Test
    void shouldReturnAuthenticationWhenApiKeyIsValid() {
        // given
        when(request.getHeader(AUTH_TOKEN_HEADER_NAME)).thenReturn(VALID_HEADER_API_KEY);
        when(apiKeyRepository.findApiKey(KEY_ID)).thenReturn(validApiKey);
        
        try (MockedStatic<ApiKeyUtil> apiKeyUtilMock = mockStatic(ApiKeyUtil.class)) {
            apiKeyUtilMock.when(() -> ApiKeyUtil.decodeSalt(ENCODED_SALT)).thenReturn(DECODED_SALT);
            apiKeyUtilMock.when(() -> ApiKeyUtil.hashApiKey(API_KEY, DECODED_SALT)).thenReturn(HASHED_API_KEY);
            
            // when
            Authentication authentication = authenticationService.getAuthentication(request);
            
            // then
            assertNotNull(authentication);
            assertEquals(VALID_HEADER_API_KEY, authentication.getPrincipal());
            apiKeyUtilMock.verify(() -> ApiKeyUtil.decodeSalt(ENCODED_SALT));
            apiKeyUtilMock.verify(() -> ApiKeyUtil.hashApiKey(API_KEY, DECODED_SALT));
        }
    }

    @Test
    void shouldThrowExceptionWhenApiKeyHeaderIsMissing() {
        // given
        when(request.getHeader(AUTH_TOKEN_HEADER_NAME)).thenReturn(null);

        // when & then
        BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                () -> authenticationService.getAuthentication(request));
        assertEquals(INCORRECT_API_KEY_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenApiKeyFormatIsInvalid() {
        // given
        when(request.getHeader(AUTH_TOKEN_HEADER_NAME)).thenReturn("invalid-format");

        // when & then
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> authenticationService.getAuthentication(request));
    }

    @Test
    void shouldThrowExceptionWhenApiKeyIsNotFound() {
        // given
        when(request.getHeader(AUTH_TOKEN_HEADER_NAME)).thenReturn(VALID_HEADER_API_KEY);
        when(apiKeyRepository.findApiKey(KEY_ID)).thenReturn(null);

        // when & then
        BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                () -> authenticationService.getAuthentication(request));
        assertEquals(NO_API_KEY_FOUND_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenApiKeyHashDoesNotMatch() {
        // given
        when(request.getHeader(AUTH_TOKEN_HEADER_NAME)).thenReturn(VALID_HEADER_API_KEY);
        when(apiKeyRepository.findApiKey(KEY_ID)).thenReturn(validApiKey);
        
        try (MockedStatic<ApiKeyUtil> apiKeyUtilMock = mockStatic(ApiKeyUtil.class)) {
            apiKeyUtilMock.when(() -> ApiKeyUtil.decodeSalt(ENCODED_SALT)).thenReturn(DECODED_SALT);
            apiKeyUtilMock.when(() -> ApiKeyUtil.hashApiKey(API_KEY, DECODED_SALT)).thenReturn("different-hash");
            
            // when & then
            BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                    () -> authenticationService.getAuthentication(request));
            assertEquals(INCORRECT_API_KEY_MESSAGE, exception.getMessage());
        }
    }

    @Test
    void shouldUseCacheForRepeatedRequests() {
        // given
        when(request.getHeader(AUTH_TOKEN_HEADER_NAME)).thenReturn(VALID_HEADER_API_KEY);
        when(apiKeyRepository.findApiKey(KEY_ID)).thenReturn(validApiKey);
        
        try (MockedStatic<ApiKeyUtil> apiKeyUtilMock = mockStatic(ApiKeyUtil.class)) {
            apiKeyUtilMock.when(() -> ApiKeyUtil.decodeSalt(ENCODED_SALT)).thenReturn(DECODED_SALT);
            apiKeyUtilMock.when(() -> ApiKeyUtil.hashApiKey(API_KEY, DECODED_SALT)).thenReturn(HASHED_API_KEY);
            
            // when
            authenticationService.getAuthentication(request);
            authenticationService.getAuthentication(request);
            
            // then
            verify(apiKeyRepository, times(1)).findApiKey(KEY_ID);
        }
    }
}