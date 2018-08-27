package com.example.shared_parking.roomdatabase;

import android.arch.persistence.room.ColumnInfo;

public class Address {

    public Address(int postCode, String city, String street, int number){
        this.postCode = postCode;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    @ColumnInfo(name = "post_code")
    private int postCode;

    private String city;
    private String street;
    private int number;

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
