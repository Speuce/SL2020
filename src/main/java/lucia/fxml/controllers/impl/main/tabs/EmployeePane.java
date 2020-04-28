package main.java.lucia.fxml.controllers.impl.main.tabs;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import main.java.lucia.client.AsynchronousTaskService;
import main.java.lucia.client.content.employee.*;
import main.java.lucia.client.protocol.message.impl.employee.GetEmployeeMapMessage;
import main.java.lucia.client.protocol.message.impl.employee.SetEmployeeMessage;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.Controller;
import main.java.lucia.fxml.controllers.impl.main.tabs.employee.EmployeeCreateAccountPaneController;
import main.java.lucia.fxml.controllers.impl.main.tabs.employee.EmployeeLoginPaneController;
import main.java.lucia.fxml.controllers.impl.main.tabs.employee.EmployeeManagerNotesPane;
import main.java.lucia.fxml.controllers.impl.main.tabs.employee.EmployeeSettingsPaneController;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.Interval;
import org.joda.time.Period;


import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * The controller for the employee pane.
 * @author Matt Kwiatkowski
 */

public class EmployeePane implements Controller {

//    @FXML
//    private Pane employeeLoginPane;

    @FXML
    private JFXButton forward1;

    @FXML
    private JFXButton back1;

    @FXML
    private Pane employeeHomePage;

    @FXML
    private Label employeeWelcomeText;

    @FXML
    private Label employeeShiftInfo;

    @FXML
    private Pane employeeMenuPane;

    @FXML
    private Pane employeeManagerNotesPane;

    @FXML
    private Circle managerNotesNotificaton;

    @FXML
    private Circle scheduleNotification;

    @FXML
    private JFXButton employeeShiftButton;

    @FXML
    private Pane employeeNotesPane;

    @FXML
    private JFXTextArea employeeNotes;

    @FXML
    private Pane employeeSettingsPane;

    @FXML
    private Pane employeeCredsPane;

    @FXML
    private JFXButton employeeCreateAccountButton;

    @FXML
    private EmployeeSettingsPaneController employeeSettingsPaneController;

    @FXML
    private EmployeeLoginPaneController employeeCredsPaneController;

    @FXML
    private EmployeeManagerNotesPane employeeManagerNotesPaneController;

    @FXML
    private EmployeeCreateAccountPaneController employeeCreateAccountPaneController;

    @FXML
    private Pane employeeCreateAccountPane;

    public static EmployeePane instance;

    private Pane currentPane = null;


    /**
     * The currently logged in employee
     */
    private Employee currentEmployee;

    /**
     * An employee to test with
     */
    private static Employee testEmployee;

    /**
     * Set to true if you want an employee to test with
     */
    private boolean testing = true;

    /**
     * a map to cache employee names and id's
     */
    private BiMap<Integer, String> employeeMap;

    /**
     * The font to use for notes
     */
    private Font noteFont;

    @FXML
    public void initialize() {
        employeeMap = HashBiMap.create();
        noteFont = new Font("Calibri",30);
        employeeManagerNotesPaneController.setEmployeePane(this);
        employeeSettingsPaneController.setEmployeePane(this);
        employeeCredsPaneController.setEmployeePane(this);
        employeeCreateAccountPaneController.setEmployeePane(this);
        ControllerMap.addController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER, this);
        instance = this;
        if(testing == true){
            System.out.println("building test Employee");
            buildTestEmployee();
//            for(int){
//
//            }
            employeeMap.put(testEmployee.getEmployeeID(), testEmployee.getName());
        }
    }
    /**
     * Called when the pane is opened.
     * Resets everything to original state
     * Used for auto-log off.
     */
    public void open(){
        //System.out.println("======= OPENED =======");
        //System.out.println(employeeManagerNotesPane.getId());
        changePane(employeeCredsPane);
        GetEmployeeMapMessage.sendMapRequest();
        employeeCredsPaneController.clearFields();
        startTimedRunner();
        System.out.println(GsonTypeFactory.EMPLOYEE_GSON.toJson(testEmployee));
        //GetEmployeeMapMessage.sendEmployeeMapRequest();
        //Todo empty current employee, etc
    }

    /**
     * called when pane is closed.
     * Stops running tasks
     */
    public void close(){
        stopTimedRunner();
    }

    /**
     * Called when correct credentials are provided
     */
    public void accessGranted(){

        changePane(employeeHomePage);
        if(currentEmployee instanceof Manager){
            employeeCreateAccountButton.setVisible(true);
        }else{
            employeeCreateAccountButton.setVisible(false);
        }
        employeeWelcomeText.setText("Welcome, " + currentEmployee.getName());
        if(currentEmployee.isWorking()){
            shiftButtonRed();
            updateEmployeeHours();
        }else{
            employeeShiftInfo.setText("You are not currently on shift");
            shiftButtonGreen();
        }
        if(currentEmployee.hasUnreadManagerNotes()){
            managerNotesNotificaton.setVisible(true);
        }
    }

    private static HashMap<Integer, Set<Consumer<Employee>>> callbackMap = new HashMap<Integer, Set<Consumer<Employee>>>();

    /**
     * Gets the emplouee from the server
     * @param id the id of the employee
     * @param callback
     */
    public static void getEmployee(Integer id, Consumer<Employee> callback){
        if(id == testEmployee.getEmployeeID()){
            callback.accept(testEmployee);
            return;
        }

        if(callbackMap.containsKey(id)){
            callbackMap.get(id).add(callback);
        }else{
            HashSet<Consumer<Employee>> callbackSet = new HashSet<Consumer<Employee>>();
            callbackSet.add(callback);
            callbackMap.put(id, callbackSet);
        }
        //TODO maybe move it in the else?
       SetEmployeeMessage.sendEmployeeRequestMessage(id);
    }

    public static void gotEmployee(Employee e){
        if(e != null){
            Integer id = Integer.valueOf(e.getEmployeeID());
            if(!callbackMap.containsKey(id)){
                System.out.println("------- GOT AN EMPLOYEE BUT FOUND NO CALLBACKS FOR IT!!------");
                return;
            }
            callbackMap.get(id).stream().forEach(c -> c.accept(e));
            callbackMap.remove(id);
            System.out.println("------- FOUND EMPLOYEE: "+id+ "------");
        }

    }

    /**
     * Changes panes
     * @param p the pane to change to
     */
    private void changePane(Pane p){
        if(currentPane == null){
            currentPane = p;
            employeeMenuPane.setVisible(false);
            return;
        }
        currentPane.setVisible(false);

        if(p == employeeCredsPane){
            employeeMenuPane.setVisible(false);
        }else{
            employeeMenuPane.setVisible(true);
        }
        currentPane = p;
        p.setVisible(true);
    }

    /**
     * Blinks a textbox, as seen with employee login
     * @param f the field to make blink
     * @param original the original color to revert to
     * @param blink the color to blink to
     *
     */
    public static void blink(Node f, Color original, Color blink, int blinks, long lasting){
        for(int x = 1; x <(blinks*2+1); x++){
            final int y = x;
            AsynchronousTaskService.scheduleProcessMils(() ->{
                if(y % 2 == 0){
                    f.setStyle("-fx-background-color: " + getHex(original));
                }else{
                    f.setStyle("-fx-background-color: " + getHex(blink));
                }
            }, x*lasting);
        }
    }

    /**
     * Blinks a textbox, as seen with employee login
     * @param f the field to make blink
     * @param original the original color to revert to
     * @param blink the color to blink to
     */
    public static void blink(Node f, Color original, Color blink){
        blink(f, original, blink, 3, 180L);
    }

    /**
     * Used for converting {@link Color}
     * @param c the Color you wish to convert
     * @return the Hex String beginning with an '#'
     */
    public static String getHex(Color c){
        return String.format("#%02X%02X%02X", c.getRed(), c.getGreen(),c.getBlue());
    }

    /**
     * When this is false, the runner will stop
     */
    private Future<?> runner;

    /**
     * Starts the runnable that runs various timed methods within the employee pane
     */
    private void startTimedRunner(){
        runner = AsynchronousTaskService.scheduleRepeating(() ->{
            if(currentEmployee.isWorking()){
                updateEmployeeHours();
            }
            //TODO check for updated chats and stuff
        }, 1000L);
        //Platform.runLater();
    }

    /**
     * Stops the runnable that runs various timed methods within the employee pane
     */
    private void stopTimedRunner(){
        runner.cancel(true);
    }
    /**
     * Updates displayed Employee Hours
     */
    private void updateEmployeeHours(){
        if(currentEmployee.isWorking()){
            employeeShiftInfo.setText("You have worked: " + currentEmployee.getCurrentShift().getHoursWorkedStr() + " hours");
        }
    }

    /**
     * Returns a formatted label with an appropriate text size
     */
    public JFXTextArea getLabel(String s){
        JFXTextArea t = new JFXTextArea(s);
        t.setEditable(false);
        t.setWrapText(true);
        t.setCache(false);
        t.setFont(noteFont);
        return t;
    }

    /**
     * gets the mapped employee name from it's id
     * @param id the employee's unique id
     * @return the employee's name. Null if not found
     */
    public String getEmployeeName(int id){
        if(employeeMap.containsKey(Integer.valueOf(id))){
            return employeeMap.get(Integer.valueOf(id));
        }
        return null;
    }

    public String getEmployeeNameShort(int id){
        if(employeeMap.containsKey(Integer.valueOf(id))){
            String name = employeeMap.get(Integer.valueOf(id));
            int split = name.indexOf(" ");
            return name.substring(0, split) +" "+ name.substring(split+1, split+2) + ".";
        }
        return null;
    }

    /**
     * Gets the mapped name of the employee from its' id
     * @param name the name of the employee
     * @return the employee's id
     */
    public Integer getEmployeeId(String name){
        if(employeeMap.inverse().containsKey(name)){
            return employeeMap.inverse().get(name);
        }
        return null;
    }

    /**
     * formats the time worked into hours and minutes
     * @param ms the time worked in ms
     */
    public String getFormattedWorkTime(long ms){
        Period p = new Period(ms);
        return p.getHours() + "h " + p.getMinutes() + "m";
    }
    public void shiftButtonGreen(){
        employeeShiftButton.setText("Start Shift");
        setColor(employeeShiftButton, Color.green);
    }

    public void shiftButtonRed(){
        employeeShiftButton.setText("End Shift");
        setColor(employeeShiftButton, Color.red);
    }

    /**
     * Called when manager notes button is clicked
     */
    @FXML
    void onManagerNotesButtonPressed(MouseEvent event) {
        changePane(employeeManagerNotesPane);
        employeeManagerNotesPaneController.open();
        currentEmployee.setManagerNotesRead();
        managerNotesNotificaton.setVisible(false);
    }

    /**
     * Sets the background color property of a node
     * @param n the node to change the color of
     * @param r the color to change to
     */
    public static void setColor(Node n, Color r){
        n.setStyle("-fx-background-color: " + getHex(r));
    }

    /**
     * Called when my schedule button is clicked
     */
    @FXML
    void onMyScheduleButtonPressed(MouseEvent event) {
       // changePane(emp);
    }

    /**
     * Called when my notes button is clicked
     */
    @FXML
    void onNotepadClicked(MouseEvent event) {
        changePane(employeeNotesPane);
    }

    /**
     * Called when orders taken button is clicked
     */
    @FXML
    void onOrdersTakenButtonClicked(MouseEvent event) {
    }

    /**
     * Called when request button is clicked
     */
    @FXML
    void onRequestButtonClicked(MouseEvent event) {

    }

    /**
     * Called when sl chat button is clicked
     */
    @FXML
    void onSLChatButtonClicked(MouseEvent event) {

    }

    @FXML
    void onHomeButtonClicked(MouseEvent event) {
        changePane(employeeHomePage);
    }
    /**
     * Called when settings button is clicked
     */
    @FXML
    void onSettingsButtonClicked(MouseEvent event) {
        employeeSettingsPaneController.open();
        changePane(employeeSettingsPane);
    }

    /**
     * Called when start shift, end shift button is clicked
     */
    @FXML
    void onShiftButtonClicked(MouseEvent event) {
        if(currentEmployee.isWorking()){
            shiftButtonGreen();
            currentEmployee.endShift();
        }else{
            currentEmployee.startShift();
            shiftButtonRed();
            updateEmployeeHours();
        }
    }

    /**
     *
     * @return the currently logged in {@link Employee}
     */
    public Employee getCurrentEmployee(){
        return this.currentEmployee;
    }

    /**
     * Called when statistics button is clicked
     */
    @FXML
    void onStatisticsButtonClicked(MouseEvent event) {

    }

    @FXML
    void onCreateAccountButtonPressed(MouseEvent e){
        changePane(employeeCreateAccountPane);
    }


    /**
     * The preferred date and time format to be used in this pane
     * using singleton instance
     */
    static SimpleDateFormat dateFormat;

    public static SimpleDateFormat getDateFormat(){
        if(dateFormat == null){
            dateFormat = new SimpleDateFormat("EEE, MMM d, h:mm a");
        }
        return dateFormat;
    }

    /**
     * The preferred time format to be used in this application
     */
    static SimpleDateFormat timeFormat;

    /**
     *
     * @return the preffered time format for this application
     */
    public static SimpleDateFormat getTimeFormat(){
        if(timeFormat == null){
            timeFormat = new SimpleDateFormat("h:mm a");
        }
        return timeFormat;
    }
    /**
     *
     * @return {@code true} if the employeePane is using a test employee
     */
    public boolean isTesting(){
        return testing;
    }

    private static void loadTestEmployee(){

    }

    /**
     * builds/gets a test employee
     */
    private static void buildTestEmployee(){
        testEmployee = new Manager("Matthew Kwiatkowski", 1016, "pass");
        //testEmployee.addNote(new EmployeeNote("Remember to fill the line before close"));
        testEmployee.setAddress("805 Henderson Hwy");
        testEmployee.addManagerNote(new ManagerNote("Bad close yesterday.", "Kos"));
        Platform.runLater(() -> testEmployee.addManagerNote(new ManagerNote("Quit forgetting to fill the line before close!!!!", "Evan")));

        for(int i = 1; i < 10; i++){
            Platform.runLater(() -> testEmployee.addManagerNote(new ManagerNote("Quit forgetting to fill the line before close!!!!", "Evan")));
        }

    }

    /**
     * Gets the employee that is used for testing
     * @return
     */
    public Employee getTestEmployee(){
        return this.testEmployee;
    }

    /**
     * setsa the current employee
     *
     */
    public void setCurrentEmployee(Employee e){
        this.currentEmployee = e;
    }


    public EmployeeLoginPaneController getEmployeeCredsPaneController(){
        return employeeCredsPaneController;
    }


    @Override
    public ControllerType getType() {
        return ControllerType.EMPLOYEE_PANE_CONTROLLER;
    }

    public Map<Integer, String> getEmployeeMap() {
        return employeeMap;
    }

    public void setEmployeeMap(BiMap<Integer, String> employeeMap) {
        this.employeeMap = employeeMap;
        System.out.println("Successfully got employee map!!");
    }
}
