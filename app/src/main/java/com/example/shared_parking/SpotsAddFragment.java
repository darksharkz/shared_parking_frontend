package com.example.shared_parking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.net.InterfaceAddress;

public class SpotsAddFragment extends Fragment {
    private EditText PostCode, City, Street, Number, Lat, Lng;
    private Button Add;

    //Set spotId < 0, if this should be a new Parking Spot
    public static SpotsAddFragment newInstance(int spotId){
        SpotsAddFragment spotsAddFragment = new SpotsAddFragment();
        Log.e("SpotIdBefore:", Integer.toString(spotId));
        Bundle args = new Bundle();
        args.putInt("spotId", spotId);
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
        //Add.setText("Test");

        int spotId = getArguments().getInt("spotId", -1);
        Log.e("SpotsAddFragmentSpotId:", Integer.toString(spotId));
        if (spotId >= 0){
            ParkingSpace parkingSpace = MainActivity.appDatabase.parkingSpaceDao().getWithId(spotId);
            PostCode.setText(Integer.toString(parkingSpace.getAddress().getPostCode()));
            City.setText(parkingSpace.getAddress().getCity());
            Street.setText(parkingSpace.getAddress().getStreet());
            Number.setText(Integer.toString(parkingSpace.getAddress().getNumber()));
            Lat.setText(Float.toString(parkingSpace.getLat()));
            Lng.setText(Float.toString(parkingSpace.getLng()));
            Add.setText("Change Information");
        }

        return view;
    }
}
