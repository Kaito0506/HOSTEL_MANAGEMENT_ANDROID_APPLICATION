package com.java.hostel_management;

public class ModelService {
    private int id;
    private String name;
    private Double price;

    public ModelService(int i, String n, Double p){
        this.id = i;
        this.name = n;
        this.price = p;
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Setter for id
    public void setId(int id) {
        this.id = id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for price
    public Double getPrice() {
        return price;
    }

    // Setter for price
    public void setPrice(Double price) {
        this.price = price;
    }

}
