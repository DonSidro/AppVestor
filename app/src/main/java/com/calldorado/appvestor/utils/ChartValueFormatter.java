package com.calldorado.appvestor.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChartValueFormatter extends ValueFormatter {



    @Override
    public String getFormattedValue(float value) {
        return new DecimalFormat("##.##").format(value) + "€";
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {

        return super.getAxisLabel(value, axis);
    }

    @Override
    public String getBarLabel(BarEntry barEntry) {
        return super.getBarLabel(barEntry);
    }


}
