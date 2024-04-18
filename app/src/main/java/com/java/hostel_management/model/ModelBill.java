package com.java.hostel_management.model;

import android.graphics.ColorSpace;

public class ModelBill {
    private int id;
    private String cus_name;
    private int room_id;
    private String checkIn;
    private String checkOut;
    private Double total;
    private int isPaid ;
    public ModelBill(){}
    public ModelBill(int id, int room_id, String cus_name, String checkIn, String checkOut, Double total, int status){
        this.id = id;
        this.checkIn = checkIn;
        if(checkOut!=null){
            this.checkOut = checkOut;
        }

        this.cus_name = cus_name;
        this.room_id = room_id;
        this.isPaid = status;
        this.total = total;
    }



    public void setRoomId(int id) {
        this.room_id = id;
    }
    public int getRoomId(){
        return this.room_id;
    }

    public void setStatus(int id) {
        this.isPaid = id;
    }
    public int getStatus(){
        return this.isPaid;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getCus_name() {
        return cus_name;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public Double getTotal() {
        return total;
    }
}
