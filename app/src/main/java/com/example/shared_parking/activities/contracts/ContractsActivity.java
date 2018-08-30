package com.example.shared_parking.activities.contracts;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.shared_parking.activities.BaseActivity;
import com.example.shared_parking.R;

public class ContractsActivity extends BaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setVisibility(View.VISIBLE);

        tabLayout.addTab(tabLayout.newTab().setText("As Tenant"));
        tabLayout.addTab(tabLayout.newTab().setText("As Landlord"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0){
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_coordinator, new ContractsAsTListFragment()).addToBackStack(null).commit();
                } else if(tabLayout.getSelectedTabPosition() == 1){
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_coordinator, new ContractsAsLListFragment()).addToBackStack(null).commit();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_coordinator, new ContractsAsTListFragment()).commit();
    }
}
