package main.java.lucia.client.manager;


/**
 * The manager interface which is
 * implemented by all managers. Manager classes
 * are also the interface used for client to server and
 * server to client communications between the client directory
 * and the network directory. Managers are maintained and processed
 * through the {@link lucia.client.Engine}
 *
 * @author Brett Downey
 */
public interface Manager {

  /**
   * Gets the {@link ManagerType} this
   * Manager is associated to
   *
   * @return The manager type
   */
  ManagerType getType();

  /**
   * Processes the Manager
   */
  void process();
}