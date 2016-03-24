package com.swym.app.data;

import java.io.Serializable;
import java.util.Date;

public interface Transaction extends Serializable {
    void setName(String name);
    void setCost(double cost);
    void setDescription(String description);
    String getName();
    double getCost();
    String getDescription();
    void setId(long id);
    long getId();
    void setDate(int date);
    int getDate();
    void setRealDate(String realDate);
    String getRealDate();
}

