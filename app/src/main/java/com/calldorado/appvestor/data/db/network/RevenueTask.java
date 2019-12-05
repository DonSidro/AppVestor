package com.calldorado.appvestor.data.db.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.calldorado.appvestor.R;
import com.calldorado.appvestor.activities.ui.investment.GraphItemViewModel;
import com.calldorado.appvestor.activities.ui.investment.interfaces.RevenueInterface;
import com.calldorado.appvestor.utils.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RevenueTask {

    private final String TAG = RevenueTask.class.getSimpleName();

    private final Context context;
    private String startDate,endDate;
    SimpleDateFormat simpleDate =  new SimpleDateFormat("yyyyMMdd");

    /**
     * Constructor
     * @param context
     */
    public RevenueTask(final Context context) {
        this.context = context;
    }

    public void RevenueRequest(final String userID, RevenueInterface revenueInterface){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        endDate = simpleDate.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        startDate = simpleDate.format(calendar.getTime());
        Log.d(TAG, "doInBackground: end " + endDate);
        Log.d(TAG, "doInBackground: start " + startDate);

        String url = "https://staging-my.appvestor.com/api/getrevenue?userid="+ userID+"&startdate="+startDate+"&enddate="+endDate;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        Log.d(TAG, "RevenueRequest: " + new JSONArray(response).length());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    revenueInterface.onDataDone(JsonUtil.readInvestmentResponse1(context,response,startDate,endDate));


                },


                error -> Log.d(TAG, "onErrorResponse: " + error.getMessage())){

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", context.getResources().getString(R.string.token));
                return params;
            }

        };

        // Adding JsonObject request to request queue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);


        // Adding JsonObject request to request queue



    }
}
