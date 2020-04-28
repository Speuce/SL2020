package main.java.lucia.fxml;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.java.lucia.Client;
import main.java.lucia.consts.ClientConstants;
import main.java.lucia.fxml.controllers.impl.main.Resolution;

/**
 * The InterfaceBuilder for our application.
 *
 * @author Zachery Unrau
 * @author Brett Downey
 * @author Matthew Kwiatkowski
 */
public class InterfaceBuilder extends Application {

  /**
   * The ScheduleExecutorService which handles the JavaFX thread
   */
  private static ScheduledExecutorService SERVICE = service();

  /**
   * The X value for our drag listener.
   */
  private double x;

  /**
   * The Y value for our drag listener.
   */
  private double y;

  /**
   * The primary stage
   */
  private static Stage primaryStage;

  /**
   * The selected resolution
   */
  private static Resolution programResolution;

  public static Resolution getProgramResolution() {
    return programResolution;
  }

  public static void setProgramResolution(Resolution programResolution) {
    InterfaceBuilder.programResolution = programResolution;
  }

  /**
   * Gets the primary stage
   *
   * @return The primary stage
   */
  static public Stage getPrimaryStage() {
    return primaryStage;
  }

  /**
   * The stage after the primary stage is closed
   * Static object holder (I couldn't find it anywhere else)
   */
  private static Stage secondaryStage;

  /**
   * Gets the secondary stage
   *
   * @return the stage after the primary stage is closed
   */
  public static Stage getSecondaryStage(){
    return secondaryStage;
  }

  /**
   * Sets the secondary stage
   *
   * @param stage the secondary stage object to be held in static reference
   */
  public static void setSecondaryStage(Stage stage){
    secondaryStage = stage;
  }
  /**
   * Initializes this {@link InterfaceBuilder}.
   */
  public void initialize() {
    SERVICE.submit((Runnable) Application::launch);
  }

  /**
   * Starts the {@link InterfaceBuilder}
   *
   * @param primaryStage The primary stage of the JavaFX application.
   */
  @Override
  public void start(Stage primaryStage) {
    Thread.currentThread().setUncaughtExceptionHandler((thread, reason) -> {
      for(String ignored : FxmlConstants.IGNORED_FXML_EXCEPTIONS) {
        if(reason.getMessage().contains(ignored)) {
          return;
        } else {
          Client.logger.error(reason.getStackTrace());
        }
      }

      Client.logger.error("An error has occurred within the JavaFX thread!", reason);
    });

    try {
      InterfaceBuilder.primaryStage = primaryStage;
      Parent loginScene = new FXMLLoader(getClass().getResource(FxmlConstants.LOGIN_FXML_DIRECTORY))
          .load();

      addDragListeners(loginScene, primaryStage);

      primaryStage.setTitle(ClientConstants.NAME + " v" + ClientConstants.VERSION);
      primaryStage
          .setScene(new Scene(loginScene, FxmlConstants.LOGIN_WIDTH, FxmlConstants.LOGIN_HEIGHT));
      primaryStage.setOnCloseRequest((WindowEvent event) -> Client.shutdown(0));
      primaryStage.setResizable(false);
      primaryStage.show();
    } catch (IOException e) {
      Client.logger.error("An error has occurred while building the GUI!", e);
    }
  }

  /**
   * Closes the {@link InterfaceBuilder}
   */
  @Override
  public void stop() {
    primaryStage.close();
    Platform.exit();
    SERVICE.shutdownNow();
    Client.logger.info("The FXML Thread has successfully shutdown.");
  }

  /**
   * Adds our drag listeners to our application's login screen.
   *
   * @param node The given Node to add the listeners to
   * @param primaryStage The given Stage to add the listeners to
   */
  private void addDragListeners(final Node node, Stage primaryStage) {
    node.setOnMousePressed((MouseEvent mouseEvent) -> {
      this.x = node.getScene().getWindow().getX() - mouseEvent.getScreenX();
      this.y = node.getScene().getWindow().getY() - mouseEvent.getScreenY();
    });

    node.setOnMouseDragged((MouseEvent mouseEvent) -> {
      primaryStage.setX(mouseEvent.getScreenX() + this.x);
      primaryStage.setY(mouseEvent.getScreenY() + this.y);
    });
  }

  /**
   * Creates the service used for asynchronous tasks.
   *
   * @return {@code ScheduledExecutorService} the newly created service.
   */
  private static ScheduledExecutorService service() {
    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    executor.setThreadFactory(new ThreadFactoryBuilder().setNameFormat("GUI Thread").build());
    return Executors.unconfigurableScheduledExecutorService(executor);
  }
}