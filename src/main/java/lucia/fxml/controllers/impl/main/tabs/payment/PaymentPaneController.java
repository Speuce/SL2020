package main.java.lucia.fxml.controllers.impl.main.tabs.payment;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import main.java.lucia.client.content.payment.paymentmethods.PaymentMethod;
import main.java.lucia.client.content.payment.paymentmethods.SimplePayment;
import main.java.lucia.client.content.payment.paymentmethods.SplitPayment;
import main.java.lucia.client.content.payment.PaymentType;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.fxml.controllers.impl.main.Utils.GridHighlighter;
import main.java.lucia.fxml.controllers.impl.main.tabs.EmployeePane;
import main.java.lucia.fxml.controllers.impl.main.tabs.PendingOrdersPane;
import main.java.lucia.fxml.controllers.impl.main.tabs.employee.EmployeeLoginPaneController;

import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;


/**
 * Controller for the payment pane
 * @author Matt Kwiatkowski
 */
public class PaymentPaneController {

    @FXML
    private JFXButton finalizeOrder;

    @FXML
    private JFXButton debit;

    @FXML
    private JFXButton cash;

    @FXML
    private JFXButton mastercard;

    @FXML
    private JFXButton visa;

    @FXML
    private JFXButton giftCard;

    @FXML
    private JFXButton cheque;

    @FXML
    private JFXButton splitPay;

    @FXML
    private Pane amountPaidPane;

    @FXML
    private Pane cashOwedPane;

    @FXML
    private Pane cardOwedPane;

    @FXML
    private JFXTextField amountPaidBox;

    @FXML
    private JFXTextField cashTipBox;

    @FXML
    private JFXTextField enterTipBox;

    @FXML
    private JFXButton confirmPaid;

    @FXML
    private Label cashPayLabel;

    @FXML
    private Pane paymentTrackerPane;

    @FXML
    private GridPane SplitPayGridPane;

    @FXML
    private Label stillOwedLabel;

    @FXML
    private JFXButton paytypeDeleleButton;

    @FXML
    private Label amtToPayLabel;

    @FXML
    private JFXTextField amountToPayBox;

    @FXML
    private Label cardAmtPaidLabel;

    @FXML
    private JFXTextField cardAmtPaidBox;

    @FXML
    private ScrollPane splitPayScrollPane;

    @FXML
    private Pane selectServerPane;

    @FXML
    private ChoiceBox<String> selectServerBox;

    @FXML
    private Label customerOverpayLabel;

    private PendingOrdersPane parent;

    private JFXButton[] buttons;

    /**
     * the currently selected Button
     */
    private JFXButton currentSelected = null;

    /**
     * the currently selected payment type;
     */
    private PaymentType currentPaymentType;

    /**
     * map of utilized payment types. For future expansion (multi splt payments)
     */
    private Map<PaymentType, BigDecimal> paymentMap;

    /**
     * true if the payment is a splitpayment
     */
    private boolean splitPayment = false;

    /**
     * The current payment
     */
    private PaymentMethod currentPaymentMethod;

    /**
     * grid highlight for split payments
     */
    private GridHighlighter gridHighlighter;


    public PaymentPaneController(){
        super();

    }

    private TextFormatter<?> textFormatter;

    private void addFocusZeroListener(JFXTextField r){
        r.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    r.clear();
                    r.setTextFormatter(textFormatter);
                }else{
                    r.setTextFormatter(null);
                    if(r.getText().equals("")){
                        r.setText("$0.00");
                    }else{
                        r.setText(NumberFormat.getCurrencyInstance().format(parse(r.getText())));
                    }
                }
            }
        });
    }

    private void addEnterListener(JFXTextField item, Node next){
        item.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER){
                if(next == confirmPaid){
                    next.requestFocus();
                    checkIfPaid();
                    return;
                }
                next.requestFocus();
            }
        });
    }

    public void resetButtons(){
        for(JFXButton b: buttons){
            b.getStyleClass().removeAll("PaymentSelected");
        }
    }

    /**
     * called when the payment pane is opened
     */
    public void open(){
        resetButtons();
        resetBoxes();
        resetPaymentTracker();
        resetSplitPayment();
        resetSelectServer();
        finalizeOrder.setVisible(false);
        currentPaymentMethod = null;
        currentPaymentType = null;
        currentSelected = null;
    }

    @FXML
    public void initialize(){
        splitPayScrollPane.setFitToWidth(true);
        textFormatter = EmployeeLoginPaneController.buildNumericFormatList(5);
        buttons = new JFXButton[]{debit, cash, mastercard, visa, giftCard, cheque};
        gridHighlighter = new GridHighlighter(SplitPayGridPane, "#D3D3D3");
        gridHighlighter.addSelectListener(r -> paytypeDeleleButton.setVisible(r));
        ChangeListener<Boolean> cashListener = (observable, oldValue, newValue) -> {
            if(!newValue){
                long pai = Math.round(parse(amountPaidBox.getText())*100.0);
                if(pai <= 0){
                    wrong(amountPaidBox);
                    amountPaidBox.requestFocus();
                    return;
                }
                if(!splitPayment){
                    long change = getRemainingPrice(pai);
                    if(change <= 0) {
                        cashPayLabel.setText(NumberFormat.getCurrencyInstance().format(getRemainingPrice(pai)/100.0));
                    }else{
                        wrong(amountPaidBox);
                        amountPaidBox.requestFocus();
                        return;
                    }
                }else {

                }
            }
        };
        ChangeListener<Boolean> cashToPayListener = (observable, oldValue, newValue) -> {
            if(!newValue && splitPayment){
                long pai = Math.round(parse(amountPaidBox.getText())*100.0);
                if(pai <= 0){
                    wrong(amountPaidBox);
                    amountPaidBox.requestFocus();
                    return;
                }
                long contribute = Math.round(parse(amountToPayBox.getText())*100.0);
                if (contribute <= 0) {
                    cashPayLabel.setText(NumberFormat.getCurrencyInstance().format(0));
                    wrong(amountPaidBox);
                    amountToPayBox.requestFocus();
                    return;
                }
                long diff = pai - contribute;;
                if (diff < 0) {
                    wrong(amountToPayBox);
                    amountToPayBox.requestFocus();
                    return;
                } else {
                    cashPayLabel.setText(NumberFormat.getCurrencyInstance().format(diff/100.0));
                }
            }
        };
        addFocusZeroListener(amountToPayBox);
        amountPaidBox.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER){
               if(splitPayment){
                    amountToPayBox.requestFocus();
               }else{
                   cashTipBox.requestFocus();
               }
            }
        });
        addFocusZeroListener(amountPaidBox);
        addEnterListener(amountToPayBox, cashTipBox);
        addFocusZeroListener(cashTipBox);
        addEnterListener(cashTipBox, confirmPaid);
        addFocusZeroListener(enterTipBox);
        enterTipBox.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER){
                if(splitPayment){
                    cardAmtPaidBox.requestFocus();
                }else{
                    confirmPaid.requestFocus();
                    checkIfPaid();
                }
            }
        });
        addFocusZeroListener(cardAmtPaidBox);
        addEnterListener(cardAmtPaidBox, confirmPaid);

        amountPaidBox.focusedProperty().addListener(cashListener);
        amountToPayBox.focusedProperty().addListener(cashToPayListener);
        resetButtons();

        selectServerBox.setOnAction(a ->{
            if(selectServerBox.getValue() != null && !selectServerBox.getValue().equals("")){
                finalizeOrder.setVisible(true);
            }
        });
    }

    private long getRemainingPrice(long paid){
        long total = parent.getHighlightedOrder().getGrandTotalLongWithTax();
        return PaymentMethod.nearest5(total - paid);
    }

    private void resetBoxes(){
        cashTipBox.setText("$0.00");
        enterTipBox.setText("$0.00");
        amountPaidBox.setText("$0.00");
        cashPayLabel.setText("$0.00");
        amountToPayBox.setText("$0.00");
        cardAmtPaidBox.setText("$0.00");
    }

    /**
     * Shows and loads the server selector pane
     */
    public void showServerSelector(){
        selectServerPane.setVisible(true);
        selectServerBox.getItems().clear();
        ArrayList<String> employeesWithCashout = new ArrayList<String>();
        //TODO add server sending employees with cashout
        employeesWithCashout.add(EmployeePane.instance.getTestEmployee().getName());
        for(String s: employeesWithCashout){
            selectServerBox.getItems().add(s);
        }
    }

    @FXML
    void splitPayButton(MouseEvent event) {
        if(splitPayment){
            splitPayment = false;
            splitPay.getStyleClass().remove("PaymentSelected");
            if(currentSelected == cash){
                openCashPane();
            }else if(currentSelected != null){
                openCardPane();
            }
        }else{
            splitPay.getStyleClass().add("PaymentSelected");
            splitPayment = true;
            if(currentSelected == cash){
                openCashPane();
            }else if(currentSelected != null){
                openCardPane();
            }
            currentPaymentMethod = new SplitPayment(parent.getHighlightedOrder().getGrandTotalLongWithTax());
        }
    }

    private void resetSelectServer(){
        selectServerPane.setVisible(false);
        selectServerBox.valueProperty().set(null);
    }

    /**
     *
     * Toggles off split payment button and sets splitpayment variable to false
     * Used for resetting this pane
     */
    private void resetSplitPayment(){
        splitPayment = false;
        splitPay.getStyleClass().remove("PaymentSelected");
        paymentTrackerPane.setVisible(false);
    }

    @FXML
    void finalizePayment(MouseEvent event) {
        Order o = parent.getHighlightedOrder();
        String selected = selectServerBox.getValue();
        Integer id = EmployeePane.instance.getEmployeeId(selected);
        o.setServer(id);
        o.setPaymentMethod(currentPaymentMethod);
        o.setPaymentType(currentPaymentMethod.getPaymentType());
        parent.completeOrder(o);
        parent.setPaymentPaneVisible(false);
    }

    @FXML
    void confirmPaid(MouseEvent event) {
        checkIfPaid();
    }

    /**
     * Checks the fields to see if the payment can be confirmed
     */
    private void checkIfPaid(){
        if(currentSelected == cash){
            if(!amountPaidBox.getText().equals("")){
                Double d = parse(amountPaidBox.getText());
                BigDecimal price = parent.getHighlightedOrder().getAllTotal();
                double tip = parse(cashTipBox.getText());

                if(!splitPayment){
                    if(d == null|| d.doubleValue()+0.03 < price.doubleValue()){
                        wrong(amountPaidBox);
                        return;
                    }
                    SimplePayment pay = new SimplePayment(PaymentType.CASH, parent.getHighlightedOrder().getGrandTotalLongWithTax());
                    if(tip > 0){
                        pay.setTip(Math.round(tip*100));
                    }
                    currentPaymentMethod = pay;
                    closePayArea();
                }else{
                    long amtToPay = Math.round(parse(amountPaidBox.getText())*100);
                    SimplePayment pay = new SimplePayment(PaymentType.CASH, amtToPay);
                    ((SplitPayment) currentPaymentMethod).addPayment(pay);
                    closePayArea();
                }

            }else{
                wrong(amountPaidBox);
            }
        }else{
            //paying card
            long tip = 0;
            if(!enterTipBox.getText().equals("")){
                tip = Math.round(parse(enterTipBox.getText())*100);
            }

            if(splitPayment){
                long paid = 0;
                if(!cardAmtPaidBox.getText().equals("")){
                    paid = Math.round(parse(cardAmtPaidBox.getText())*100);
                }
                if(cardAmtPaidBox.getText().equals("") || paid <= 0){
                    wrong(cardAmtPaidBox);
                    return;
                }
                SimplePayment pay = new SimplePayment(currentPaymentType, paid);
                if(tip > 0){
                    pay.setTip(tip);
                }
                ((SplitPayment) currentPaymentMethod).addPayment(pay);
            }else{
                SimplePayment pay = new SimplePayment(currentPaymentType, parent.getHighlightedOrder().getGrandTotalLongWithTax());
                currentPaymentMethod = pay;
            }
            closePayArea();

        }
    }
    /**
     * Closes the pay area
     */
    private void closePayArea(){
        amountPaidPane.setVisible(false);
        cashOwedPane.setVisible(false);
        cardOwedPane.setVisible(false);
        currentSelected = null;
        resetButtons();
        if(splitPayment){
            resetPaymentTracker();
        }else{
            showServerSelector();
        }
    }

    @FXML
    private void deletePayment(MouseEvent e){
        if(gridHighlighter.getHighLightedRow() > -1){
            SplitPayment sp = (SplitPayment) currentPaymentMethod;

            sp.getPaymentSet().remove(gridHighlighter.getHighLightedRow());

            resetPaymentTracker();
        }
    }

    private Double parse(String s){
        return Double.parseDouble(s.replaceAll("[^\\d.]", ""));
    }



    /**
     * Resets the payment overview area
     */
    private void resetPaymentTracker(){
        //remove old
        SplitPayGridPane.getChildren().clear();
        if(currentPaymentMethod instanceof SplitPayment){
            SplitPayment r = (SplitPayment) currentPaymentMethod;
            if(Math.abs(r.getRemainingPrice()) <= 2){
                showServerSelector();
                return;
            }
            int index = 0;
            for(SimplePayment single: r.getPaymentSet()){
                Label paytype = new Label(single.getPaymentType().getDisplayCode());
                paytype.setFont(parent.pt25Font);
                paytype.setPrefSize(125, 35);
                gridHighlighter.registerNode(paytype);

                Label payAmt = new Label(NumberFormat.getCurrencyInstance().format(single.getPrice()/100.0));
                payAmt.setPrefSize(200, 35);
                payAmt.setFont(parent.pt25Font);
                gridHighlighter.registerNode(payAmt);

                SplitPayGridPane.add(paytype, 0, index);
                SplitPayGridPane.add(payAmt, 1, index);

                index++;
            }
            long owed = r.getRemainingPrice();
            if(owed < 0){
                stillOwedLabel.setStyle("-fx-background-color:#D16666;");
                customerOverpayLabel.setVisible(true);
            }else{
                stillOwedLabel.setStyle("-fx-background-color:#D3D3D3;");
                customerOverpayLabel.setVisible(false);
            }
            stillOwedLabel.setText(NumberFormat.getCurrencyInstance().format(r.getRemainingPrice()/100.0));
            paymentTrackerPane.setVisible(true);

        }


    }

    /**
     * Switches payment pane
     * @param b the button pressed
     */
    private void switchButton(JFXButton b){
        if(currentSelected == b){
            return;
        }
        currentPaymentType = getPaymentType(b);
        if(currentSelected == null){
            amountPaidPane.setVisible(true);
            if(b == cash){
                openCashPane();
            }else{
                openCardPane();
            }
        }else{
            if(b == cash){
                cardOwedPane.setVisible(false);
                openCashPane();
            }else if(currentSelected == cash){
                cashOwedPane.setVisible(false);
                openCardPane();
            }
        }
        resetButtons();
        b.getStyleClass().add("PaymentSelected");
        currentSelected = b;
    }

    private void openCashPane(){
        resetBoxes();
        cashOwedPane.setVisible(true);
        paymentTrackerPane.setVisible(false);
        amountPaidBox.requestFocus();
        if(splitPayment){
            amtToPayLabel.setVisible(true);
            amountToPayBox.setVisible(true);
        }else{
            amtToPayLabel.setVisible(false);
            amountToPayBox.setVisible(false);
        }
    }

    private void openCardPane(){
        resetBoxes();
        cardOwedPane.setVisible(true);
        paymentTrackerPane.setVisible(false);
        enterTipBox.requestFocus();
        if(splitPayment){
            cardAmtPaidBox.setVisible(true);
            cardAmtPaidLabel.setVisible(true);
        }else{
            cardAmtPaidBox.setVisible(false);
            cardAmtPaidLabel.setVisible(false);
        }
    }

    /**
     * Maps payment buttons to payment type
     * @param b the button to find the appropriate mapping of
     * @return {@link PaymentType} representing the payment type used
     */
    private PaymentType getPaymentType(JFXButton b){
        if(b == cash){
            return PaymentType.CASH;
        }else if(b == debit){
            return PaymentType.DEBIT;
        }else if(b == mastercard){
            return PaymentType.MASTERCARD;
        }else if(b == visa){
            return PaymentType.VISA;
        }else if(b == giftCard){
            return PaymentType.GIFT;
        }else if(b == cheque){
            return PaymentType.CHEQUE;
        }
        return null;
    }

    /**
     * Sets the parent pane controller
     * @param p the parent
     */
    public void setParent(PendingOrdersPane p){
        parent = p;
    }

    @FXML
    void chequeButton(MouseEvent event) {
        switchButton(cheque);
    }

    @FXML
    void debitButton(MouseEvent event) {
        switchButton(debit);
    }

    @FXML
    void visaButton(MouseEvent event) {
        switchButton(visa);
    }

    @FXML
    void giftButton(MouseEvent event) {
        switchButton(giftCard);
    }

    @FXML
    void mcButton(MouseEvent event) {
        switchButton(mastercard);
    }
    @FXML
    void cashButton(MouseEvent event) {
        switchButton(cash);
    }

    private void wrong(Node f){ EmployeePane.blink(f, Color.decode("#D3D3D3"), Color.decode("#AA0000"));
    }
}
