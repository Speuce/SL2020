package main.java.lucia.fxml.controllers;

/**
 * An enumerated type representing the controller types.
 *
 * @author Brett Downey
 * @author Matt Kwiatkowski
 */
public enum ControllerType {

  /*
    Primary controllers that are initialized
    from direct FXML file loads.
   */

  /**
   * The login controller which handles all login requests
   */
  LOGIN_CONTROLLER(),

  /**
   * The master controller which handles the main program
   */
  MASTER_CONTROLLER(),

  /*
    Primary controllers that are within
    {@code MASTER_CONTROLLER}.
   */

  /**
   * The employee login pane which handles all
   * employee login information
   */
  EMPLOYEE_PANE_CONTROLLER(),

  /**
   * The pane which allows viewing of all current
   * pre-orders
   */
  VIEW_PREODERS_PANE_CONTROLLER(),

  /**
   * The pane which controls the driver
   * segments of the program
   */
  DRIVER_FRAME_PANE_CONTROLLER(),

  /**
   * The pane which controls the pending
   * orders pane
   */
  PENDING_ORDERS_PANE_CONTROLLER(),

  /**
   * The pane which controls managing of
   * tables
   */
  MANAGE_TABLES_PANE_CONTROLLER(),

  /**
   * The pane which controls all back office
   * operations
   */
  BACK_OFFICE_PANE_CONTROLLER(),

  /**
   * The pane which controls all manager options
   */
  SERVER_REPORT_PANE_CONTROLLER(),

  /**
   * The pane which controls dispatch operations
   */
  DISPATCH_PANE_CONTROLLER(),

  /**
   * The pane which handles all transferring of orders
   * to other stores
   */
  VIEW_ORDERS_PANE_CONTROLLER(),

  /**
   * The pane which controls scheduling of managers
   */
  SCHEDULE_MANAGER_PANE_CONTROLLER(),

  /**
   * The pane which handles customer information
   */
  CUSTOMER_DIRECTORY_PANE_CONTROLLER(),

  /**
   * The pane which controls the statistics pane
   */
  STAT_PANE_CONTROLLER(),

  /**
   * The pane which controls all settings operations
   */
  SETTINGS_PANE_CONTROLLER(),

  /**
   * The pane which controls a new pickup/delivery order
   */
  PICKUP_DELIVERY_PANE_CONTROLLER(),

  /**
   * The pane which controls entering number and
   * getting new customer information
   */
  ENTER_NUMBER_PANE_CONTROLLER(),

  /**
   * The employee manager notes pane controller
   */
  EMPLOYEE_MANAGER_NOTES_CONTROLLER(),

  /**
   * The employee notes pane controller
   */
  EMPLOYEE_NOTES_CONTROLLER(),

  /**
   * The employee orders taken pane controller
   */
  EMPLOYEE_ORDERS_CONTROLLER(),

  /**
   * The employee settings page controller
   */
  EMPLOYEE_SETTINGS_CONTROLLER(),

  /**
   * The employee Login pane controller
   */
  EMPLOYEE_LOGIN_CONTROLLER();
}
