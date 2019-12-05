package com.calldorado.appvestor.activities.ui.account;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.calldorado.appvestor.R;
import com.calldorado.appvestor.activities.ui.investment.interfaces.WalletInterface;
import com.calldorado.appvestor.data.db.network.UserDetailsTask;
import com.calldorado.appvestor.data.db.network.UserTask;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import org.json.JSONObject;


public class AccountSettingsFragment extends Fragment {

    private static final String TAG = "AccountSettingsFragment";

    private AccountSettingsViewModel homeViewModel;

    private TextInputEditText mCompany;
    private TextInputEditText mVAT;
    private TextInputEditText mFirstName;
    private TextInputEditText mLastName;
    private TextInputEditText mAddress;
    private TextInputEditText mCity;
    private TextInputEditText mZip;
    private TextInputEditText mPhone;
    private TextInputEditText mEmail;

    String json;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(AccountSettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account_settings, container, false);


        mCompany = root.findViewById(R.id.input_company);
        mVAT = root.findViewById(R.id.input_vat);
        mFirstName = root.findViewById(R.id.input_first_name);
        mLastName = root.findViewById(R.id.input_last_name);
        mAddress = root.findViewById(R.id.input_address);
        mCity = root.findViewById(R.id.input_city);
        mZip = root.findViewById(R.id.input_zip);
        mPhone = root.findViewById(R.id.input_phone_number);
        mEmail = root.findViewById(R.id.input_email_);


        json = getContext().getSharedPreferences("UserLoginState", Context.MODE_PRIVATE).getString("USER_INFO","");

        if(json.isEmpty()){
            new UserDetailsTask(getContext()).WalletRequest(getContext().getSharedPreferences("UserLoginState", Context.MODE_PRIVATE).getString("Id", ""), new WalletInterface() {
                @Override
                public void onDataDone(String response) {
                    getContext().getSharedPreferences("UserLoginState", Context.MODE_PRIVATE).edit().putString("USER_INFO", response).commit();
                    json = response;
                    JSONObject obj = null;
                    try {

                        obj = new JSONObject(json);
                        //Log.d(TAG, "onCreateView: " + obj);
                        mCompany.setText(obj.getString("Company"));
                        mVAT.setText(obj.getString("Vat"));
                        mFirstName.setText(obj.getString("Firstname"));
                        mLastName.setText(obj.getString("Surname"));
                        mAddress.setText(obj.getString("Address"));
                        mCity.setText(obj.getString("City"));
                        mZip.setText(obj.getString("Zipcode"));
                        mPhone.setText(obj.getString("Phone"));
                        mEmail.setText(obj.getString("Email"));

                    } catch (Throwable t) {
                    }

                }

                @Override
                public void onFailed(String response) {

                }
            });
        }else{
            JSONObject obj = null;
            try {

                obj = new JSONObject(json);
                //Log.d(TAG, "onCreateView: " + obj);
                mCompany.setText(obj.getString("Company"));
                mVAT.setText(obj.getString("Vat"));
                mFirstName.setText(obj.getString("Firstname"));
                mLastName.setText(obj.getString("Surname"));
                mAddress.setText(obj.getString("Address"));
                mCity.setText(obj.getString("City"));
                mZip.setText(obj.getString("Zipcode"));
                mPhone.setText(obj.getString("Phone"));
                mEmail.setText(obj.getString("Email"));

            } catch (Throwable t) {
            }

        }



        return root;
    }
}