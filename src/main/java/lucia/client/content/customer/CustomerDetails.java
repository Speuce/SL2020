package main.java.lucia.client.content.customer;

import main.java.lucia.client.content.order.impl.Address;

/**
 * The customer details of the origin of this order,
 * the server will then merge these details with other
 * details of the same origin.
 *
 * @author Brett Downey
 * @author Zachery Unrau
 * @author Matt Kwiatkowski
 */
public class CustomerDetails {

  /**
   * The specific id of this pizza on the server.
   * DO NOT SET. Let Gson do that.
   */
  private int rowNum = -1;

  private String name;

  private String phoneNumber;

  private Address address;

  private String deliveryArea;

  public CustomerDetails(String name, String phoneNumber, Address address) {
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }

  public CustomerDetails(String name, String phoneNumber) {
    this.name = name;
    this.phoneNumber = phoneNumber;
  }

  public CustomerDetails(String name) {
    this.name = name;
  }

  public CustomerDetails() {
  }

  public String getName() {
    return name;
  }

  /**
   *
   * @return {@String} representing the formatted phone number of the customer
   */
  public String getPhoneNumberFormatted(){
    if(this.phoneNumber.length() == 7){
      return phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3);
    }else if(phoneNumber.length()==10){
      return "(" + phoneNumber.substring(0,3) + ")" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6);
    }else{
      return phoneNumber;
    }
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public Address getAddress() {
    return address;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public void setDeliveryArea(String string) {
    this.deliveryArea = string;
  }

  public String getDeliveryArea() {
    return this.deliveryArea;
  }

  public int getRowNum(){
    return rowNum;
   }

  public String getNameDelivery() {
    return String.valueOf(address);
  }

  public String getNamePickup() {
    return name;
  }
}