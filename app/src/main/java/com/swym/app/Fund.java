package com.swym.app;

/**
 * Created by Arjun on 8/4/2014.
 */
public class Fund implements Datum{
    private String name;
    private double cost;
    private String desc;
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
}
