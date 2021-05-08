package com.example.shopaplication.Models;

public class ModelOrder {
    String address,city,name,number,state;

    public ModelOrder(){

    }

    public ModelOrder(String address, String city, String name, String number, String state) {
        this.address = address;
        this.city = city;
        this.name = name;
        this.number = number;
        this.state = state;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getState() {
        return state;
    }
}
