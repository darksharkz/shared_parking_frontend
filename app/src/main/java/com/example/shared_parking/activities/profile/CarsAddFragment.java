package com.example.shared_parking.activities.profile;

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
import com.example.shared_parking.activities.parkingspots.SpotsActivity;
import com.example.shared_parking.activities.parkingspots.SpotsAddFragment;
import com.example.shared_parking.networking.NetworkUtilities;
import com.example.shared_parking.networking.ServerCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class CarsAddFragment extends Fragment implements View.OnClickListener {
    private EditText LicensePlate;
    private Button Add;
    String car;
    private int ID = 0;

    private static final String TAG = "CarsAddFragment";

    //Set spotId < 0, if this should be a new Parking Spot
    public static CarsAddFragment newInstance(JSONObject car) {
        CarsAddFragment carsAddFragment = new CarsAddFragment();
        //Log.e("SpotIdBefore:", Integer.toString(spotId));
        Bundle args = new Bundle();
        //args.putInt("spotId", spotId);
        if (car == null) {
            args.putString("car", null);
        } else {
            args.putString("car", car.toString());
        }
        carsAddFragment.setArguments(args);

        return carsAddFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cars_add, container, false);

        LicensePlate = (EditText) view.findViewById(R.id.etCarsLicenseplate);
        Add = (Button) view.findViewById(R.id.btnCarsAdd);
        Add.setOnClickListener(this);

        car = getArguments().getString("car", null);
        if (car != null) {
            try {
                JSONObject psjson = new JSONObject(car);
                ID = psjson.getInt("ID");
                LicensePlate.setText(psjson.getString("licenseplate"));
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
            case R.id.btnCarsAdd:
                SharedPreferences sharedPref = getContext().getSharedPreferences("key", Context.MODE_PRIVATE);

                if (car == null) {
                    NetworkUtilities.createCar(sharedPref.getString("auth_token", "default"), LicensePlate.getText().toString(), getContext(), new ServerCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            Intent intent = new Intent(getContext(), ProfileActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            Log.e(TAG, "Error with volley while create parkingspacing" + volleyError);
                        }
                    });
                } else {
                    Log.e(TAG, "Jetzt wird editcar aufgerufen!");
                    NetworkUtilities.editCar(ID, sharedPref.getString("auth_token", "default"), LicensePlate.getText().toString(), getContext(), new ServerCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            Intent intent = new Intent(getContext(), ProfileActivity.class);
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