package main.java.lucia.client.content.customer;

import main.java.lucia.client.content.employee.Permission;

/**
 * Actions that managers/employees can take based on complaints
 * @author Matt Kwiatkowski
 */
public enum ComplaintAction {

    NONE("None", Permission.COMPLAINT_ACTION_NONE),
    REMAKE("Remake", Permission.COMPLAINT_ACTION_REMAKE),
    PARTIAL_REFUND("Partial Refund", true, Permission.COMPLAINT_ACTION_REFUND),
    FULL_REFUND("Full Refund", Permission.COMPLAINT_ACTION_REFUND),
    FREE_2L("Free 2L", Permission.COMPLAINT_ACTION_FREE_2L),
    FREE_ITEMS("Free Items", true, Permission.COMPLAINT_ACTION_FREE_ITEMS),
    STORE_CREDIT("Store Credit", true, Permission.COMPLAINT_ACTION_CREDIT),
    STORE_DISCOUNT("Store Discount", true, Permission.COMPLAINT_ACTION_DISCOUNT);

    /**
     * The display text of this action
     */
    private String display;

    /**
     * Flag for whether or not the Action Info box should be shown
     */
    private boolean additionalInfo;

    /**
     * The permission necessary for this action
     */
    private Permission permission;

    private ComplaintAction(String display, boolean additionalInfo, Permission p) {
        this.display = display;
        this.additionalInfo = additionalInfo;
        this.permission = p;
    }

    private ComplaintAction(String display, Permission p){
        this(display, false, p);
    }


    public String getDisplay() {
        return display;
    }

    public boolean hasAdditionalInfo() {
        return additionalInfo;
    }

    public Permission getPermission() {
        return permission;
    }

    /**
     * used for mapping display names to complaint actions
     * @param display the display name to convert
     * @return the proper {@link ComplaintAction}
     */
    public static ComplaintAction getFromDisplay(String display){
        for(ComplaintAction a: ComplaintAction.values()){
            if(a.getDisplay().equals(display)){
                return a;
            }
        }
        return NONE;
    }
}
