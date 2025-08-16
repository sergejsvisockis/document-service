package io.github.sergejsvisockis.documentservice.auth;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApiKeyAuthenticationTest {

    @Test
    void shouldCreateAuthenticationWithApiKey() {
        // given
        String apiKey = "test-api-key";

        // when
        ApiKeyAuthentication authentication = new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);

        // then
        assertEquals(apiKey, authentication.getPrincipal());
        assertNull(authentication.getCredentials());
        assertTrue(authentication.isAuthenticated());
    }
}