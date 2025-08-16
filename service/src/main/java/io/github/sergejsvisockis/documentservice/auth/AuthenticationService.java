package io.github.sergejsvisockis.documentservice.auth;

import io.github.sergejsvisockis.documentservice.repository.ApiKey;
import io.github.sergejsvisockis.documentservice.repository.ApiKeyRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    static final String AUTH_TOKEN_HEADER_NAME = "x-api-key";
    static final String INCORRECT_API_KEY_MESSAGE = "Incorrect API key passed";
    static final String NO_API_KEY_FOUND_MESSAGE = "NO associated API key found";
    static final String API_KEY_EXPIRED_MESSAGE = "An API key has expired";

    private final ApiKeyRepository apiKeyRepository;

    public Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (apiKey == null) {
            throw new BadCredentialsException(INCORRECT_API_KEY_MESSAGE);
        }

        Optional<ApiKey> apiAuthKey = findApiAuthKey(apiKey);
        if (apiAuthKey.isEmpty()) {
            throw new BadCredentialsException(NO_API_KEY_FOUND_MESSAGE);
        }

        if (!isValid(apiAuthKey.get())) {
            throw new BadCredentialsException(API_KEY_EXPIRED_MESSAGE);
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }

    private boolean isValid(ApiKey apiKey) {
        LocalDateTime validTo = LocalDateTime.parse(apiKey.getExpirationDate());
        return LocalDateTime.now().isBefore(validTo);
    }

    private Optional<ApiKey> findApiAuthKey(String key) {
        return apiKeyRepository.findApiKey(encodeApiKey(key));

    }

    private String encodeApiKey(String key) {
        return Base64.getEncoder().encodeToString(key.getBytes());
    }

}
