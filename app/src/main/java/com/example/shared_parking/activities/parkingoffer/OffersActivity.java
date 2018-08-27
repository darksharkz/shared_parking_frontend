package com.example.shared_parking.activities.parkingoffer;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.shared_parking.BaseActivity;
import com.example.shared_parking.R;
import com.example.shared_parking.activities.parkingspots.SpotsListFragment;

public class OffersActivity extends BaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OffersListFragment fragment = new OffersListFragment();
        fragmentTransaction.add(R.id.main_coordinator, fragment);
        fragmentTransaction.commit();
    }
}
