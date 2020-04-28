/*
 * Copyright (c) Zachery Unrau
 */

package main.java.lucia.client.content.order.pricing;

public class StaffDiscount {
    private boolean onShift;

    private boolean offShift;

    private final double onShiftDiscount = .25;

    private final double offShiftDiscount = .15;

    public double calcDiscount() {
        if(onShift)
            return onShiftDiscount;
        else if(offShift)
            return offShiftDiscount;
        else return -1;
    }

    public void setOnShift() {
        onShift = true;
        offShift = false;
    }

    public void setOffShift() {
        offShift = true;
        onShift = false;
    }



}
