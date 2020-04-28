package main.java.lucia.net.protocol.opcode;


import main.java.lucia.client.protocol.message.Message;
import main.java.lucia.client.protocol.message.MessageHandler;
import main.java.lucia.net.packet.PacketProcessor;

import java.util.HashMap;

public class OpcodeMap {

    /**
     * The HashMap list of message handlers.
     */
    private final HashMap<Integer, MessageHandler> messageHandlers = new HashMap<>();

    /**
     * Gets the {@link Message} for the specified opcode.
     *
     * @param opcode The opcode.
     * @return The message handler.
     */
    public final Message getMessage(int opcode, PacketProcessor packetProcessor) {
        return messageHandlers.get(opcode).decodeInbound(packetProcessor);
    }

    /**
     * Registers a {@link MessageHandler} for the specified message type.
     *
     * @param opcode The opcode for the MessageHandler.
     * @param handler The message handler.
     */
    protected final void register(int opcode, MessageHandler handler) {
        messageHandlers.put(opcode, handler);
    }

    /**
     * Checks if the given opcode exists within our opcode map.
     *
     * @param opcode The opcode to retrieve
     * @return Boolean depending on if the opcode exists within the map.
     */
    public final boolean hasCode(int opcode) {
        return messageHandlers.containsKey(opcode);
    }
}