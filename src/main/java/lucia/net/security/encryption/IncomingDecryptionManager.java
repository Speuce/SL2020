package main.java.lucia.net.security.encryption;

import main.java.lucia.Client;
import main.java.lucia.consts.ErrorCodeConstants;

import java.security.*;

public class IncomingDecryptionManager {

    private KeyPair keys;

    private Decrypt decrypt;

    public IncomingDecryptionManager() {
        try {
            this.keys = KeyPairGenerator.getInstance(EncryptionConstants.RSA).generateKeyPair();
            this.decrypt = new Decrypt(keys.getPrivate());
        } catch (NoSuchAlgorithmException e) {
            Client.logger.error(e);
            System.exit(ErrorCodeConstants.DECRYPTION_ERROR.getCode());
        }
    }

    public PublicKey getServerKey() {
        return keys.getPublic();
    }

    public Decrypt getDecryption() {
        return decrypt;
    }
}
