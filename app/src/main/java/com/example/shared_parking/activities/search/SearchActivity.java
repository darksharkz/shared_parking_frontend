package com.example.shared_parking.activities.search;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.android.volley.VolleyError;
import com.example.shared_parking.activities.BaseActivity;
import com.example.shared_parking.R;
import com.example.shared_parking.activities.profile.ProfileActivity;
import com.example.shared_parking.networking.NetworkUtilities;
import com.example.shared_parking.networking.ServerCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class SearchActivity extends BaseActivity implements OnMapReadyCallback, TimeDialogFragment.TimeDialogListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    List<JSONObject> parkingOffers = new LinkedList<JSONObject>();
    private static final String TAG = "SearchActivity";
    String startDate, startTime, endDate, endTime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Open profile if no car is active
        SharedPreferences sharedPref = this.getSharedPreferences("key", Context.MODE_PRIVATE);
        NetworkUtilities.getCarActive(sharedPref.getString("auth_token", "default"), this, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    if(result.getJSONArray("result").length() != 1){
                        Toast.makeText(getApplicationContext(), "Please choose exactly 1 car as active!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(VolleyError volleyError) {
                Log.e(TAG, "Error with volley while create parkingoffer" + volleyError);
            }
        });

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

        // Find the MenuItem for Search and set onQueryListener
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

        // Find the MenuItem for Time and set onMenuClickedListener
        MenuItem timeViewItem = menu.findItem(R.id.action_search_time);
        timeViewItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                TimeDialogFragment timeDialogFragment = TimeDialogFragment.newInstance(startDate, startTime, endDate, endTime);
                timeDialogFragment.show(getSupportFragmentManager(), "timedialog");
                return true;
            }
        });

        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);

        // Move camera to Würzburg Hubland
        LatLng itBuilding = new LatLng(49.781, 9.973);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(itBuilding));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12));

        // Get current date and time and update Map with current_dt as start_dt and 2 hours later as end_dt
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Calendar c = Calendar.getInstance();
        startDate = dateFormat.format(c.getTime());
        startTime = timeFormat.format(c.getTime()) + ":01";
        c.add(Calendar.HOUR, 2);
        endDate = dateFormat.format(c.getTime());
        endTime = timeFormat.format(c.getTime()) + ":00";
        updateMap(startDate, startTime, endDate, endTime);
    }

    public void updateMap(String startDate, String startTime, String endDate, String endTime){
        parkingOffers.clear();
        String start_dt = startDate + " " + startTime;
        String end_dt = endDate + " " + endTime;
        SharedPreferences sharedPref = this.getSharedPreferences("key", Context.MODE_PRIVATE);
        NetworkUtilities.getParkingOffersbyTime(sharedPref.getString("auth_token", "default"), start_dt, end_dt, this, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    JSONArray resultArray = null;
                    resultArray = result.getJSONArray("result");
                    for(int i = 0; i < resultArray.length(); i++){
                        parkingOffers.add((JSONObject)resultArray.get(i));
                    }
                    for(JSONObject po : parkingOffers){
                        mMap.addMarker(new MarkerOptions().position(new LatLng(po.getDouble("lat"), po.getDouble("lng"))).title(po.getString("ID"))).setTag(po);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(VolleyError volleyError) {
                Log.e(TAG, "Error with volley while create parkingoffer" + volleyError);
            }
        });

    }

    @Override
    public void onDialogPositiveClick(TimeDialogFragment dialog) {
        //Toast.makeText(this, "Startdate " + startDate, Toast.LENGTH_LONG).show();
        startDate = dialog.startDate;
        startTime = dialog.startTime;
        endDate = dialog.endDate;
        endTime = dialog.endTime;
        updateMap(startDate, startTime, endDate, endTime);
    }

    @Override
    public void onDialogNegativeClick(TimeDialogFragment dialog) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        JSONObject parkingOffer = (JSONObject)marker.getTag();
        String start_dt = startDate + " " + startTime;
        String end_dt = endDate + " " + endTime;
        ParkingOfferDialogFragment parkingOfferDialogFragment = ParkingOfferDialogFragment.newInstance(parkingOffer, start_dt, end_dt);
        parkingOfferDialogFragment.show(getSupportFragmentManager(), "parkingOfferDialog");
        return false;
    }
}
