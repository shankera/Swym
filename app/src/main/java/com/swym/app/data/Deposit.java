package com.swym.app.data;

public class Deposit implements Transaction {
    private String name;
    private double cost;
    private String desc;
    private long id;
    private int date;
    private TransactionType type;
    private String realDate;

    public Deposit() {
    }

    public Deposit(String name, double cost, String desc, int date, TransactionType type, String realDate) {
        this.name = name;
        this.cost = cost;
        this.desc = desc;
        this.date = date;
        this.type = type;
        this.realDate = realDate;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public void setDescription(String description) {
        this.desc = description;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int getDate() {
        return date;
    }

    @Override
    public void setDate(int date) {
        this.date = date;
    }

    public String getRealDate() {
        return realDate;
    }

    public void setRealDate(String realDate) {
        this.realDate = realDate;
    }

    public TransactionType getType() {
        return type;
    }
}
