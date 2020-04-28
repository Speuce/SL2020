package main.java.lucia.net.security.passwords;

import main.java.lucia.Client;
import main.java.lucia.client.AsynchronousTaskService;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.function.Consumer;

/**
 * Implementation of our password hashing
 * using PBKDF2 and SHA512 algorithms to hash
 * our salted passwords before sending them
 * to the server for verification/storage.
 *
 * @author Brett Downey
 * @author Matt Kwiatkowski
 */
public abstract class CryptographicHash {

    /**
     * The key length our {@link javax.crypto.spec.PBEKeySpec}
     */
    private static final int KEY_LENGTH = 512;

    /**
     * The amount of iterations the given password will be hashed.
     * This greatly reduces the effectiveness of rainbow tables and
     * brute force attacks against our hashes passwords.
     */
    private static final int ITERATIONS = 100000;

    /**
     * The algorithm our {@link javax.crypto.SecretKeyFactory} will be
     * created for.
     */
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";

    /**
     * The salt that will be used in order to help prevent rainbow tables.
     * The server will also generate a random salt and rehash our password in
     * order to exponentially increase the security of our hashed password.
     */
    private static final byte[] CLIENT_SALT = "fg${EAt[>gC@j9>".getBytes();

    /**
     * Hashes the given password and returns the hashed password with the given plain text
     * password from the user.
     */
    public static String hashPassword(final String password) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), CLIENT_SALT, ITERATIONS, KEY_LENGTH);
            SecretKey key = keyFactory.generateSecret(keySpec);

            return Base64.getUrlEncoder().withoutPadding().encodeToString(key.getEncoded());

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            Client.logger.error("An error occurred while hashing a password!", e);
        }
        return null;
    }

    /**
     * Hashes the given password asynchronously
     * @param password the plaintext password to be hashed
     * @param callback a String-type {@link Consumer} that is supplied with the hashed version of the given plaintext password
     */
    public static void hashPassword(final String password, Consumer<String> callback){
        AsynchronousTaskService.process(() -> callback.accept(hashPassword(password)));
    }
}