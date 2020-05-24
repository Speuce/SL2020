package main.java.lucia.net.packet.impl.outgoing.codec;

import main.java.lucia.net.packet.OutgoingPacket;
import main.java.lucia.net.packet.impl.GsonTypeFactory;

/**
 * Old Authenticated packet system
 */
@Deprecated
public class OutgoingAuthenticatedPacket extends OutgoingPacket {

    private final int opcode;

    private String jsonRequest;

    public OutgoingAuthenticatedPacket(final int opcode) {
        super();
        this.opcode = opcode;
    }

    public void setJsonRequest(String jsonRequest) {
        this.jsonRequest = jsonRequest;
    }

    public int getOpcode() {
        return opcode;
    }

    public String getJsonRequest() {
        return jsonRequest;
    }

    @Override
    public String toString() {
        return GsonTypeFactory.GENERIC_GSON.toJson(this, this.getClass());
    }
}
