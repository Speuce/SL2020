package main.java.lucia.client.content.order.discount.impl.fields;

import main.java.lucia.client.content.order.discount.impl.DiscountAttribute;

import java.io.PrintStream;

/**
 * Represents a field that must be filled in before
 * a discount can be applied.
 * Usually not checked
 * @author Matthew Kwiatkowski
 */
public class DiscountField extends DiscountAttribute {

    /**
     * The label of this field.
     */
    private String label;

    /**
     * The type of this field.
     */
    private FieldType type;

    public DiscountField(String label, FieldType type) {
        this.label = label;
        this.type = type;
    }

    /**
     * The label of this field.
     */
    public String getLabel() {
        return label;
    }

    /**
     * The label of this field.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * The type of this field.
     */
    public FieldType getType() {
        return type;
    }

    /**
     * The type of this field.
     */
    public void setType(FieldType type) {
        this.type = type;
    }

    /**
     * Prints out information of this attribute
     *
     * @param out the {@link PrintStream} to print to.
     */
    @Override
    public void printInfo(PrintStream out) {
        out.print("Field: " + label + " Type: " + type.getTypeName());
    }
}
