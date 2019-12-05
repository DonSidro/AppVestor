package com.calldorado.appvestor.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class JsonRequestResponse {

    private int code;
    private String reponse;

    public JsonRequestResponse(int code, String reponse){
        this.code = code;
        this.reponse = reponse;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public void handleresponseDialog(Context context, String s){

        new AlertDialog.Builder(context)
                .setTitle("Ops something went wrong")
                .setMessage(s)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();

    }
}
