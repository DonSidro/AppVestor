package com.calldorado.appvestor.utils;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class ChartValueFomatter2 extends ValueFormatter {
    private String[] mValues;


    public ChartValueFomatter2(String[] values) {
        this.mValues = values; }


    @Override
    public String getFormattedValue(float value) {
        // "value" represents the position of the label on the axis (x or y)
        int index = Math.round(value);

        if(index >= mValues.length){
            index = mValues.length-1;
        }
        return mValues[index];
    }
}

