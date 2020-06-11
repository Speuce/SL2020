package main.java.lucia.fxml.controllers.impl.main.tabs.order.enternumber;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.converter.IntegerStringConverter;
import main.java.lucia.client.AsynchronousTaskService;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.discount.DiscountManager;
import main.java.lucia.client.content.order.discount.impl.AppliedDiscount;
import main.java.lucia.client.content.order.discount.impl.CustomDiscount;
import main.java.lucia.client.content.order.discount.impl.fields.DiscountField;
import main.java.lucia.client.content.order.discount.impl.fields.FieldType;
import main.java.lucia.consts.ColorUtils;
import main.java.lucia.fxml.controllers.impl.main.Utils.GridHighlighter;
import main.java.lucia.fxml.utils.BlinkUtils;
import main.java.lucia.fxml.utils.Fonts;
import org.jetbrains.annotations.Nullable;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * The controller for the discount selector pane
 * @author Matthew Kwiatkowski
 */
public class DiscountPaneController {

    @FXML
    private Pane discountPane;

    @FXML
    private JFXComboBox<CustomDiscount> discountSelectorComboBox;

    @FXML
    private Pane discountFurtherInfoPane;

    @FXML
    private GridPane discountOrderViewPane;

    @FXML
    private JFXButton removeButton;

    private GridHighlighter highlighter;

    /**
     * The current discount being displayed
     */
    private CustomDiscount currentDiscount;

    private int viewPaneRows = 0;

    /**
     * Variables for showing the order fields pane
     */
    private static final int leftMargin = 10, topMargin = 10, verticalSpacing = 50, labelToFieldSpace = 20, labelMaxSize = 150;

    private static final Color green = ColorUtils.fromHexfx("#006600");

    private static final UnaryOperator<TextFormatter.Change> integerFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("-?([1-9][0-9]*)?")) {
            return change;
        }
        return null;
    };

    /**
     * The current order being discounted.
     */
    private Order curr;

    @FXML
    public void initialize(){
        highlighter = new GridHighlighter(discountOrderViewPane, "#D3D3D3");
        highlighter.addSelectListener(aBoolean -> {
            removeButton.setVisible(aBoolean);
            if(aBoolean){
                AppliedDiscount dis = curr.getDiscountList().get(highlighter.getHighLightedRow()+1);
                if(dis.getApplied() instanceof CustomDiscount)
                showFields((CustomDiscount) dis.getApplied(), dis.getFilledFields());
            }else{
                discountFurtherInfoPane.getChildren().clear();
            }

        });
        discountSelectorComboBox.valueProperty().addListener((observableValue, customDiscount, t1) ->
                showFields(t1, null));
    }

    /**
     * Opens this pane, displaying the discount information for the given order.
     * @param o the order to display
     */
    public void open(Order o){
        curr = o;
        discountSelectorComboBox.setValue(null);
        discountSelectorComboBox.getItems().clear();
        AsynchronousTaskService.process(() -> {
            List<CustomDiscount> lis = new LinkedList<>();
            for(CustomDiscount d: DiscountManager.getLoadedCustomDiscounts()){
                if(d.isDiscountEligible(o)){
                    lis.add(d);
                }
            }
            Platform.runLater(() -> discountSelectorComboBox.getItems().addAll(lis));
        });
        discountPane.setVisible(true);
        discountFurtherInfoPane.getChildren().clear();
        discountOrderViewPane.getChildren().clear();

        viewPaneRows = 0;
        addToOrderViewPane("Subtotal ",o.getCost());
        for(AppliedDiscount d: o.getDiscountList()){
            addToOrderViewPane(d.getApplied().getName(), d.getAmtSaved());
        }
    }

    /**
     * Shows the fields for the given discount in the
     * further info pane.
     * @param discount the discount to show fields for.
     * @param objMap the filled fields of the discount (may be null)
     */
    private void showFields(CustomDiscount discount,@Nullable Map<String, Object> objMap){
        discountFurtherInfoPane.getChildren().clear();
        List<DiscountField> fields = discount.getFields();
        this.currentDiscount = discount;
        DiscountField f;
        Object o;
        int i;
        for (i = 0; i < fields.size(); i++) {
            f = fields.get(i);
            Label la = new Label(f.getLabel());
            la.setFont(Fonts.pt25Font);
            la.setLayoutX(leftMargin);
            la.setLayoutY((i*verticalSpacing) + topMargin);
            System.out.println("X size: " + la.getWidth());

            o = (objMap != null) ? objMap.get(f.getLabel()) : null;
            Region item = getNodeFor(f.getType(), o);
            item.setLayoutY((i*verticalSpacing)+topMargin);
            item.setLayoutX(leftMargin + labelMaxSize);

            discountFurtherInfoPane.getChildren().addAll(la, item);
        }
        JFXButton button = new JFXButton("Add");
        button.setStyle("-fx-background-color: #006600; -fx-background-radius: 20; -fx-border-radius: 20;");
        button.setLayoutX(leftMargin);
        button.setLayoutY((i*verticalSpacing)+topMargin);
        button.setPrefWidth(66);
        button.setPrefHeight(44);
        button.setOnAction( value -> {onDiscountAdd();});
        discountFurtherInfoPane.getChildren().add(button);
    }

    /**
     * Shows a 'discount not applicable' message
     */
    private void showNotApplicable(){
        Label lab = new Label("Order not eligible for this discount.");
    }


    /**
     * Adds the given string to the order view pane
     * @param s the string to add
     * @param amount the cost column for this s
     */
    private void addToOrderViewPane(String s, long amount){
        Label labels = new Label(s);
        labels.setFont(Fonts.pt20Font);

        Label labelAmt = new Label(NumberFormat.getCurrencyInstance().format(amount/100.0));
        labelAmt.setFont(Fonts.pt20Font);

        discountOrderViewPane.add(labels, 0, viewPaneRows);
        discountOrderViewPane.add(labelAmt, 0, viewPaneRows);

        highlighter.registerNode(labels);
        highlighter.registerNode(labelAmt);
        viewPaneRows++;
    }

    /**
     * Called when the 'add' button is hit for a discount.
     */
    private void onDiscountAdd(){
        Map<String, Object> objs = readObject();
        if(objs == null){
            return;
        }
        currentDiscount.applyDiscount(curr, objs);
        open(curr);
    }

    /**
     * Reads the objects from the discount info pane.
     * @return the object map, if reading was sucessful, null otherwise.
     */
    private Map<String, Object> readObject(){
        String currlab = null;
        Object currData = null;
        Map<String, Object> objAdd = new HashMap<>();
        for (Node child : discountFurtherInfoPane.getChildren()) {
            if(child instanceof JFXButton){
                continue;
            }
            if(child instanceof Label){
                currlab = ((Label)child).getText();
            }else{
                Object ret = getObjectFrom(child);
                if(ret != null){
                    currData = ret;
                }else{
                    BlinkUtils.wrong(child);
                    return null;
                }
            }

            if(currlab != null && currData != null){
                objAdd.put(currlab, currData);
                currlab = null;
                currData = null;
            }
        }
        return objAdd;
    }

    @FXML
    void onConfirm(ActionEvent event) {
        discountPane.setVisible(false);
    }

    @FXML
    void onRemove(ActionEvent event) {
        AppliedDiscount d = curr.getDiscountList().get(highlighter.getHighLightedRow()+1);
        if(d != null){
            d.remove(curr);
            open(curr);
        }
    }

    /**
     * Gets a node for the given fieldtype
     * @param type the type to match a node to
     * @param def the default value to place in the node.
     * @return a {@link Region} corelating to the fieldtype input.
     */
    private Region getNodeFor(FieldType type,@Nullable Object def){
        switch(type){
            case TEXT:
                JFXTextField ret = new JFXTextField();
                if(def instanceof String)
                    ret.setText((String)def);
                return ret;
            case DATE:
                JFXDatePicker ret2 =  new JFXDatePicker();
                if(def instanceof LocalDate)
                    ret2.setValue((LocalDate)def);
                return ret2;
            case TIME:
                JFXTimePicker ret3 = new JFXTimePicker();
                if(def instanceof LocalTime)
                    ret3.setValue((LocalTime)def);
                return ret3;
            case NUMBER:
                JFXTextField f = new JFXTextField();
                f.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0, integerFilter));
                if(def != null)
                    f.setText(def.toString());
                return f;
            default:
                throw new IllegalArgumentException("Could not find defined node for field type: " + type);

        }
    }

    /**
     * Attempts to read a field from a node
     */
    private Object getObjectFrom(Node r){
        if(r instanceof JFXTextField){
            return ((JFXTextField)r).getText();
        }else if(r instanceof JFXTimePicker){
            return ((JFXTimePicker)r).getValue();
        }else if(r instanceof JFXDatePicker){
            return ((JFXDatePicker)r).getValue();
        }
        return null;
    }

//    /**
//     * Class for coloring the discount combo box items
//     */
//    class DiscountCell{
//
//        /**
//         * Whether or not the discount should be 'greyed' out or not
//         */
//        private boolean grey;
//
//        /**
//         * The discount reffered to.
//         */
//        private final Discount discount;
//
//        public DiscountCell(Discount discount, boolean grey) {
//            this.grey = grey;
//            this.discount = discount;
//        }
//
//        /**
//         * The discount reffered to.
//         */
//        public Discount getDiscount() {
//            return discount;
//        }
//
//        /**
//         * Whether or not the discount should be 'greyed' out or not
//         */
//        public boolean isGrey() {
//            return grey;
//        }
//
//        /**
//         * Whether or not the discount should be 'greyed' out or not
//         */
//        public void setGrey(boolean grey) {
//            this.grey = grey;
//        }
//    }
}
