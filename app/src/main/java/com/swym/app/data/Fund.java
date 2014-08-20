package com.swym.app.data;

import java.util.Date;

/**
 * Created by Arjun on 8/4/2014.
 */
public class Fund implements Transaction {
    private String name;
    private double cost;
    private String desc;
    private long id;
    private int date;
    private String realDate;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public void setDescription(String description) {
        this.desc = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public int getDate() {
        return date;
    }
    public void setRealDate(String realDate){
        this.realDate = realDate;
    }
    public String getRealDate(){
        return realDate;
    }
}
