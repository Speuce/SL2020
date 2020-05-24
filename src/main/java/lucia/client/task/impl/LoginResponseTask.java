package main.java.lucia.client.task.impl;

import main.java.lucia.client.task.Task;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.login.LoginController;
import main.java.lucia.net.packet.impl.incoming.codec.login.IncomingLoginAttemptPacket;

/**
 * The login response task which occurs when the server
 * replies to us with a login code
 *
 * @author Brett Downey
 */
public class LoginResponseTask extends Task {

  /**
   * The delay for this task which is zero since it
   * should be executed immediately.
   */
  private static final int DELAY = 0;

  /**
   * The incoming login packet which created this login
   * response task
   */
  private final IncomingLoginAttemptPacket incoming;

  /**
   * The constructor
   *
   * @param incoming
   */
  public LoginResponseTask(IncomingLoginAttemptPacket incoming) {
    super(DELAY, false, false);
    this.incoming = incoming;
  }

  public void run() {
    LoginController loginController = (LoginController) ControllerMap.getController(ControllerType.LOGIN_CONTROLLER);

    switch (incoming.getLoginResponseOpcode()) {
      case 2:
        loginController.openPreview();
        break;
      default:
        loginController.wrongLogin("INCORRECT LOGIN"); // TODO From server
        break;
    }
  }
}