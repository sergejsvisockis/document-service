import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Use these tool to generate an API keys locally.
 */
public class ApiKeyGenerator {

    public static String generateApiKey() {
        byte[] randomBytes = new byte[32]; // 256 bits
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public static byte[] generateSalt() {
        byte[] salt = new byte[16]; // 128-bit salt
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    public static String hashApiKey(String apiKey, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(apiKey.toCharArray(), salt, 100_000, 256); // iterations, key length
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    public static void main(String[] args) throws Exception {
        String apiKey = generateApiKey();
        byte[] salt = generateSalt();
        String hashedKey = hashApiKey(apiKey, salt);

        System.out.println("API Key: " + apiKey);
        System.out.println("Salt (Base64): " + Base64.getEncoder().encodeToString(salt));
        System.out.println("Hashed Key (Base64): " + hashedKey);
    }
}