package main.java.lucia.net.packet.impl.incoming;

import main.java.lucia.Client;
import main.java.lucia.net.NetworkBuilder;
import main.java.lucia.net.packet.IncomingPacket;
import main.java.lucia.net.packet.impl.incoming.handshake.HandshakeDecoder;
import main.java.lucia.net.security.encryption.IncomingDecryptionManager;

/**
 * A decoder which decodes and handles immediate incoming requests, and changes state
 * depending on what state the client is currently on.
 *
 * @author Brett Downey
 */
public class MasterDecoder {

    /**
     * The network for this {@link MasterDecoder}
     */
    private NetworkBuilder network;

    /**
     * The current decoder.
     */
    private DecoderInterface current;

    /**
     * The class that will decrypt incoming
     * messages.
     */
    private IncomingDecryptionManager decrypt;

    /**
     * The constructor for the decoder which builds the packet sender
     * and sends the handshake message
     *
     * @param network The network
     */
    public MasterDecoder(NetworkBuilder network) {
        this.network = network;
        this.network.buildPacketSender();
        decrypt = new IncomingDecryptionManager();
        current = new HandshakeDecoder(this).handshake();
    }

    public IncomingDecryptionManager getDecrypt() {
        return decrypt;
    }

    public NetworkBuilder getNetwork() {
        return network;
    }

    /**
     * Proceeds to the next impl
     */
    public void next() {
        current = current.next();
    }

    /**
     * Decodes the incoming {@link String}
     *
     * @param encryptedMessage The encrypted incoming {@link String}
     */
    public void decode(String encryptedMessage) {
        try {
            IncomingPacket incoming = current.process(decrypt.getDecryption().decrypt(encryptedMessage));
            if (incoming != null) {
                network.checkId(incoming.getEchoCode());
            }
        } catch (Exception e) {
            Client.logger.error("An error has occurred during decryption!", e);
        }
    }
}