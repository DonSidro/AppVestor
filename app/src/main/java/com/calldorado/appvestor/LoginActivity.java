package com.calldorado.appvestor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import android.app.Activity;
import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.calldorado.appvestor.interfaces.LoginInterface;
import com.calldorado.appvestor.utils.JsonErrorCodes;
import com.calldorado.appvestor.utils.JsonRequestResponse;
import com.calldorado.appvestor.data.db.network.LoginTask;
import com.calldorado.appvestor.utils.MainThreadExecutor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static androidx.biometric.BiometricConstants.ERROR_NEGATIVE_BUTTON;


public class LoginActivity extends AppCompatActivity implements LoginInterface {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    int mode = Activity.MODE_PRIVATE;


    private EditText mEditTextEmail;
    private EditText mEditTextPassword;

    private CheckBox mRememberMe;

    private AppCompatButton mButtonLogin;

    private TextView mTextViewForgotPassword;

    private LoginInterface mLoginInterface;

    private boolean mLoginState;
    private boolean mLoginBiometric;


    private Context mContext;

    SharedPreferences mSharedPreferences;

    private Executor executor = new MainThreadExecutor();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.d(TAG, "onComplete: " + token);
                        // Log and toast
                        //Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        nuke();
        mSharedPreferences = getSharedPreferences("UserLoginState", mode);

        mLoginState = mSharedPreferences.getBoolean("LoginState",false);
        mLoginBiometric = mSharedPreferences.getBoolean("USE_BIOMETRIC",false);
        Log.d(TAG, "onCreate: " + mLoginBiometric);
        mContext = this;
        mLoginInterface = this;


        if(mLoginState){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }else if (mSharedPreferences.getString("AccountId",null) != null && mLoginBiometric){
            showBiometricPrompt();
        }
        setUpView();
        setUpClickListeners();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }


    private void setUpView(){

        mEditTextEmail = findViewById(R.id.input_email);
        mEditTextPassword = findViewById(R.id.input_password);

        mButtonLogin = findViewById(R.id.btn_login);

        mRememberMe = findViewById(R.id.checkBox);

        mTextViewForgotPassword = findViewById(R.id.link_forget);
    }

    private void setUpClickListeners(){
        mButtonLogin.setOnClickListener(view -> {
            if (!validate()) {
                return;
            }else{
                new LoginTask(mContext).LoginRequest(mEditTextEmail.getText().toString(), mEditTextPassword.getText().toString(), mLoginInterface);

            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String email = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();


        if (mEditTextEmail.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEditTextEmail.setError("enter a valid email address");
            valid = false;
        } else {
            mEditTextEmail.setError(null);
        }

        if (mEditTextPassword.getText().toString().isEmpty() || password.length() < 4 || password.length() > 1000) {
            mEditTextPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mEditTextPassword.setError(null);
        }

        return valid;
    }


    @Override
    public void onLoginSucess(JSONObject jsonObject) {
        Log.d(TAG, "onLoginSucess: ");

        try {
            mSharedPreferences
                    .edit()
                    .putString("Id", jsonObject.get("Id").toString())
                    .putString("AccountId", jsonObject.get("AccountId").toString())
                    .commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(mRememberMe.isChecked()) {
            mSharedPreferences.edit().putBoolean("LoginState", true).commit();
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onLoginFailed(String error)
    {
        JsonRequestResponse jsonRequestResponse = JsonErrorCodes.checkForPostRequest(Integer.parseInt(error));

        jsonRequestResponse.handleresponseDialog(this,jsonRequestResponse.getReponse());


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().getExtras() != null){
            Set<String> keys = getIntent().getExtras().keySet();
            for (String key : keys) {
                Log.d(TAG, "Bundle Contains: key=" + key);
                if(key.equals("click_action")){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getExtras().getString("click_action")));
                    getIntent().removeExtra("click_action");
                    startActivity(browserIntent);
                    //finish();
                }
            }
        }
    }

    public static void nuke() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }


    private void showBiometricPrompt() {
        BiometricPrompt.PromptInfo promptInfo =
                new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Biometric login")
                        .setSubtitle("Log in using your biometric credential")
                        .setNegativeButtonText("Cancel")
                        .setConfirmationRequired(false)
                        .build();

        BiometricPrompt biometricPrompt = new BiometricPrompt(LoginActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                switch (errorCode) {
                    case BiometricManager.BIOMETRIC_SUCCESS:
                        break;
                    case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                        if(mLoginState){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                        break;
                }
                Toast.makeText(getApplicationContext(), errString,
                        Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                BiometricPrompt.CryptoObject authenticatedCryptoObject =
                        result.getCryptoObject();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                // User has verified the signature, cipher, or message
                // authentication code (MAC) associated with the crypto object,
                // so you can use it in your app's crypto-driven workflows.

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        // Displays the "log in" prompt.
        biometricPrompt.authenticate(promptInfo);
    }

}
