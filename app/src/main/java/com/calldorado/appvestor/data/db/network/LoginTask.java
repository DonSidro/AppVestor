package com.calldorado.appvestor.data.db.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.calldorado.appvestor.R;
import com.calldorado.appvestor.interfaces.LoginInterface;
import com.calldorado.appvestor.utils.BitShifter;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class LoginTask {

    private final String TAG = LoginTask.class.getSimpleName();

    private final Context context;
    ProgressDialog progressDialog;

    /**
     * Constructor
     * @param context
     */
    public LoginTask(final Context context) {
        this.context = context;
    }

    public void LoginRequest(final String email, final String password , final LoginInterface loginInterface){

        byte[] encodeValue = Base64.encode(password.getBytes(), Base64.DEFAULT);
        // create from array
        BigInteger bigInt = new BigInteger(encodeValue);

        // shift
        //BigInteger shiftInt = bigInt.shiftLeft(4);


        String url = "https://staging-my.appvestor.com/api/login?email="+email+"&password="+password;
        progressDialog = new ProgressDialog(context,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    //Log.d(TAG, "onResponse: " + response);
                    progressDialog.dismiss();

                    try {
                        JSONObject obj = new JSONObject(response);
                        boolean status = obj.getBoolean("Status");
                        JSONObject data = obj.getJSONObject("Data");


                        if(status){
                            loginInterface.onLoginSucess(data);

                        }else {

                            loginInterface.onLoginFailed(obj.get("Error").toString());
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                        if(error != null) {
                            if(error.networkResponse != null){
                                Log.d(TAG, "onErrorResponse: " + error.networkResponse.statusCode);
                                loginInterface.onLoginFailed(String.valueOf(error.networkResponse.statusCode));
                            }else{
                                Log.d(TAG, "LoginRequest: " + error.getMessage());
                                loginInterface.onLoginFailed("999");

                            }
                        }
                    progressDialog.dismiss();
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", context.getResources().getString(R.string.token));
                return params;
            }
        };



        // Adding JsonObject request to request queue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);



    }
}
