package com.java.hostel_management;

public class ModelRoom {
    private int id;
    private String name;
    private int status;
    private String type;

    private double price;
//    public ModelRoom(){}

    public ModelRoom(int id, String n,  int status, String type,  double p){
        this.id = id;
        this.name = n;
        this.type = type;
        this.status = status;
        this.price = p;
    }

    // Getter and setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for type
    public String getType() {
        return type;
    }

    public int getStatus(){return status;}

    public void setStatus(int s){
        this.status = s;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Getter and setter for price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

