package com.example.sqlitedemo;

public class Spot {
    private int id;
    private String name,web,phone,address;
    private byte[] image;

    public Spot(String name,String web,String phone,String address,byte[] image){
        this(0,name,web,phone,address,image);
    }
    public Spot(int id,String name,String web,String phone,String address,byte[] image){
        this.id=id;
        this.name=name;
        this.web=web;
        this.phone=phone;
        this.address=address;
        this.image=image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
