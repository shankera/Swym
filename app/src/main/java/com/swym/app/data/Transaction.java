package com.swym.app.data;

import java.io.Serializable;

/**
 * Created by Arjun on 8/4/2014.
 */
public interface Transaction extends Serializable {

    public void setName(String name);
    public void setCost(double cost);
    public void setDescription(String description);
    public String getName();
    public double getCost();
    public String getDescription();
    public void setId(long id);
    public long getId();
}
