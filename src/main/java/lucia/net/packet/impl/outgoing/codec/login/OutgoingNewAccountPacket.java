package main.java.lucia.net.packet.impl.outgoing.codec.login;

import main.java.lucia.client.content.rights.Rights;
import main.java.lucia.net.packet.impl.GsonTypeFactory;

/**
 * An outgoing new account attempt packet
 *
 * @author Brett Downey
 */
public class OutgoingNewAccountPacket extends OutgoingLoginPacket {

  /**
   * The type associated to this login attempt packet
   */
  private String type = GsonTypeFactory.CLIENT_TO_SERVER_NEW_ACCOUNT_ATTEMPT;

  /**
   * The username for the new account that is
   * being created
   */
  private String newAccountUsername;

  /**
   * The hashed password for the new account that
   * is being created.
   */
  private String newAccountPassword;

  /**
   * The email for the new account that is being
   * created.
   */
  private String email;

  /**
   * The phone number for the new account that is being
   * created.
   */
  private String phoneNumber;

  /**
   * The rights received from the client, this value is
   * verified before being applied to the new client. If an
   * invalid rights combination is given, the new account
   * request is denied.
   */
  private Rights rights;

  public OutgoingNewAccountPacket setNewAccountUsername(String newAccountUsername) {
    this.newAccountUsername = newAccountUsername;
    return this;
  }

  public OutgoingNewAccountPacket setNewAccountPassword(String newAccountPassword) {
    this.newAccountPassword = newAccountPassword;
    return this;
  }

  public OutgoingNewAccountPacket setEmail(String email) {
    this.email = email;
    return this;
  }

  public OutgoingNewAccountPacket setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public OutgoingNewAccountPacket setRights(Rights rights) {
    this.rights = rights;
    return this;
  }

  @Override
  public String toString() {
    return GsonTypeFactory.LOGIN_GSON_OUTGOING.toJson(this, this.getClass());
  }
}