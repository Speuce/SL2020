package main.java.lucia.net.packet.impl.outgoing.codec;

import main.java.lucia.net.packet.OutgoingPacket;

import java.security.PublicKey;
import main.java.lucia.net.packet.impl.GsonTypeFactory;

public class OutgoingHandshakePacket extends OutgoingPacket {

    private String version;

    private byte[] encodedClientPublicKey;

    public OutgoingHandshakePacket() {
        super();
    }

    public OutgoingHandshakePacket setVersion(String version) {
        this.version = version;
        return this;
    }

    public OutgoingHandshakePacket setClientPublicKey(PublicKey clientPublicKey) {
        this.encodedClientPublicKey = clientPublicKey.getEncoded();
        return this;
    }

    @Override
    public String toString() {
        return GsonTypeFactory.GENERIC_GSON.toJson(this, this.getClass());
    }
}
