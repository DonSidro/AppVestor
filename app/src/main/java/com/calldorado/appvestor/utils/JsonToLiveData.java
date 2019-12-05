package com.calldorado.appvestor.utils;

import android.graphics.Color;

import com.calldorado.appvestor.data.db.entity.Investments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JsonToLiveData {


    private final static List<Investments> Data = new ArrayList();
    private static ArrayList<Integer> colors = new ArrayList<>();


    public JsonToLiveData() {
    }

    public static List<Investments> JsonToLiveData(String JsonResponse){


        try {
            JSONArray jsonArray = new JSONArray(JsonResponse);

            for (int i = 0; i < jsonArray.length() ; i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String strColor = String.format("#%06X", 0xFFFFFF & getRandomColor());

                Data.add(new Investments(jsonObject.getString("id"),
                        jsonObject.getString("created"),
                        jsonObject.getString("state"),
                        jsonObject.getString("application-id"),
                        jsonObject.getString("application-name"),
                        jsonObject.getString("application-package"),
                        jsonObject.getString("wallet-id"),
                        jsonObject.getString("amount-invested"),
                        jsonObject.getString("amount-activated"),
                        jsonObject.getString("amount-used"),
                        jsonObject.getString("amount-earned"),
                        jsonObject.getString("roi-after-day"),
                        strColor));
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        return Data;

    }

    public static int getRandomColor(){
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        if(colors.size() > 0) {
            for (Integer integer : colors) {
                if(!integer.equals(color) && !integer.equals(android.R.color.black)){
                    colors.add(color);
                    return color;
                }
            }
            return color;
        }else{
            colors.add(color);
            return color;
        }
    }
}
