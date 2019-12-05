package com.calldorado.appvestor.data.db.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.calldorado.appvestor.R;
import com.calldorado.appvestor.activities.ui.investment.interfaces.WalletInterface;

import java.util.HashMap;
import java.util.Map;

public class WalletTask {

    private final String TAG = WalletTask.class.getSimpleName();

    private final Context context;

    /**
     * Constructor
     * @param context
     */
    public WalletTask(final Context context) {
        this.context = context;
    }

    public void WalletRequest(final String userID, WalletInterface walletInterface){


        String url = "https://staging-my.appvestor.com/api/getwallet?userid="+ userID;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                        Log.d(TAG, "onResponse: " + response);


                    walletInterface.onDataDone(response);


                },


                error -> {
                    Log.d(TAG, "onErrorResponse: " + error.getMessage());
                    walletInterface.onFailed(error.getMessage());
                } ){

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
