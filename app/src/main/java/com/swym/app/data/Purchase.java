package com.swym.app.data;

import java.util.Date;

/**
 * Created by Arjun Shanker on 7/19/14.
 */
public class Purchase implements Transaction {
    private String name;
    private double cost;
    private String description;
    private String tag;
    private long id;
    private int date;
    private String realDate;

    public void setName(String name){
        this.name = name;
    }
    public void setCost(double cost){
        this.cost = cost;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setTag(String tag){
        this.tag = tag;
    }
    public String getName(){
        return name;
    }
    public double getCost(){
        return cost;
    }
    public String getDescription(){
        return description;
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

    public String getTag(){
        return tag;
    }
    public void setRealDate(String realDate){
        this.realDate = realDate;
    }
    public String getRealDate(){
        return realDate;
    }
}
