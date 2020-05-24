package main.java.lucia.net.packet.impl.outgoing.codec.login;

import main.java.lucia.net.packet.OutgoingPacket;

public abstract class OutgoingLoginPacket extends OutgoingPacket {

  private String username;

  private String hashedPassword;

    public OutgoingLoginPacket(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public OutgoingLoginPacket() {
    }

    public OutgoingLoginPacket setUsername(String username) {
    this.username = username;
    return this;
  }

  public OutgoingLoginPacket setHashedPassword(String hashedPassword) {
    this.hashedPassword = hashedPassword;
    return this;
  }
}
