package com.swym.app;

import java.io.Serializable;

/**
 * Created by Arjun Shanker on 7/19/14.
 */
public class Purchase implements Datum{
    private String name;
    private double cost;
    private String description;
    private String tag;

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
    public String getTag(){
        return tag;
    }
}
