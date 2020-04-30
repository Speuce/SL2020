package main.java.lucia.fxml.controllers.impl.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import main.java.lucia.Client;
import main.java.lucia.client.AsynchronousTaskService;
import main.java.lucia.client.content.rights.Rights;
import main.java.lucia.client.manager.impl.OrderManager;
import main.java.lucia.client.protocol.message.impl.order.FoundOrdernumMessage;
import main.java.lucia.consts.ClientConstants;
import main.java.lucia.fxml.FxmlConstants;
import main.java.lucia.fxml.InterfaceBuilder;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.Controller;
import main.java.lucia.fxml.controllers.impl.main.Resolution;
import main.java.lucia.net.packet.impl.outgoing.PacketSender;
import main.java.lucia.net.packet.impl.outgoing.codec.login.OutgoingLoginAttemptPacket;
import main.java.lucia.net.packet.impl.outgoing.codec.login.OutgoingLoginPacket;
import main.java.lucia.net.packet.impl.outgoing.codec.login.OutgoingNewAccountPacket;
import main.java.lucia.net.security.passwords.CryptographicHash;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//import com.sun.org.apache.bcel.internal.generic.LADD;

/**
 * The controller which handles the login screen when
 * the program initially starts up.
 *
 * @author Zachery Unrau
 * @author Matt Kwiatkowski
 */
public class LoginController implements Controller {

  /**
   * The maximum amount of characters which can fit within the
   * error code length
   */
  private static final int MAX_ERROR_LENGTH = 27;

  /**
   * The base width used to develop the GUI
   */
  private static final int BASE_WIDTH = 1920;

  /**
   * The base height used to develop the GUI
   */
  private static final int BASE_HEIGHT = 1080;

  private static final DateTimeFormatter regularDateFormat = DateTimeFormatter.ofPattern("EEEE, MMMM dd");

  @FXML
  private Text SLText;

  @FXML
  private JFXTextField userid;

  @FXML
  private JFXPasswordField password;

  @FXML
  private Label contact;

  @FXML
  private JFXButton confirmLogin;

  @FXML
  private JFXButton newAccount;

  @FXML
  private Text incorrectCredentials;

  @FXML
  private Label dateLabel;

  @FXML
  private Label orderLabel;

  @FXML
  private Label attempts;

  @FXML
  private Pane settingsPane;

  @FXML
  private Label storeDisplay;

  @FXML
  private JFXButton settingsButton;

  @FXML
  private Pane locationsPane;

  @FXML
  private JFXButton stmarys;

  @FXML
  private JFXButton saskatooneast;

  @FXML
  private JFXButton saskatoonwest;

  @FXML
  private JFXButton brandon;

  @FXML
  private JFXButton morris;

  @FXML
  private JFXButton regent;

  @FXML
  private JFXButton henderson;

  @FXML
  private JFXButton pembina;

  @FXML
  private JFXButton corydon;

  @FXML
  private JFXButton portage;

  @FXML
  private JFXButton main;

  @FXML
  private JFXButton steinbach;

  @FXML
  private Pane previewPane;

  @FXML
  private Label storeDisplayPreview;

  @FXML
  private Pane createAccountPane;

  @FXML
  private JFXTextField createUsername;

  @FXML
  private JFXTextField createPhoneNumber;

  @FXML
  private JFXTextField createEmail;

  @FXML
  private JFXPasswordField createPassword;

  @FXML
  private JFXTextField signOffUsername;

  @FXML
  private JFXPasswordField signOffPassword;

  @FXML
  private JFXRadioButton ownerPerm;

  @FXML
  private ToggleGroup toggleGroup;

  @FXML
  private JFXRadioButton managerPerm;

  @FXML
  private JFXRadioButton statsPerm;

  @FXML
  private JFXButton createAccount;

  @FXML
  private Label incorrectSignOff;

  @FXML
  private JFXButton exitCreateAccount;


  /**
   * The amount of times a failed login has occured
   */
  private int failedLoginCount = 0;


  /**
   * Initializes the controllegdsgfds
   */
  @FXML
  public void initialize() {
    ControllerMap.addController(ControllerType.LOGIN_CONTROLLER, this);
    incorrectCredentials.setTextAlignment(TextAlignment.CENTER);
  }

  /**
   * // TODO This is here for the bypass option!
   * @param event
   */
  @FXML
  public void accessGranted(ActionEvent event) {
    accessGranted();
  }

  /**
   * Closes the store (and the program)
   */
  @FXML
  public void closeStore() {
    Client.shutdown(0);
  }

  /**
   * Occurs when the user attempts to login
   */
  @FXML
  public void confirmLogin() {
    checkLogin();
  }

  /**
   * Occurs when the user attempts to create an account
   */
  @FXML
  public void createAccount() {
    if(PacketSender.getCurrentPacketSender() != null && PacketSender.getCurrentPacketSender().isActive()) {
      AsynchronousTaskService.process(() -> {
        String signOffPasswordHashed = CryptographicHash.hashPassword(signOffPassword.getText());
        String newPasswordHashed = CryptographicHash.hashPassword(createPassword.getText());
        Rights rights = ownerPerm.isSelected() ? Rights.OWNER : managerPerm.isSelected() ? Rights.MANAGER : statsPerm.isSelected() ? Rights.STATISTICS : Rights.NONE;

        OutgoingNewAccountPacket outgoing = new OutgoingNewAccountPacket();
        outgoing.setNewAccountUsername(createUsername.getText())
            .setNewAccountPassword(newPasswordHashed)
            .setEmail(createEmail.getText())
            .setPhoneNumber(createPhoneNumber.getText())
            .setRights(rights)
            .setUsername(signOffUsername.getText())
            .setHashedPassword(signOffPasswordHashed);

        PacketSender.getCurrentPacketSender().sendMessage(outgoing);
        resetCredentials();
      });

      enableLogin();
      disableCreateCustomer();
    } else {
      Client.logger.info("This request cannot occur since the network is not connected");
    }
  }

  /**
   * Occurs when the user exits the create customer
   * interface
   */
  @FXML
  public void exitCreateCustomer() {
    createAccountPane.setVisible(false);
    createAccount.setVisible(false);
    exitCreateAccount.setVisible(false);
    enableLogin();
  }

  /**
   * Enables our user to be able to use the "enter"
   * key to proceed.
   *
   * @param event The KeyEvent that triggered this action
   */
  @FXML
  public void keyAccess(KeyEvent event) {
    if (confirmLogin.isVisible()) {
      if (event.getCode() == KeyCode.ENTER) {
        checkLogin();
      }
    }
    if (exitCreateAccount.isVisible()) {
      if (event.getCode() == KeyCode.ENTER) {
        createAccount();
      }
    }
  }

  /**
   * Loads the create a new account interface
   */
  @FXML
  public void loadCreateAccount() {
    disableLogin();
    getCreateCustomer();
  }

  /**
   * Occurs when the user presses the "open store" button
   */
  @FXML
  public void openStore() {
    accessGranted();
  }

  @FXML
  private void resetCredentials() {
    Platform.runLater(() -> {
      signOffUsername.clear();
      signOffPassword.clear();
      createPassword.clear();
      createPhoneNumber.clear();
      createEmail.clear();
      createUsername.clear();
    });
  }

  /**
   * Occurs when the user presses the settings
   * button
   */
  @FXML
  public void settings() {
    settingsPane.setVisible(!settingsPane.isVisible());
    disableCreateCustomer();
    locationsPane.setVisible(false);
    enableLogin();
  }

  /**
   * Occurs when the user presses the store location
   * selection button
   */
  @FXML
  public void storeLocationSelection() {
    if (locationsPane.isVisible()) {
      locationsPane.setVisible(false);
      enableLogin();
    } else {
      locationsPane.setVisible(true);
      disableLogin();
    }
  }

  /**
   * Occurs when a user selects a new store
   *
   * @param event The button selected
   */
  @FXML
  public void storeSelect(ActionEvent event) {
    JFXButton button = (JFXButton) event.getSource();
    String name = StringUtils.capitalize(button.getId());
    storeDisplay.setText(name);
    storeDisplayPreview.setText(name);
    locationsPane.setVisible(false);
    enableLogin();
  }

  /**
   * Displayed when the wrong login credentials are inputted
   */
  public void wrongLogin(String errorMessage) {
    Platform.runLater(() -> {
      incorrectCredentials.setText(errorMessage);
      incorrectCredentials.setVisible(true);
      if (++failedLoginCount == 5) {
        accessDenied();
      }
    });
  }

  /**
   * Ran when access is granted to be able to login into the program
   */
  public void accessGranted() {
    Platform.runLater(() -> {
      try {

        /* Get the correct screen resolution */
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Resolution optimalResolution = findOptimalResolution(device);
        InterfaceBuilder.setProgramResolution(optimalResolution);

        Stage primaryStage = new Stage();
        InterfaceBuilder.setSecondaryStage(primaryStage);
        Stage close = InterfaceBuilder.getPrimaryStage();
        FXMLLoader mainProgram = new FXMLLoader(
            getClass().getClassLoader().getResource(FxmlConstants.MAIN_FXML_DIRECTORY));
        Pane root = mainProgram.load();

        primaryStage.setTitle(ClientConstants.NAME + " " + ClientConstants.VERSION);
        primaryStage.setOnCloseRequest((WindowEvent window) -> Client.shutdown(0));


        Scene c = new Scene(new Group(root), optimalResolution.getWidth(), optimalResolution.getHeight());

        primaryStage.setScene(c);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        /* Finalize the stage switch */
        close.close();
        primaryStage.show();


        /* Load all the orders off the server */
        if(ClientConstants.ENABLE_NET){
          OrderManager.loadAllOrders();
          OrderManager.loadAllPreOrders();
        }


      } catch (Exception e) {
        Client.logger.error("An error has occurred while switching to the main GUI!", e);
      }
    });
//    W.p("running order test in 15 seconds....");
//    AsynchronousTaskService.scheduleProcess(() ->{
//      TestMaker.testOrderPlace();
//    }, 15);
  }
  /**
   * Displays the access denied information
   */
  private void accessDenied() {
    confirmLogin.setDisable(true);
    userid.setDisable(false);
    password.setDisable(false);
    contact.setVisible(true);
    incorrectCredentials.setVisible(false);
  }

  /**
   * Opens the preview pane when the login
   * is complete
   */
  public void openPreview() {
    Platform.runLater(() -> {
      updatePreviewPane();
      FoundOrdernumMessage.requestYesterdaysOrders();
      previewPane.toFront();
      previewPane.setVisible(true);
    });
  }

  /**
   * Updates the preview pane to contain current info
   */
  public void updatePreviewPane(){
    LocalDateTime now = LocalDateTime.now();
    dateLabel.setText("Today is " + regularDateFormat.format(now) + getSuffix(now.getDayOfMonth()));
    orderLabel.setText("Yesterday there were ... orders.");

  }

  /**
   * Sets the "Yesterday there were ## orders" field
   */
  public void setYesterdaysOrders(int yesterdaysOrders){
    Platform.runLater(() -> {
      orderLabel.setText("Yesterday there were " + yesterdaysOrders + " orders.");
    });
  }

  /**
   * Returns the appropriate suffix for an integer
   * @param i the given integer
   * @return an {@link String} that contains the
   * appropriate suffix for the provided integer, i.
   */
  private String getSuffix(int i){
    if (i >= 11 && i <= 13) {
      return "th";
    }
    switch (i % 10) {
      case 1:  return "st";
      case 2:  return "nd";
      case 3:  return "rd";
      default: return "th";
    }
  }

  /**
   * Gets the create customer information
   */
  private void getCreateCustomer() {
    disableLogin();
    createAccountPane.setVisible(true);
    createAccount.setVisible(true);
    incorrectCredentials.setVisible(false);
    exitCreateAccount.setVisible(true);
    signOffPassword.setVisible(true);
    signOffUsername.setVisible(true);

  }

  private JFXButton getSelectedStore() {
    for (Node e : locationsPane.getChildren()) {
      if (e.isPressed()) {
        System.out.println("FOUND " + e.getId());
        return (JFXButton) e;
      }
    }

    return null;
  }

  /**
   * Disables the create customer
   * information
   */
  private void disableCreateCustomer() {
    exitCreateCustomer();
  }

  /**
   * Disables the login buttons
   */
  private void disableLogin() {
    userid.setVisible(false);
    password.setVisible(false);
    newAccount.setVisible(false);
    confirmLogin.setVisible(false);
    SLText.setVisible(false);
  }

  /**
   * Enables the login buttons
   */
  private void enableLogin() {
    userid.setVisible(true);
    password.setVisible(true);
    newAccount.setVisible(true);
    confirmLogin.setVisible(true);
    SLText.setVisible(true);
  }

  /**
   * Sends the login details
   */
  private void checkLogin() {
    if(PacketSender.getCurrentPacketSender() != null && PacketSender.getCurrentPacketSender().isActive()) {
      incorrectCredentials.setVisible(false);
      incorrectSignOff.setVisible(false);
      AsynchronousTaskService.process(() -> {
        String hashedPassword = CryptographicHash.hashPassword(password.getText());
        getSelectedStore();
        OutgoingLoginPacket outgoing = new OutgoingLoginAttemptPacket()
            .setUsername(userid.getText())
            .setHashedPassword(hashedPassword);
        PacketSender.getCurrentPacketSender().sendMessage(outgoing);
      });
    } else {
      wrongLogin("Connection not established.");
      Client.logger.error("The check login attempt cannot proceed since the network is not enabled.");
    }
  }

  /**
   * Finds the optimal resolution based off of the given graphics device
   *
   * @param graphicsDevice The graphics device for the resolution
   */
  private Resolution findOptimalResolution(GraphicsDevice graphicsDevice) {
    int width = graphicsDevice.getDisplayMode().getWidth();
    int height = graphicsDevice.getDisplayMode().getHeight();
    double scaleX = width == BASE_WIDTH ? 0 : (width / (double) BASE_WIDTH);
    double scaleY = height == BASE_HEIGHT ? 0 : (height / (double) BASE_HEIGHT);
    double offsetX = width == BASE_WIDTH ? 0 : ((BASE_WIDTH - width) / 2.0) * -1;
    double offsetY = height == BASE_HEIGHT ? 0 : ((BASE_HEIGHT - height) / 2.0) * -1;
    return new Resolution(width, height, scaleX, scaleY, offsetX, offsetY);
  }

  @Override
  public ControllerType getType() {
    return ControllerType.LOGIN_CONTROLLER;
  }
}