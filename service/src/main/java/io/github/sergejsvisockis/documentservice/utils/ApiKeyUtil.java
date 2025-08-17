package io.github.sergejsvisockis.documentservice.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Arrays;
import java.util.Base64;

public final class ApiKeyUtil {

    private ApiKeyUtil() {
    }

    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    public static String hashApiKey(String apiKey, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(apiKey.toCharArray(), salt, 100_000, 256); // iterations, key length
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to hash an API key=" + apiKey
                    + "and salt=" + Arrays.toString(salt), e);
        }
    }

    public static byte[] decodeSalt(String salt) {
        return Base64.getDecoder().decode(salt);
    }

}
