package com.calldorado.appvestor.utils;

import android.content.Context;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class TimerUtil {

    public static boolean hasTimePassed(Context context){
        boolean timer;
        Calendar calendar = Calendar.getInstance();
        long timestamp = context.getSharedPreferences("hasTimePassed", MODE_PRIVATE).getLong("time", 0);

        if(timestamp == 0){
            timer =  true;
        }else{
            long diff =  calendar.getTimeInMillis() -  timestamp;
            long mins = (diff / 60000);
            long hours = (mins/60);
            //todo limit should to be server controlled
            if (hours >= 12) {
                timer =  true;
            }else{
                timer =  false;

            }
        }
        return timer;
    }
}
