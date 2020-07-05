package main.java.lucia.client.content.employee.type;

import com.google.gson.GsonBuilder;
import main.java.lucia.client.content.customer.ComplaintAction;
import main.java.lucia.client.content.employee.EmployeeNote;
import main.java.lucia.client.content.employee.ManagerNote;
import main.java.lucia.client.content.employee.Permission;
import main.java.lucia.client.content.employee.Shift;
import main.java.lucia.net.security.passwords.CryptographicHash;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Encapsulates all details about an employee
 *
 * @author Matt Kwiatkowski
 */
public class Employee extends ShiftWorker{

  /**
   * The specific id of this pizza on the server.
   * DO NOT SET. Let Gson do that.
   */
  private int rowNum = -1;

  /**
   * The name of the employee
   */
  private String name;


  /**
   * The Employee's personal notes
   */
  private SortedSet<EmployeeNote> notes;

  /**
   * The Employee's manager notes
   */
  private SortedSet<ManagerNote> managerNotes;

  /**
   * The employee ID, a 4 or 5 digit unique identifier for employees
   */
  private int employeeID;

  /**
   * The hashed password
   */
  private String password;

  /**
   * The employee's address
   */
  private String address;

//  /**
//   * Used for searching for past shifts in a specific range
//   *
//   * @param from the furthest date you would like to include
//   * @param to the most recent date that you would like to include
//   * @return A {@link SortedSet} of completed {@link Shift} done by the employee
//   */
//  public SortedSet<Shift> getShiftsRange(ClientTime from, ClientTime to) {
//    return this.pastShifts.subSet(new Shift(to), new Shift(from));
//  }

  // TODO change to BD or long
  public long getPayfromShift(Shift f) {
    return Math.round(f.getMinutesWorked()/60.0 * this.getPayRate());
  }


  /**
   * Used to change the employees password
   *
   * @param pass plaintext password
   */
  public void changePassword(String pass) {
    CryptographicHash.hashPassword(pass, this::setPasswordHash);
    updateServer();
    //TODO update the server about the change?
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Creates a default employee earning minimum wage This should only be allowed by managers.
   * Managers should give the employee a default password, that should be changed asap.
   *
   * @param name the employee's name
   * @param employeeID the employee's ID (4 or 5 digits)
   * @param password the unhashed default password of the employee
   * @param pay the hourly pay of the employee (in cents)
   */
  public Employee(String name, int employeeID, String password, long pay) {
      super(new HashSet<Permission>(), new TreeSet<>(), pay);
    this.name = name;
    this.employeeID = employeeID;
    this.changePassword(password);
    this.notes = new TreeSet<>();
    this.managerNotes = new TreeSet<>();

    /* add employee default permissions */
    addPermission(Permission.COMPLAINT_NEW);
    addPermission(Permission.COMPLAINT_ACTION_NONE);
    addPermission(Permission.COMPLAINT_ACTION_REMAKE);
    addPermission(Permission.COMPLAINT_ACTION_FREE_2L);

    //TODO update the server. Tell them a new employee has been added
  }

//  /**
//   * Used for searching for pay from a specific range
//   *
//   * @param from the furthest date you would like to include
//   * @param to the most recent date that you would like to include
//   * @return a {@link Double} representing the pay from that time period.
//   */
//  public Double getTotalPayFromRange(ClientTime from, ClientTime to) {
//    return getShiftsRange(from, to).stream().mapToDouble(this::getPayfromShift).sum();
//  }



  /**
   * PRIVATE METHOD to set the password SHOULD ONLY BE USED WITHIN THE CLASS WITH A HASHED PASSWORD
   *
   * @param hashedPassword the password as a hash
   */
  private void setPasswordHash(String hashedPassword) {
    this.password = hashedPassword;
  }

  /**
   * Supplies {@code true} to the callback if the password is correct, false otherwise
   *
   * @param password the plaintext password
   * @param callback a boolean {@link Consumer}
   */
  public void testPass(String password, Consumer<Boolean> callback) {
    CryptographicHash
        .hashPassword(password, hash -> callback.accept(hash.equals(getHashedPassword())));
  }

  /**
   * Updates the server about this employee from this client
   */
  public void updateServer() {

  }

  private String getHashedPassword() {
    return this.password;
  }

  public static Employee fromJson(String json) {
    return new GsonBuilder().create().fromJson(json, Employee.class);
  }

  /**
   * @return a shorter employee name. I.e "John D." rather than "John Doe"
   */
  public String getShorterName() {
    int split = name.indexOf(" ");
    return name.substring(0, split) + " " + name.substring(split + 1, split + 2) + ".";
  }
//
//  public void addCashoutOfTill(CashOutOfTill t) {
//    outOfTills.add(t);
//  }
//
//    /**
//     * Cash taken out of this employee's till for various reasons
//     */
//  public List<CashOutOfTill> getOutOfTills() {
//    return outOfTills;
//  }
//
//  /**
//   * Gets the orders that the employee has taken
//   */
//  public List<Integer> getOrdersTaken() {
//    return this.ordersTaken;
//  }

  public int getEmployeeID() {
    return employeeID;
  }
//
//  public Shift getCurrentShift() {
//    return this.currentShift;
//  }

  public boolean hasUnreadManagerNotes() {
    for (ManagerNote f : managerNotes) {
      if (!f.isRead()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Sets all manager notes to read
   */
  public void setManagerNotesRead() {
    for (ManagerNote f : managerNotes) {
      f.setRead(true);
    }
  }

//  /**
//   * @return {@code true} if the employee is currently on shift
//   */
//  public boolean isWorking() {
//    return this.currentShift != null;
//  }

  public void setEmployeeID(int employeeID) {
    this.employeeID = employeeID;
  }

//  /**
//   * @return {@code true} if the employee is a server (i.e the employee can have a cashout)
//   */
//  public boolean isServer() {
//    return server;
//  }

//  /**
//   * @param server should be true if the employee is a server (Can have a cashout)
//   */
//  public void setServer(boolean server) {
//    this.server = server;
//  }

  public SortedSet<EmployeeNote> getNotes() {
    return notes;
  }

  public SortedSet<ManagerNote> getManagerNotes() {
    return managerNotes;
  }

  public void addNote(EmployeeNote e) {
    notes.add(e);
  }

  public void addManagerNote(ManagerNote e) {
    managerNotes.add(e);
  }



  /**
   * Gets a list of allowable complaint actions for the employee
   *
   * @return a List of {@link ComplaintAction} that the employee has access to
   */
  public List<ComplaintAction> getAllowableAction() {
    List<ComplaintAction> actionList = Arrays.asList(ComplaintAction.values());
    return actionList.stream().filter(a -> getPermissions().contains(a.getPermission()))
        .collect(Collectors.toList());
  }

  /**
   * @return the employee's address
   */
  public String getAddress() {
    return address;
  }

  /**
   * sets the employee's address
   *
   * @param address the employee's address
   */
  public void setAddress(String address) {
    this.address = address;
  }

}
