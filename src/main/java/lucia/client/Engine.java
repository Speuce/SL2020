package main.java.lucia.client;

import java.util.Queue;
import main.java.lucia.Client;
import main.java.lucia.client.protocol.message.Message;
import main.java.lucia.client.task.TaskManager;
import main.java.lucia.net.NetworkBuilder;
import main.java.lucia.net.NetworkConstants;
import main.java.lucia.net.packet.impl.outgoing.PacketSender;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import main.java.lucia.net.packet.impl.outgoing.codec.OutgoingAuthenticatedPacket;

/**
 * Represents the engine, processing it and its clients.
 *
 * @author Brett Downey
 */
public class Engine {

    /**
     * The network associated with this engine
     */
    static NetworkBuilder network;

    /**
     * The queue of echo codes associated with the engine, these
     * codes are sent from the client and then echoed from the
     * server back to the client, this is done to ensure
     * the messages are received by the server, and that the server
     * has handled our requests.
     */
    private static Queue<Integer> echoedCodes = new ConcurrentLinkedDeque<>();

    /**
     * The queue of packets that will be handled on the next sequence.
     */
    private static final Queue<Message> packetsQueue = new ConcurrentLinkedQueue<>();

    /**
     * Processes the engine
     */
    public static void process() {
        TaskManager.process();
        if(network.isConnected()) {
            for (Integer echoedCode : echoedCodes) {
                network.processCodeId(echoedCode);
            }
            network.attemptResend();
            processPackets();
        }
    }

    /**
     * Processes the engine until all remaining packets that
     * need to be sent to the server are sent. There is no guarantee
     * the server will receive or even process these messages.
     */
    static void unload() {
        while(!packetsQueue.isEmpty()) {
            process();
        }
    }

    /**
     * Processes all of the queued messages from the {@link PacketSender} by
     * polling the internal queue, and then handling them via the
     * handleInputMessage. This method is called each server cycle.
     */
    public static void processPackets() {
        for (int i = 0; i < NetworkConstants.PACKET_PROCESS_LIMIT; i++) {
            Message packet = packetsQueue.poll();
            if (packet == null) {
                return;
            }
            processPacket(packet);
        }
    }

    /**
     * Handles an incoming packet.
     *
     * @param packet The packet to handle.
     *
     */
    public static void processPacket(Message packet) {
        try {
            packet.process();

            /* Send all available responses if they exist */
            if(packet.getReplyOpcode() != -1) {
                network.sendMessage(new OutgoingAuthenticatedPacket(packet.getReplyOpcode()));
            }
            if(packet.getReplyPacket() != null) {
                network.sendMessage(packet.getReplyPacket());
            }
            if(packet.getPacketProcessor().getOutgoingPacket() != null) {
                network.sendMessage(packet.getPacketProcessor().getOutgoingPacket());
            }
        } catch (Exception e) {
            Client.logger.error("A packet failed to send!", e);
        }
    }

    /**
     * Queues a recently decoded packet received from the channel.
     *
     * @param packet The packet that should be processed.
     */
    public static void queuePacket(Message packet) {
        if (packetsQueue.size() >= NetworkConstants.PACKET_PROCESS_LIMIT) {
            Client.logger.info("Discarding message as packets size has exceeded the total amount");
        } else packetsQueue.add(packet);
    }

    /**
     * Returns the echo codes stored all server messages since
     * the last {@code Engine.process()}.
     *
     * @return The queue containing the echo codes.
     */
    public static Queue<Integer> getEchoedCodes() {
        return echoedCodes;
    }
}