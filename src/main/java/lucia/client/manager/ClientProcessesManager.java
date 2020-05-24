package main.java.lucia.client.manager;

import com.google.common.base.Preconditions;
import main.java.lucia.client.manager.impl.PrinterManager;

import java.util.HashMap;

/**
 * The client processes manager, which handles
 * all other managers and allows implements
 * necessary delegation between this class and
 * all other associated managers within the client
 *
 * @author Brett Downey
 */
public class ClientProcessesManager {

  /**
   * The instance of this singleton class
   */
  public static final ClientProcessesManager INSTANCE = new ClientProcessesManager();

  /**
   * The map which contains the manager type linked to the
   * given manager
   */
  private final HashMap<ManagerType, Manager> MAP = new HashMap<>();

  /**
   * A private constructor to support this singleton class
   */
  private ClientProcessesManager() {
    register(ManagerType.PRINTER_MANAGER, new PrinterManager());
  }

  /**
   * Registers the given {@link ManagerType} and
   * {@link Manager} to the map
   *
   * @param type The ManagerType key
   * @param manager The Manager entry
   */
  private void register(ManagerType type, Manager manager) {
    MAP.put(type, manager);
  }

  /**
   * Gets the given manager instance
   *
   * @param type The manager type to get
   * @return The manager
   */
  public static Manager getManagerOf(ManagerType type) {
    Preconditions.checkState(INSTANCE.MAP.containsKey(type), "The given ManagerType has not been registered!");
    return INSTANCE.MAP.get(type);
  }

  /**
   * Processes all managers that the store currently has registered
   */
  public static void processAllManagers() {
    INSTANCE.MAP.forEach((type, manager) -> manager.process());
  }
}