package com.calldorado.appvestor.interfaces;

import org.json.JSONObject;

public interface LoginInterface {

    void onLoginSucess(JSONObject jsonObject);
    void onLoginFailed(String error);
}
