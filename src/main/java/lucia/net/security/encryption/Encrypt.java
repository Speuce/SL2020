package main.java.lucia.net.security.encryption;

import java.util.Objects;
import main.java.lucia.Client;
import main.java.lucia.consts.ErrorCodeConstants;
import main.java.lucia.net.NetworkConstants;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.Base64;
import javax.crypto.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class Encrypt {

    private PublicKey key;

    private Cipher rsaCipher;

    private Cipher aesCipher;

    public Encrypt() {
        try {
            key = loadKey();
            rsaCipher = Cipher.getInstance(EncryptionConstants.RSA);
            rsaCipher.init(Cipher.ENCRYPT_MODE, key);
            aesCipher = Cipher.getInstance(EncryptionConstants.AES);
        } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            Client.logger.error("A fatal error occurred while initialing the encryption algorithm!", e);
            System.exit(ErrorCodeConstants.ENCRYPTION_ERROR.getCode());
        }
    }

    public String encrypt(String message) {
        try {
            // First AES encryption of message
            KeyGenerator generator = KeyGenerator.getInstance(EncryptionConstants.AES);
            generator.init(EncryptionConstants.AES_BITS);
            SecretKey secretKey = generator.generateKey();
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] byteCipherText = aesCipher.doFinal(message.getBytes());

            // Then RSA encryption of the secretKey
            byte[] aesKeyEncrypted = rsaCipher.doFinal(secretKey.getEncoded());

            // Finally build the message to send
            String byteCipherTextString = Base64.getEncoder().encodeToString(byteCipherText);
            String aesKeyEncryptedString = Base64.getEncoder().encodeToString(aesKeyEncrypted);

            return byteCipherTextString +
                    " " +
                    aesKeyEncryptedString;
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchAlgorithmException e) {
            Client.logger.error("An error has occurred during encryption!", e);
        }
        return null;
    }

    private static PublicKey loadKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        // Read Public Key
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = loader.getResourceAsStream(NetworkConstants.PUBLIC_KEY_DIRECTORY);
        byte[] encodedPublicKey = IOUtils.toByteArray(Objects.requireNonNull(inputStream));
        inputStream.close();

        // Generate Public Key
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
        return keyFactory.generatePublic(publicKeySpec);
    }
}