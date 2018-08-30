package com.example.shared_parking.activities.parkingspots;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.shared_parking.activities.BaseActivity;
import com.example.shared_parking.R;

public class SpotsActivity extends BaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SpotsListFragment fragment = new SpotsListFragment();
        fragmentTransaction.add(R.id.main_coordinator, fragment);
        fragmentTransaction.commit();

    }
}
