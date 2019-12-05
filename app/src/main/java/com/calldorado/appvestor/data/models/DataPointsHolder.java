package com.calldorado.appvestor.data.models;


import java.io.Serializable;

/**
 * DataPointsHolder object class holding array of DataPoints used to generate graph
 */

public class DataPointsHolder implements Serializable {
    private DataPoints [] dataPoints;
    private String color;

    public DataPointsHolder() {
    }

    public DataPoints[] getDataPoints() {
        return dataPoints;
    }


    public void setDataPoints(DataPoints[] dataPoints) {
        this.dataPoints = dataPoints;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
