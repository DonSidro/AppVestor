package com.calldorado.appvestor;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.calldorado.appvestor.R;
import com.calldorado.appvestor.activities.ui.SettingsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    public static boolean hasRun = false;
    private static final String TAG = "MainActivity";
    BottomNavigationView navView;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                navView.setSelectedItemId(R.id.navigation_dashboard);
                return true;
            case R.id.logoff:
                SharedPreferences mSharedPreferences = getSharedPreferences("UserLoginState", MODE_PRIVATE);

                mSharedPreferences.edit().putBoolean("LoginState",false).commit();
                mSharedPreferences.edit().putBoolean("USE_BIOMETRIC",false).commit();
                mSharedPreferences.edit().putString("AccountId",null).commit();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();

                break;
            case R.id.settingsFragment:
                navView.setSelectedItemId(R.id.settingsFragment);
                NavigationUI.onNavDestinationSelected(item, navController);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: " + navView.getSelectedItemId() + " : " + R.id.settingsFragment);
        if(navController.getCurrentDestination().getId() == R.id.settingsFragment){
            navView.setSelectedItemId(R.id.navigation_dashboard);
        }else{
            finish();
        }
    }
}
