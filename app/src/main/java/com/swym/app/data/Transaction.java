package com.swym.app.data;

import java.io.Serializable;

public interface Transaction extends Serializable {
    String getName();

    void setName(String name);

    double getCost();

    void setCost(double cost);

    String getDescription();

    void setDescription(String description);

    long getId();

    void setId(long id);

    int getDate();

    void setDate(int date);

    String getRealDate();

    void setRealDate(String realDate);
}

