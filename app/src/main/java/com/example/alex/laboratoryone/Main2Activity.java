package com.example.alex.laboratoryone;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.alex.laboratoryone.fragments.CurrencyFragment;
import com.example.alex.laboratoryone.fragments.Dialog;
import com.example.alex.laboratoryone.fragments.LenghtFragment;
import com.example.alex.laboratoryone.fragments.MassFragment;

public class Main2Activity extends AppCompatActivity {

    private TextView mTextMessage;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(new LenghtFragment());
                    return true;
                case R.id.navigation_dashboard:
                    switchFragment(new MassFragment());
                    return true;
                case R.id.navigation_notifications:
                    ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
                    if (connectivityManager != null) {
                        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            switchFragment(new CurrencyFragment());
                        } else {
                            Dialog dialog = new Dialog();
                            dialog.show(getSupportFragmentManager(), "23");
                        }
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).commit();
    }

}
