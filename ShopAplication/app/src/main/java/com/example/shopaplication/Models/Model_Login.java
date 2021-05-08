package com.example.shopaplication.Models;

public class Model_Login {
    String name;
    String numper;
    String pass;
    String imageurl;
    String address;

    public Model_Login(String name, String numper, String pass, String imageurl, String address) {
        this.name = name;
        this.numper = numper;
        this.pass = pass;
        this.imageurl = imageurl;
        this.address = address;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getAddress() {
        return address;
    }

    public Model_Login() {
    }



    public String getName() {
        return name;
    }

    public String getNumper() {
        return numper;
    }

    public String getPass() {
        return pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumper(String numper) {
        this.numper = numper;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}