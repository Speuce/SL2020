package main.java.lucia.net.packet.impl.incoming;

import main.java.lucia.Client;
import main.java.lucia.client.content.files.MLogger;
import main.java.lucia.net.NetworkBuilder;
import main.java.lucia.net.packet.IncomingPacket;
import main.java.lucia.net.packet.impl.incoming.authenticated.AuthenticatedDecoder;
import main.java.lucia.net.packet.impl.incoming.handshake.HandshakeDecoder;
import main.java.lucia.net.packet.impl.incoming.login.LoginDecoder;
import main.java.lucia.net.security.encryption.IncomingDecryptionManager;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A decoder which decodes and handles immediate incoming requests, and changes state
 * depending on what state the communication is currently on.
 *
 *
 *
 * @author Brett Downey
 * @author Matthew Kwiatkowski
 */
public class MasterDecoder {

    /**
     * The network for this {@link MasterDecoder}
     */
    private NetworkBuilder network;

    /**
     * The decoders to use as next is called, in order
     */
    private Queue<Decoder> decoder;

    /**
     * the current {@link Decoder}
     */
    private Decoder current;

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
        //current = new HandshakeDecoder(this).handshake();
        resetDecoders();
    }

    public void resetDecoders(){
        decoder = new LinkedList<>();
        decoder.add(new HandshakeDecoder(this).handshake());
        decoder.add(new LoginDecoder(this));
        decoder.add(new AuthenticatedDecoder());
        next();
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
        if(!decoder.isEmpty()){
            current = decoder.poll();
        }else{
            MLogger.logError("Tried to get the next decoder when there is none!");
        }
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