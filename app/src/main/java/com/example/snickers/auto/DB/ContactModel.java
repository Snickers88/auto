package com.example.snickers.auto.DB;

import java.io.Serializable;

public class ContactModel implements Serializable {

    private int _id;
    private String date;
    private int distance;
    private double volume;
    private double price;
    private double together;
    private String type;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTogether() {
        return together;
    }

    public void setTogether(double together) {
        this.together = together;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
