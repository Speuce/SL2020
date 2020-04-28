package main.java.lucia.client.content.order.impl;

import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.client.content.time.TimeFormat;
import main.java.lucia.consts.ClientConstants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

/**
 * An itemlist with a time for delivery/pickup,
 * order time, etc. Should contain mostly getters & setters
 *
 * @author Matt Kwiatkowski
 * @author Zach Unrau
 * @author Brett Downey
 */
public abstract class TimedItemList extends ItemList {

    /**
     * The time when this order was began
     */
    private ClientTime creationTime;

    /**
     * The flag which indicates if this order is still pending
     */
    private boolean pendingOrder;

    /**
     * The time this order was finalized
     */
    private ClientTime builtTime;

    /**
     * The time the order left for a delivery, if this order was not a pickup
     */
    private ClientTime deliveryLeftTime;

    /**
     * The flag which indicates if this order is a pre-order
     */
    private boolean preorder;

    /**
     * True if the order has been completed (i.e made and paid)
     */
    private boolean completed;

    /**
     * The time of the preorder time, note that is
     * {@code preorder} is {@code false}, then this
     * will be null
     */
    private ClientTime orderTime;

    /**
     * True if the order is a delivery order
     */
    private boolean isDelivery = false;

    /**
     * true if the order has completed being made and is ready for
     * the customer.
     */
    private boolean isMade = false;

    /**
     * Indicates whether the order is ready for dispatch?
     */
    private boolean isDispatchReady;

    private boolean preOrderPast = false;


    public TimedItemList(){
        pendingOrder = true;
        this.creationTime = new ClientTime(TimeFormat.FORMATTER_12_HOUR,
                ZoneId.systemDefault()); // TODO When needed check saskatoon/winnipeg etc. SERVER SIDED ONLY
        this.orderTime = new ClientTime(TimeFormat.FORMATTER_12_HOUR,
                ClientTime.getWinnipegTimeZone());
    }

    /**
     * Set the time of this order, if it is an preorder
     * @param year
     * @param month
     * @param dayOfMonth
     * @param hour
     * @param minute
     */
    public void setPreorderTime(int year, Month month, int dayOfMonth, int hour, int minute) {
        this.orderTime = new ClientTime(LocalDateTime.of(year, month, dayOfMonth, hour, minute));
    }

    /**
     * Returns true if the order is for a future date (not today)
     */
    public boolean isFuturePreorder(){
        return orderTime.getThisTime().isAfter(LocalDate.now(ZoneId.systemDefault()).plusDays(1).atTime(ClientConstants.DAY_RESET, 0));
    }

    /**
     * Gets the time that the order was taken out to be delivered
     *
     * @return an {@link ClientTime} representing when the order was taken out by a driver
     */
    public ClientTime getTimeOut() {
        return deliveryLeftTime;
    }

    /**
     * @return {@code true} if the delivery has been taken out
     */
    public boolean isTakenOut() {
        return isDelivery && !pendingOrder;
    }

    /**
     * Get the time that this order was created
     */
    public ClientTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(ClientTime creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * @return TRUE if the order is still pending
     */
    public boolean isPendingOrder() {
        return pendingOrder;
    }

    public void setPendingOrder(boolean pendingOrder) {
        this.pendingOrder = pendingOrder;
    }

    /**
     * @return the time that the order was finalized
     */
    public ClientTime getBuiltTime() {
        return builtTime;
    }

    public void setBuiltTime(ClientTime builtTime) {
        this.builtTime = builtTime;
    }

    /**
     * @return the time left for the order if it is a delivery
     */
    public ClientTime getDeliveryLeftTime() {
        return deliveryLeftTime;
    }

    public void setDeliveryLeftTime(ClientTime deliveryLeftTime) {
        this.deliveryLeftTime = deliveryLeftTime;
    }

    /**
     * @return TRUE if this order is a preorder
     */
    public boolean isPreOrder() {
        return preorder;
    }

    public void setPreorder(boolean preorder) {
        this.preorder = preorder;
    }


    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public ClientTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(ClientTime orderTime) {
        this.orderTime = orderTime;
    }

    public boolean isDelivery() {
        return isDelivery;
    }

    public void setDelivery(boolean delivery) {
        isDelivery = delivery;
    }

    public boolean isMade() {
        return isMade;
    }

    public void setMade(boolean made) {
        isMade = made;
    }

    public boolean isPreOrderForDispatch() {
        return isDispatchReady;
    }

    public void setPreOrderReadyForDispatch(boolean dispatchReady) {
        isDispatchReady = dispatchReady;
    }

    public boolean isPreOrderPast() {
        return preOrderPast;
    }

    public void setPreOrderPast(boolean preOrderPast) {
        this.preOrderPast = preOrderPast;
    }

}
