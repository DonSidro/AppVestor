package com.calldorado.appvestor.activities.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import com.calldorado.appvestor.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    private static final String TAG = "SettingsFragment";
    SharedPreferences mSharedPreferences;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference_screen, rootKey);
         mSharedPreferences = getActivity().getSharedPreferences("UserLoginState", Context.MODE_PRIVATE);
        ((SwitchPreferenceCompat) getPreferenceScreen().findPreference("biometric")).setChecked(mSharedPreferences.getBoolean("USE_BIOMETRIC", false));

        ((SwitchPreferenceCompat) getPreferenceScreen().findPreference("biometric")).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.d(TAG, "onPreferenceChange: " + newValue);
                mSharedPreferences.edit().putBoolean("USE_BIOMETRIC", (Boolean) newValue).commit();
                return true;
            }
        });

    }



}
