package com.calldorado.appvestor.data.models;

import java.io.Serializable;
import java.util.Date;


/**
 * Datapoint object class used for save instance of graph data used to display information on view
 */

public class DataPoints implements Serializable {

    private double x;
    private double y;

    public DataPoints(double x, double y) {
        this.x=x;
        this.y=y;
    }

    public DataPoints(Date x, double y) {
        this.x = x.getTime();
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "["+x+"/"+y+"]";
    }


}
