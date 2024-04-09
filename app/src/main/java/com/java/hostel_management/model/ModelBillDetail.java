package com.java.hostel_management.model;

public class ModelBillDetail {
    private int id;
    private int bill_id;
    private int service_id;
    private int quantity;

    // Constructor
    public ModelBillDetail() {
        // Default constructor
    }

    public ModelBillDetail(int id, int bi, int si, int q) {
        this.id = id;
        this.bill_id = bi;
        this.service_id = si;
        this.quantity = q;
    }

    // Getter and setter methods for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and setter methods for bill_id
    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    // Getter and setter methods for service_id
    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    // Getter and setter methods for quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

