package com.swym.app.data;

public class Withdrawal implements Transaction {
    private String name;
    private double cost;
    private String description;
    private String tag;
    private long id;
    private int date;
    private String realDate;
    private TransactionType type;

    public Withdrawal(){}
    public Withdrawal(String name, double cost, String desc, int date, TransactionType type, String realDate){
        this.name = name;
        this.cost = cost;
        this.description = desc;
        this.date = date;
        this.type = type;
        this.realDate = realDate;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setCost(double cost){
        this.cost = cost;
    }
    public double getCost(){
        return cost;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }
    public void setTag(String tag){
        this.tag = tag;
    }
    public String getTag(){
        return tag;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
    public void setDate(int date) {
        this.date = date;
    }
    public int getDate() {
        return date;
    }
    public void setRealDate(String realDate){
        this.realDate = realDate;
    }
    public String getRealDate(){
        return realDate;
    }
    public TransactionType getType(){return type;}
}
