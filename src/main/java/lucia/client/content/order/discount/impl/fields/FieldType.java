package main.java.lucia.client.content.order.discount.impl.fields;

/**
 * Represents a field type input
 * @author Matthew Kwiatkowski
 */
public enum FieldType {

    /**
     * Any plaintext input
     */
    TEXT("Text"),

    /**
     * Any number input
     */
    NUMBER("Number"),

    /**
     * Any Date-specific input
     */
    DATE("Date"),

    /**
     * Any time-specific input
     */
    TIME("Time"),

    /**
     * any input that requires both a date AND time.
     */
    DATE_TIME("DateTime"),

    /**
     * Input requires staff login/permissions
     */
    STAFF("Staff"),

    /**
     * Input requires Manager login/permissions
     */
    MANAGER("Manager"),

    /**
     * Input requires driver login/permissions
     */
    DRIVER("Driver");

    private final String typeName;

    FieldType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
