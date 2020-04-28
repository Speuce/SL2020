package main.java.lucia.client.content.order.impl;

/**
 * @author Brett Downey
 * @author Matt Kwiatkowski
 */
public class Address {


  private String addressNumber;

  private String buzzCode;

  private String apartmentNumber;

  private boolean isApartment;

  private String street, number;

  private String longitude;

  private String latitude;

  public Address(String name, String latitude, String longitude) {
    this.number = name.substring(0, name.indexOf(" "));
    this.street = name.substring(name.indexOf(" "));
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Address(String streetName, String addressNumber, String apartmentNumber, String buzzCode) {
    this.street = streetName;
    this.addressNumber = addressNumber;
    this.buzzCode = buzzCode;
    this.apartmentNumber = apartmentNumber;
    this.isApartment = !buzzCode.equals("") && !apartmentNumber.equals("");
  }

  public String getAddressNumber() {
    return addressNumber;
  }

  public String getBuzzCode() {
    return buzzCode;
  }

  public String getApartmentNumber() {
    return apartmentNumber;
  }

  public boolean isApartment() {
    return isApartment;
  }

  public void setAddressNumber(String addressNumber) {
    this.addressNumber = addressNumber;
  }

  public void setBuzzCode(String buzzCode) {
    this.buzzCode = buzzCode;
  }

  public void setApartmentNumber(String apartmentNumber) {
    this.apartmentNumber = apartmentNumber;
  }

  public void setApartment(boolean apartment) {
    isApartment = apartment;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getName(){
    return this.getAddressNumber() + " " + this.getStreet();
  }

  @Override
  public String toString(){
    if(this.isApartment){
      return this.getBuzzCode() + "-" + this.getApartmentNumber() +"-" +getName();
    }else{
      return getName();
    }
  }
}