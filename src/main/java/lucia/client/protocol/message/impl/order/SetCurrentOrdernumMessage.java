package main.java.lucia.client.protocol.message.impl.order;

import com.google.gson.Gson;
import main.java.lucia.client.manager.impl.OrderManager;
import main.java.lucia.client.protocol.message.Message;
import main.java.lucia.client.protocol.message.impl.MattMessage;
import main.java.lucia.client.protocol.message.impl.W;
import main.java.lucia.net.packet.PacketProcessor;
import main.java.lucia.net.packet.impl.outgoing.codec.OutgoingAuthenticatedPacket;

/**
 * Sent from the server to the client when the client
 * should change its' internal ordernum tracker
 * @author Matt Kwiatkowski
 */
public class SetCurrentOrdernumMessage extends MattMessage {
    /**
     * The constructor for a {@link Message} which provides the needed {@link PacketProcessor}.
     *
     * @param packetProcessor
     */
    public SetCurrentOrdernumMessage(PacketProcessor packetProcessor) {
        super(packetProcessor);
    }

    /**
     * Gets the reply opcode to send to the server, and if the message contains
     * an opcode other than -1, the client will send the opcode to the server.
     * Note that this does not support any information to also be sent, if a specific
     * packet must be sent, then see getReplyPacket, and leave this as {@code -1}.
     * <p>
     * Note that this function as well as {@code getReplyPacket} will be ran
     * when this packet is processed, and there is no need to set the outgoing
     * packet manually. If the outgoing packet has been manually set (via the
     * {@link PacketProcessor}, that packet
     * will also be checked and sent. Therefore at the maximum, 3 packets may
     * be returned from a single message.
     *
     * @return The reply opcode
     */
    @Override
    public int getReplyOpcode() {
        return -1;
    }

    /**
     * Gets the reply packet to send to the server, this method should be
     * filled in if the packet that is required needs to send information instead
     * of just simply an opcode to the server. If no information is to be sent,
     * leave this {@code null} and see getReplyOpcode.
     * <p>
     * Note that this function as well as {@code getReplyOpcode} will be ran
     * when this packet is processed, and there is no need to set the outgoing
     * packet manually. If the outgoing packet has been manually set (via the
     * {@link PacketProcessor}, that packet
     * will also be checked and sent. Therefore at the maximum, 3 packets may
     * be returned from a single message.
     *
     * @return The packet to reply with
     */
    @Override
    public OutgoingAuthenticatedPacket getReplyPacket() {
        return null;
    }

    /**
     * An abstract class which processes the given implemented {@link Message}.
     */
    @Override
    public void process() {
        getPacketProcessor().setOutgoingPacket(null);
        Integer set = deserialize();
        if(set >= 0){
            W.p("got new order num: " + set);
            OrderManager.setCurrentOrderNumber(set);
        }
    }

    @Override
    public Integer deserialize(){
        return new Gson().fromJson(getPacketProcessor().getJsonRequest(), Integer.class);
    }
}
