package main.java.lucia.net.packet.impl.incoming.codec.login;

public class IncomingNewAccountPacket extends IncomingLoginAttemptPacket {

  public IncomingNewAccountPacket(int echoCode, int loginResponseCode) {
    super(echoCode, loginResponseCode);
  }
}