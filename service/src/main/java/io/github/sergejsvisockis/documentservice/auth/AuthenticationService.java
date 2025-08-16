package io.github.sergejsvisockis.documentservice.auth;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.sergejsvisockis.documentservice.repository.ApiKey;
import io.github.sergejsvisockis.documentservice.repository.ApiKeyRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Service
public class AuthenticationService {

    static final String AUTH_TOKEN_HEADER_NAME = "x-api-key";
    static final String INCORRECT_API_KEY_MESSAGE = "Incorrect API key passed";
    static final String NO_API_KEY_FOUND_MESSAGE = "No associated API key found";
    static final String API_KEY_EXPIRED_MESSAGE = "An API key has expired";

    private final Cache<String, ApiKey> cache;
    private final ApiKeyRepository apiKeyRepository;

    public AuthenticationService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(2, TimeUnit.HOURS)
                .initialCapacity(10)
                .maximumSize(70)
                .build();
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (apiKey == null) {
            throw new BadCredentialsException(INCORRECT_API_KEY_MESSAGE);
        }

        ApiKey apiAuthKey = findApiAuthKey(apiKey);

        if (!isValid(apiAuthKey)) {
            throw new BadCredentialsException(API_KEY_EXPIRED_MESSAGE);
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }

    private boolean isValid(ApiKey apiKey) {
        LocalDateTime validTo = LocalDateTime.parse(apiKey.getExpirationDate());
        return LocalDateTime.now().isBefore(validTo);
    }

    private ApiKey findApiAuthKey(String key) {
        ApiKey fromCache = cache.getIfPresent(key);

        if (fromCache != null) {
            return fromCache;
        }

        ApiKey apiKey = apiKeyRepository.findApiKey(encodeApiKey(key));

        if (apiKey == null) {
            throw new BadCredentialsException(NO_API_KEY_FOUND_MESSAGE);
        }

        cache.put(key, apiKey);
        return apiKey;
    }

    private String encodeApiKey(String key) {
        return Base64.getEncoder().encodeToString(key.getBytes());
    }

}
