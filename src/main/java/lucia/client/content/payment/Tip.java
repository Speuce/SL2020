package main.java.lucia.client.content.payment;

import main.java.lucia.client.content.payment.paymentmethods.SinglePayment;

import java.math.BigDecimal;

/**
 * Class representing a tip given to an employee/store
 * @author Matt Kwiatkowski
 * For GSON usage see payment.java
 */
public class Tip extends SinglePayment {

    /**
     * The employee ID of the employee that received this tip
     */
    private Integer employee;

    public Tip(PaymentType p, long price) {
        super(p, price);
    }

    /**
     * @return an {@link Integer} that is the employee's id who has received this tip
     */
    public Integer getEmployeeID(){
        return employee;
    }
}
