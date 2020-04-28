package main.java.lucia.client.protocol.message.impl;

import com.google.gson.Gson;
import main.java.lucia.Client;
import main.java.lucia.client.protocol.message.Message;
import main.java.lucia.client.protocol.message.codec.ServerUpTimeCodec;
import main.java.lucia.net.packet.PacketProcessor;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.packet.impl.outgoing.codec.OutgoingAuthenticatedPacket;

/**
 *
 *
 * @author Brett Downey
 */
public class ServerUpTimeMessage extends Message {

    /**
     * The constructor for the {@link ServerUpTimeMessage}
     *
     * @param packetProcessor The {@link PacketProcessor} which holds all
     *                        needed values for any {@link Message}.
     */
    public ServerUpTimeMessage(PacketProcessor packetProcessor) {
        super(packetProcessor);
    }

    /**
     * Processes this message.
     */
    @Override
    public void process() {
        ServerUpTimeCodec retrieved = deserialize();
        Client.logger.info(retrieved.getFormattedUptime());
    }

    /**
     * Indicates whether or not this {@link Message} should be a
     * custom response which only the specific client which requested it
     * should receive back, or if result of this can be shared to other clients.
     * This improves efficiency, for example if two clients ask for the same
     * piece of information, it only needs to be processed once for both clients.
     *
     * @return {@code true} if the code should be custom, {@code false} otherwise.
     *          This case is {@code false} since this can be given to any user.
     */
    @Override
    public boolean isCustom() {
        return false;
    }

    @Override
    public int getReplyOpcode() {
        return -1;
    }

    @Override
    public OutgoingAuthenticatedPacket getReplyPacket() {
        return null;
    }

    /**
     * Serialize this message into JSON-format.
     *
     * @return null since this is not used on the server-side.
     */
    @Override
    public String serialize() {
        return null;
    }

    /**
     * Deserialize a given JSON String into it's origin object.
     *
     * @return {@link ServerUpTimeCodec} The object input.
     */
    @Override
    public ServerUpTimeCodec deserialize() {
        return GsonTypeFactory.GENERIC_GSON.fromJson(getPacketProcessor().getJsonRequest(), ServerUpTimeCodec.class);
    }
}
