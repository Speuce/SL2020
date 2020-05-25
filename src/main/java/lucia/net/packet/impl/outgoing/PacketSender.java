package main.java.lucia.net.packet.impl.outgoing;

import io.netty.channel.Channel;
import main.java.lucia.Client;
import main.java.lucia.client.content.files.MLogger;
import main.java.lucia.client.protocol.packet.OutgoingAuthPacket;
import main.java.lucia.net.NetworkConstants;
import main.java.lucia.net.packet.OutgoingPacket;
import main.java.lucia.net.packet.event.PacketListenerManager;
import main.java.lucia.net.security.encryption.Encrypt;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class PacketSender {

    /**
     * The AtomicInteger associated with
     * this PacketSender.
     */
    private AtomicInteger counter;

    /**
     * The channel client associated with this
     * message sender service.
     */
    private Channel client;

    /**
     * The encryption service associated with
     * this PacketSender.
     */
    private Encrypt encryptService;

    /**
     * The secure token for the current session which
     * is gotten from the server during our handshake.
     */
    private static String secureToken;

    /**
     * The amount of messages that will be saved into a map
     * to be resent.
     */
    private static final int MAX_HISTORY = 10000;

    /**
     * The amount of time that must elapse in milliseconds
     * before a message is considered failed and a reattempt
     * is made.
     */
    private static final int TIME_ELAPSED_MILLIS = 5000;

    /**
     * The history associated with this PacketSender
     * which records all messages sent with their associated
     * StatusCode.
     */
    private static HashMap<Integer, History> history = new HashMap<>();

    /**
     * The current PacketSender that this client is using,
     * which may change depending on if a new PacketSender is created
     * with new network values. This is done since the end-to-end encryption
     * and the secure token will change with a new incoming handshake packet
     */
    private static PacketSender currentPacketSender;

    /**
     * The constructor for this message sender service.
     */
    public PacketSender(Channel client) {
        this.client = client;
        this.counter = new AtomicInteger();
        this.encryptService = new Encrypt();
        currentPacketSender = this;
    }

    public static void setSecureToken(String secureToken) {
        PacketSender.secureToken = secureToken;
    }

    /**
     * Sends the associated messages.
     */
    public void sendMessage(OutgoingPacket packet) {
        if(!PacketListenerManager.get.callEvent(packet)){
            return;
        }
        packet.setSessionToken(secureToken);
        int echoId = counter.get();
        packet.setEchoCode(echoId);
        if (client.isOpen()) {
            sendMessage(echoId, packet);
        } else if(history.size() < MAX_HISTORY) {
            history.putIfAbsent(echoId, new History(packet));
        }else{
            MLogger.logError("History size exceeded max history size.");
        }
    }

    private void sendMessage(int codeId, OutgoingPacket packet) {
        history.replace(codeId, new History(packet));
        /* Encrypt our information and send it out */
        String encrypted = encryptService.encrypt(packet.toString()) + System.lineSeparator();
        client.writeAndFlush(encrypted);
    }


    public void reattemptFailedMessages() {
        if (history.isEmpty()) {
            return;
        }
        int resent = 0;
        for (Map.Entry<Integer, History> entry : history.entrySet()) {
            if (entry.getValue().getTimeElapsed() >= TIME_ELAPSED_MILLIS) {
                resent++;
                sendMessage(entry.getKey(), entry.getValue().getMessage());
                if (resent > NetworkConstants.MAX_RESEND_ATTEMPT) {
                    break;
                }
            }
        }
    }

    public void echoedCodeProcess(int codeId) {
        if (history.containsKey(codeId)) {
            if (history.get(codeId).getTimeElapsed() >= TIME_ELAPSED_MILLIS) {
                Client.logger.info("CodeId which was " + history.get(codeId).getTimeElapsed() + " over the elapsed time received back! " + codeId);
            }
        }
        history.remove(codeId);
    }

    public static PacketSender getCurrentPacketSender() {
        return currentPacketSender;
    }

    public static void setCurrentPacketSender(PacketSender newPacketSender) {
        currentPacketSender = newPacketSender;
    }

    public boolean isActive() {
        return client.isActive() && client.isOpen();
    }

    private class History {

        private long saveTime;

        private OutgoingPacket packet;

        public History(OutgoingPacket packet) {
            this.packet = packet;
            this.saveTime = System.currentTimeMillis();
        }

        public OutgoingPacket getMessage() {
            return packet;
        }

        public long getTimeElapsed() {
            return System.currentTimeMillis() - saveTime;
        }
    }

    /**
     * Sends out a packet to the current packet sender.
     * @param out the packet to send out.
     */
    public static void sendPacket(OutgoingAuthPacket out){
        getCurrentPacketSender().sendMessage(out);
    }
}