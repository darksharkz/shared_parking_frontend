package com.example.shared_parking.activities.report;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.shared_parking.R;
import com.example.shared_parking.activities.BaseActivity;
import com.example.shared_parking.activities.parkingoffer.OffersListFragment;

public class ReportActivity extends BaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_coordinator, new ReportFragment()).commit();
    }
}
