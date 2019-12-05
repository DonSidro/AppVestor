package com.calldorado.appvestor.data.db.network;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.calldorado.appvestor.R;
import com.calldorado.appvestor.activities.ui.investment.interfaces.InvestmentInterface;
import com.calldorado.appvestor.utils.JsonToLiveData;


import java.util.HashMap;
import java.util.Map;

public class InvestmentTask {

    private final String TAG = InvestmentTask.class.getSimpleName();

    private final Context context;

    /**
     * Constructor
     * @param context
     */
    public InvestmentTask(final Context context) {
        this.context = context;
    }

    public void InvestmentRequest(final String userID, InvestmentInterface investmentInterface){



        String url = "https://staging-my.appvestor.com/api/getinvestments?userid="+ userID;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                        Log.d(TAG, "onResponse: " + response);


                    investmentInterface.onDataDone(JsonToLiveData.JsonToLiveData(response));


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
