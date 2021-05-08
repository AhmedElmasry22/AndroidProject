package com.example.shopaplication.Models;

public class Products {
    String data,descreption,name,image,pid,price,product_name,time,producte_state;
    public Products(){

    }

    public Products(String data, String descreption, String name, String image, String pid, String price, String product_name, String time, String producte_state) {
        this.data = data;
        this.descreption = descreption;
        this.name = name;
        this.image = image;
        this.pid = pid;
        this.price = price;
        this.product_name = product_name;
        this.time = time;
        this.producte_state = producte_state;
    }

    public String getProducte_state() {
        return producte_state;
    }

    public void setProducte_state(String producte_state) {
        this.producte_state = producte_state;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public String getDescreption() {
        return descreption;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getPid() {
        return pid;
    }

    public String getPrice() {
        return price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getTime() {
        return time;
    }
}
