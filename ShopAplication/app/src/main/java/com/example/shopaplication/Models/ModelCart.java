package com.example.shopaplication.Models;

public class ModelCart {
    String price, pname,data,pid,discount,id_product;


    public ModelCart(){

    }

    public ModelCart(String price, String pname, String data, String pid, String discount, String id_product) {
        this.price = price;
        this.pname = pname;
        this.data = data;
        this.pid = pid;
        this.discount = discount;
        this.id_product = id_product;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getPrice() {
        return price;
    }

    public String getPname() {
        return pname;
    }

    public String getData() {
        return data;
    }

    public String getPid() {
        return pid;
    }

    public String getDiscount() {
        return discount;
    }

    public String getId_product() {
        return id_product;
    }
}
