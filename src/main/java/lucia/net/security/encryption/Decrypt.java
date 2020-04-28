package main.java.lucia.net.security.encryption;

import main.java.lucia.Client;
import main.java.lucia.consts.ErrorCodeConstants;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

/**
 * A class which handles our RSA and AES
 * decryption from the client side.
 *
 * @author Brett Downey
 */
public class Decrypt {

    /**
     * The RSA cipher to handle decryption.
     */
    private Cipher rsaCipher;

    /**
     * The AES cipher to handle decryption.
     */
    private Cipher aesCipher;

    /**
     * The decryption constructor.
     */
    public Decrypt(PrivateKey key) {
        try {
            rsaCipher = Cipher.getInstance("RSA");
            rsaCipher.init(Cipher.DECRYPT_MODE, key);
            aesCipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            Client.logger.error("A fatal error occurred while initializing the decryption algorithm!", e);
            System.exit(ErrorCodeConstants.DECRYPTION_ERROR.getCode());
        }
    }

    /**
     * Decrypts the given string.
     */
    public String decrypt(String message) throws Exception {
        String[] parts = message.split(" ");
        byte[] byteCipherText = Base64.getDecoder().decode(parts[0]);
        byte[] aesKey = Base64.getDecoder().decode(parts[1]);

        aesKey = rsaCipher.doFinal(aesKey);
        SecretKey aesSecretKey = new SecretKeySpec(aesKey, 0, aesKey.length, EncryptionConstants.AES);
        aesCipher.init(Cipher.DECRYPT_MODE, aesSecretKey);

        return new String(aesCipher.doFinal(byteCipherText));
    }
}