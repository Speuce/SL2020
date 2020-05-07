package main.java.lucia.client.content.order.impl;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.structures.Exclude;
import main.java.lucia.util.currency.CurrencyConverter;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * An order that you can print!!
 * YAY!
 * @author Matt Kwiatkowski
 */
public abstract class PrintableOrder extends OrderInfo implements Printable {

    // TODO Printing information, such as Henderson/phone number etc should be loaded from Server as it differs from store to store
    //paper size 10cm/27m
    //Constants based on paper size and desired layout
    @Exclude
    private static final int MAX_CHARS_PER_LINE = 35, MAX_LINES_PER_PAGE = 10,
            X_OFFSET = 170, Y_OFFSET = 130, LINE_SPACING = 17, PRICE_X_OFFSET = 395, STUB_SPACING = 500,
            HEADER_OFFSET = 50, HEADER2_OFFSET = 420;

    /**
     * Standard Format for money on bills
     */
    @Exclude
    private static final NumberFormat formatter = new DecimalFormat("#0.00");

    @Exclude
    private ArrayList<String> lines;
    @Exclude
    private BigDecimal[] pricelines;
    /**
     * True if this order is being reprinted
     * It adds the extra box or whatever
     */
    @Exclude
    private boolean reprint = false;


    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        this.recalculatePrice();
        graphics.setFont(new Font("Calibri", Font.PLAIN, 10));
        if (pageIndex == 0) {
            preparePrint();
        }
        if (pageIndex * MAX_LINES_PER_PAGE >= lines.size()) {
            return NO_SUCH_PAGE;
        }

        //If this is a multipage document, print page number in bottom corner
        if (lines.size() > MAX_LINES_PER_PAGE) {
            int pages = (int) Math.ceil((double) lines.size() / MAX_LINES_PER_PAGE);
            graphics.drawString("(" + (pageIndex + 1) + "/" + pages + ")", X_OFFSET, 371);
            graphics.drawString("(" + (pageIndex + 1) + "/" + pages + ")", X_OFFSET, 726);
        }

        //Print out Order info
        int space = Y_OFFSET;
        for (int i = pageIndex * MAX_LINES_PER_PAGE;
             i < (pageIndex + 1) * MAX_LINES_PER_PAGE && i < lines.size(); i++) {
            graphics.setFont(new Font("Calibri", Font.BOLD, 10));
            graphics.drawString(lines.get(i), X_OFFSET, space);
            if (pricelines[i] != null) {
                //Print out price column
                graphics.setFont(new Font("Calibri", Font.PLAIN, 10));
                graphics.drawString("" + pricelines[i], PRICE_X_OFFSET, space);
            }
            space += LINE_SPACING;
        }
        space = STUB_SPACING;
        for (int i = pageIndex * MAX_LINES_PER_PAGE;
             i < (pageIndex + 1) * MAX_LINES_PER_PAGE && i < lines.size(); i++) {
            graphics.drawString(lines.get(i), X_OFFSET, space);
            if (pricelines[i] != null) {
                //Print out price column
                graphics.drawString("" + pricelines[i], PRICE_X_OFFSET, space);
            }
            space += LINE_SPACING;
        }
        //Print out the GST, PST, SUB, etc
        drawPrice(graphics, 320);
        drawPrice(graphics, 675);

        //TODO Delivery fee  - contoller.isDelivery()

        //Print out Header with store info, gst info, etc
        graphics.setFont(new Font("Calibri", Font.PLAIN, 12));
        graphics.drawString("805 HENDERSON HWY (204) 222-0222", X_OFFSET + 20, HEADER_OFFSET);
        graphics.drawString("805 HENDERSON HWY (204) 222-0222", X_OFFSET + 20, HEADER2_OFFSET);
        DateFormat dateFormat = new SimpleDateFormat("hh:mm aa dd/MM/yyyy");
        String dateString = dateFormat.format(new Date()).toString();
        graphics.drawString(dateString + " GST #104718986 RT", X_OFFSET, HEADER_OFFSET + LINE_SPACING);
        graphics.drawString(dateString + " GST #104718986 RT", X_OFFSET, HEADER2_OFFSET + LINE_SPACING);

        //Print out pickup/delivery info
        if (this.isDelivery()) {
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.setColor(Color.lightGray);
            g2d.fillRect(X_OFFSET - 5, HEADER2_OFFSET + (LINE_SPACING + 10), 265, 16);
            g2d.fillRect(X_OFFSET - 5, HEADER_OFFSET + (LINE_SPACING + 10), 265, 16);
            graphics.setColor(Color.black);
            graphics.setFont(new Font("Calibri", Font.BOLD, 12));
            graphics.drawString("Address: " + this.getCustomerDetails().getAddress().toString(), X_OFFSET,
                    HEADER_OFFSET + (LINE_SPACING * 2) + 6);
            graphics.drawString("Address: " + this.getCustomerDetails().getAddress().toString(), X_OFFSET,
                    HEADER2_OFFSET + (LINE_SPACING * 2) + 6);
            graphics.setFont(new Font("Calibri", Font.PLAIN, 12));
        } else {

            Graphics2D g2d = (Graphics2D) graphics;
            g2d.setColor(Color.lightGray);
            g2d.fillRect(X_OFFSET - 5, HEADER2_OFFSET + (LINE_SPACING + 10), 265, 16);
            g2d.fillRect(X_OFFSET - 5, HEADER_OFFSET + (LINE_SPACING + 10), 265, 16);
            graphics.setColor(Color.black);
            graphics.drawString("PICKUP: " + getCustomerDetails().getName(), X_OFFSET,
                    HEADER_OFFSET + (LINE_SPACING * 2) + 6);
            graphics.drawString("PICKUP: " + getCustomerDetails().getName(), X_OFFSET,
                    HEADER2_OFFSET + (LINE_SPACING * 2) + 6);
        }

        //Print Phone Number, Order Number, and payment type
        if(this.getPayment() != null){
            graphics.drawString("Phone: " + getCustomerDetails().getPhoneNumberFormatted() + " Order #" +
                            this.getOrderNumber() + " Pay: " + this.getPayment().getPaymentType().getDisplayCode(), X_OFFSET,
                    HEADER_OFFSET + (LINE_SPACING * 3) + 6);
            graphics.drawString("Phone: " + getCustomerDetails().getPhoneNumberFormatted() + " Order #" +
                            this.getOrderNumber() + " Pay: " + this.getPayment().getPaymentType().getDisplayCode(), X_OFFSET,
                    HEADER2_OFFSET + (LINE_SPACING * 3) + 6);
        }else{
            graphics.drawString("Phone: " + getCustomerDetails().getPhoneNumberFormatted() + " Order #" +
                            this.getOrderNumber() + " Pay: "/*TODO Implement pay type*/, X_OFFSET,
                    HEADER_OFFSET + (LINE_SPACING * 3) + 6);
            graphics.drawString("Phone: " + getCustomerDetails().getPhoneNumberFormatted() + " Order #" +
                            this.getOrderNumber() + " Pay: "/*TODO Implement pay type*/, X_OFFSET,
                    HEADER2_OFFSET + (LINE_SPACING * 3) + 6);
        }


        //TODO print special requests (cook/driver)

        return PAGE_EXISTS;
    }

    private void drawPrice(Graphics g, int start) {
        g.drawString("SUB  $" + this.getGrandTotalTax()
                        .subtract(this.getGrandTotalGST()).subtract(this.getGrandTotalPST()), PRICE_X_OFFSET - 50,
                start);
        g.drawString("GST  $" + this.getGrandTotalGST(), PRICE_X_OFFSET - 50, start + LINE_SPACING);
        g.drawString("PST  $" + this.getGrandTotalPST(), PRICE_X_OFFSET - 50,
                start + (LINE_SPACING * 2));
        g.drawString("TOT  $" + ((formatter.format(CurrencyConverter.taxAndBuild(this.getCost())))),
                PRICE_X_OFFSET - 50, start + (LINE_SPACING * 3));
    }

    /*
     * Prepare the order for printing
     * Write the proper lines, etc
     */
    private void preparePrint() {
        this.calculateCost();
        lines = new ArrayList<>();
        // Divide order string into chunks that will fit the page
        // Span the order over multiple rows if necessary
        for (Item i : this.getItems()) {

            divide(i.getName());
        }
        pricelines = new BigDecimal[lines.size()];
        int index = 0;
        for (Item i : this.getItems()) {
            pricelines[index] = CurrencyConverter.build(i.getDiscountedPrice());
            index += skipLines(i);
            index++;
        }
    }

    /**
     * Divide string up depending on MAX_CHARS_PER_LINES
     */
    private void divide(String s) {
        if (s.length() < MAX_CHARS_PER_LINE) {
            lines.add(s);
            return;
        }
        int lastSpace = s.lastIndexOf(' ', MAX_CHARS_PER_LINE);
        lines.add(s.substring(0, lastSpace));
        divide(s.substring(lastSpace + 1));
    }

    /**
     * Calculates how many lines to skip (set to null) in pricelines to have
     * the prices line up with the first line of the order
     */
    private int skipLines(Item i) {
        return (int) Math.floor(i.getName().length() / (double) MAX_CHARS_PER_LINE);
    }

    public boolean isReprint() {
        return reprint;
    }

    public void setReprint(boolean reprint) {
        this.reprint = reprint;
    }
}
