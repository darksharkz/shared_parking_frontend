package com.example.shared_parking.activities.parkingspots;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.example.shared_parking.R;
import com.example.shared_parking.networking.NetworkUtilities;
import com.example.shared_parking.networking.ServerCallback;

import org.json.JSONObject;
import org.json.*;

public class SpotsAddFragment extends Fragment implements View.OnClickListener {
    private EditText PostCode, City, Street, Number, Lat, Lng;
    private Button Add;
    String parkingSpot;
    private int ID = 0;

    private static final String TAG = "SpotsAddFragment";

    //Set spotId < 0, if this should be a new Parking Spot
    public static SpotsAddFragment newInstance(JSONObject parkingSpace){
        SpotsAddFragment spotsAddFragment = new SpotsAddFragment();
        //Log.e("SpotIdBefore:", Integer.toString(spotId));
        Bundle args = new Bundle();
        //args.putInt("spotId", spotId);
        if(parkingSpace == null){
            args.putString("parkingSpot", null);
        }
        else{
            args.putString("parkingSpot", parkingSpace.toString());
        }
        spotsAddFragment.setArguments(args);

        return spotsAddFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spots_add, container, false);

        PostCode = (EditText) view.findViewById(R.id.etSpotsAddPostCode);
        City = (EditText) view.findViewById(R.id.etSpotsAddCity);
        Street = (EditText) view.findViewById(R.id.etSpotsAddStreet);
        Number = (EditText) view.findViewById(R.id.etSpotsAddNumber);
        Lat = (EditText) view.findViewById(R.id.etSpotsAddLat);
        Lng = (EditText) view.findViewById(R.id.etSpotsAddLng);
        Add = (Button) view.findViewById(R.id.btnSpotsAdd);
        Add.setOnClickListener(this);
        //Add.setText("Test");

        parkingSpot = getArguments().getString("parkingSpot", null);
        //Log.e("SpotsAddFragmentSpotId:", Integer.toString(spotId));
        if (parkingSpot != null){
            try {
                JSONObject psjson = new JSONObject(parkingSpot);
                ID = psjson.getInt("ID");
                PostCode.setText(psjson.getString("postcode"));
                City.setText(psjson.getString("city"));
                Street.setText(psjson.getString("street"));
                Number.setText(psjson.getString("number"));
                Lat.setText(psjson.getString("lat"));
                Lng.setText(psjson.getString("lng"));
                Add.setText("Change Information");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSpotsAdd:
                Log.e(TAG, "Click on Button");
                SharedPreferences sharedPref = getContext().getSharedPreferences("key", Context.MODE_PRIVATE);

                if(parkingSpot == null){
                    NetworkUtilities.createParkingSpace(sharedPref.getString("auth_token", "default"), City.getText().toString(), Street.getText().toString(), Integer.parseInt(PostCode.getText().toString()), Integer.parseInt(Number.getText().toString()), Double.parseDouble(Lat.getText().toString()), Double.parseDouble(Lng.getText().toString()), getContext(), new ServerCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            //FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            //ft.replace(R.id.main_coordinator, new SpotsListFragment()).addToBackStack(null);
                            //ft.commit();
                            Intent intent = new Intent(getContext(), SpotsActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            Log.e(TAG, "Error with volley while create parkingspacing" + volleyError);
                        }
                    });
                }
                else {
                    NetworkUtilities.editParkingSpace(ID, sharedPref.getString("auth_token", "default"), City.getText().toString(), Street.getText().toString(), Integer.parseInt(PostCode.getText().toString()), Integer.parseInt(Number.getText().toString()), Double.parseDouble(Lat.getText().toString()), Double.parseDouble(Lng.getText().toString()), getContext(), new ServerCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            //FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            //ft.replace(R.id.main_coordinator, new SpotsListFragment()).addToBackStack(null);
                            //ft.commit();
                            Intent intent = new Intent(getContext(), SpotsActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            Log.e(TAG, "Error with volley while editing parkingspacing" + volleyError);
                        }
                    });
                }

                break;
        }
    }
}
