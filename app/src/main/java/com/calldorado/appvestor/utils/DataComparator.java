package com.calldorado.appvestor.utils;

import java.util.Comparator;
import java.util.Date;

public class DataComparator implements Comparator<Date> {
    @Override
    public int compare(Date o1, Date o2) {
        return o1.compareTo(o2);
    }
}
