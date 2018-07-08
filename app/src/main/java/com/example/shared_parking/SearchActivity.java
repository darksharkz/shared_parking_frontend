package com.example.shared_parking;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class SearchActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        fragment.getMapAsync(this);
        fragmentTransaction.add(R.id.main_coordinator, fragment);
        fragmentTransaction.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.appbar_search, menu);

        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();

                Geocoder geocoder = new Geocoder(getBaseContext());
                List<android.location.Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocationName(query, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(addresses != null){
                    android.location.Address address = addresses.get(0);
                    LatLng position = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Move camera to Würzburg Hubland
        LatLng itBuilding = new LatLng(49.781, 9.973);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(itBuilding));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12));

        AppDatabase appDatabase = MainActivity.appDatabase;
        List<ParkingSpace> parkingSpaces = appDatabase.parkingSpaceDao().getAll();

        for(ParkingSpace ps : parkingSpaces){
            mMap.addMarker(new MarkerOptions().position(new LatLng(ps.getLat(), ps.getLng())).title("First Marker"));
        }

    }
}
