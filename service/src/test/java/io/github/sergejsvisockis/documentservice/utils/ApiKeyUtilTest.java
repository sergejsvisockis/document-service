package io.github.sergejsvisockis.documentservice.utils;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApiKeyUtilTest {

    private static final String API_KEY = "test-api-key";
    private static final String SALT_STRING = "test-salt";
    private static final byte[] SALT_BYTES = SALT_STRING.getBytes();
    private static final String ENCODED_SALT = Base64.getEncoder().encodeToString(SALT_BYTES);

    @Test
    void shouldHashApiKeyCorrectly() {
        // given
        byte[] salt = SALT_BYTES;
        
        // when
        String hashedApiKey = ApiKeyUtil.hashApiKey(API_KEY, salt);
        
        // then
        assertNotNull(hashedApiKey);
        assertNotEquals(API_KEY, hashedApiKey);
        
        // Verify that the same input produces the same hash
        String hashedApiKey2 = ApiKeyUtil.hashApiKey(API_KEY, salt);
        assertEquals(hashedApiKey, hashedApiKey2);
    }
    
    @Test
    void shouldProduceDifferentHashesForDifferentApiKeys() {
        // given
        byte[] salt = SALT_BYTES;
        String differentApiKey = "different-api-key";
        
        // when
        String hashedApiKey1 = ApiKeyUtil.hashApiKey(API_KEY, salt);
        String hashedApiKey2 = ApiKeyUtil.hashApiKey(differentApiKey, salt);
        
        // then
        assertNotEquals(hashedApiKey1, hashedApiKey2);
    }
    
    @Test
    void shouldProduceDifferentHashesForDifferentSalts() {
        // given
        byte[] salt1 = SALT_BYTES;
        byte[] salt2 = "different-salt".getBytes();
        
        // when
        String hashedApiKey1 = ApiKeyUtil.hashApiKey(API_KEY, salt1);
        String hashedApiKey2 = ApiKeyUtil.hashApiKey(API_KEY, salt2);
        
        // then
        assertNotEquals(hashedApiKey1, hashedApiKey2);
    }
    
    @Test
    void shouldThrowExceptionWhenApiKeyIsNull() {
        // given
        byte[] salt = SALT_BYTES;
        
        // when & then
        assertThrows(IllegalStateException.class, () -> ApiKeyUtil.hashApiKey(null, salt));
    }
    
    @Test
    void shouldThrowExceptionWhenSaltIsNull() {
        // when & then
        assertThrows(IllegalStateException.class, () -> ApiKeyUtil.hashApiKey(API_KEY, null));
    }
    
    @Test
    void shouldDecodeSaltCorrectly() {
        // given
        String encodedSalt = ENCODED_SALT;
        
        // when
        byte[] decodedSalt = ApiKeyUtil.decodeSalt(encodedSalt);
        
        // then
        assertNotNull(decodedSalt);
        assertArrayEquals(SALT_BYTES, decodedSalt);
    }
    
    @Test
    void shouldThrowExceptionWhenDecodingSaltWithNullInput() {
        // when & then
        assertThrows(NullPointerException.class, () -> ApiKeyUtil.decodeSalt(null));
    }
    
    @Test
    void shouldHandleEmptyInputWhenDecodingSalt() {
        // when
        byte[] result = ApiKeyUtil.decodeSalt("");
        
        // then
        assertNotNull(result);
        assertEquals(0, result.length);
    }
    
    @Test
    void shouldThrowExceptionWhenDecodingSaltWithInvalidBase64Characters() {
        // when & then
        assertThrows(IllegalArgumentException.class, () -> ApiKeyUtil.decodeSalt("invalid-base64!"));
    }
    
    @Test
    void shouldThrowExceptionWhenDecodingSaltWithNonBase64EncodedString() {
        // when & then
        assertThrows(IllegalArgumentException.class, () -> ApiKeyUtil.decodeSalt("not-base64-encoded"));
    }
}