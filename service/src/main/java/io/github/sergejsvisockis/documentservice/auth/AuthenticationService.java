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
import java.util.concurrent.TimeUnit;

import static io.github.sergejsvisockis.documentservice.utils.ApiKeyUtil.decodeSalt;
import static io.github.sergejsvisockis.documentservice.utils.ApiKeyUtil.hashApiKey;

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

        String[] splitHeader = apiKey.split("\\.");
        String keyId = splitHeader[0];
        String headerApiKey = splitHeader[1];

        ApiKey apiAuthKey = findApiAuthKey(keyId);
        isValid(apiAuthKey, headerApiKey);

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }

    private void isValid(ApiKey apiKey, String headerApiKey) {
        byte[] decodedSalt = decodeSalt(apiKey.getSalt());

        String hashedApiKey = hashApiKey(headerApiKey, decodedSalt);

        if (!hashedApiKey.equals(apiKey.getApiKey())) {
            throw new BadCredentialsException(INCORRECT_API_KEY_MESSAGE);
        }

        LocalDateTime validTo = LocalDateTime.parse(apiKey.getExpirationDate());
        if (LocalDateTime.now().isEqual(validTo)) {
            throw new BadCredentialsException(API_KEY_EXPIRED_MESSAGE);
        }
    }

    private ApiKey findApiAuthKey(String keyId) {
        ApiKey fromCache = cache.getIfPresent(keyId);

        if (fromCache != null) {
            return fromCache;
        }

        ApiKey apiKey = apiKeyRepository.findApiKey(keyId);

        if (apiKey == null) {
            throw new BadCredentialsException(NO_API_KEY_FOUND_MESSAGE);
        }

        cache.put(keyId, apiKey);
        return apiKey;
    }

}
