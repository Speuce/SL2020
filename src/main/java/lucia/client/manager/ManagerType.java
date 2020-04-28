package main.java.lucia.client.manager;

import main.java.lucia.client.manager.impl.OrderManager;
import main.java.lucia.client.manager.impl.PrinterManager;

/**
 * An enumerated type representing the different
 * types of managers that exist within the server
 *
 * @author Brett Downey
 */
public enum ManagerType {

  ORDER_MANAGER(OrderManager.class),

  PRINTER_MANAGER(PrinterManager.class);

  private final Class ORIGIN_CLASS;

  ManagerType(final Class ORIGIN_CLASS) {
    this.ORIGIN_CLASS = ORIGIN_CLASS;
  }

  public Class getOriginClass() {
    return ORIGIN_CLASS;
  }
}