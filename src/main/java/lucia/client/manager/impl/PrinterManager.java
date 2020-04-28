package main.java.lucia.client.manager.impl;

import java.awt.print.PrinterJob;
import main.java.lucia.client.manager.Manager;
import main.java.lucia.client.manager.ManagerType;

/**
 * The printer manager which controls all
 * printing operations
 *
 * @author Brett Downey
 */
public class PrinterManager implements Manager {

  private static PrinterJob printerJob;

  public PrinterManager() {
    printerJob = PrinterJob.getPrinterJob();
  }

  @Override
  public ManagerType getType() {
    return ManagerType.PRINTER_MANAGER;
  }

  @Override
  public void process() {

  }
}