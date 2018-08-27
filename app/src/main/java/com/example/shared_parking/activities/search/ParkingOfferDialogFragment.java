package com.example.shared_parking.activities.search;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.shared_parking.ContractsActivity;
import com.example.shared_parking.R;
import com.example.shared_parking.networking.NetworkUtilities;
import com.example.shared_parking.networking.ServerCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class ParkingOfferDialogFragment extends DialogFragment {

    private TextView Parkingspaceid, Price, Start_dt, End_dt;
    private Context context;
    private static final String TAG = "ParkingOfferDialogFrgmt";

    static ParkingOfferDialogFragment newInstance(JSONObject parkingOffer, String start_dt, String end_dt){
        ParkingOfferDialogFragment fragment = new ParkingOfferDialogFragment();

        Bundle args = new Bundle();
        try {
            parkingOffer.put("start_dt", start_dt);
            parkingOffer.put("end_dt", end_dt);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        args.putString("parkingOffer", parkingOffer.toString());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        Log.e(TAG, "onAttach(Context context) called");
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity context) {
        Log.e(TAG, "onAttach(Activity context) called");
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.list_offers, null);

        builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton("Book", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        createParkingTrade();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ParkingOfferDialogFragment.this.getDialog().cancel();
                    }
                });

        String po = getArguments().getString("parkingOffer");
        try {
            JSONObject parkingOffer = new JSONObject(po);
            Parkingspaceid = (TextView) dialogView.findViewById(R.id.list_offers_parkingspaceid);
            Price = (TextView) dialogView.findViewById(R.id.list_offers_price);
            Start_dt = (TextView) dialogView.findViewById(R.id.list_offers_startdt);
            End_dt = (TextView) dialogView.findViewById(R.id.list_offers_enddt);
            Parkingspaceid.setText(parkingOffer.getString("parkingspaceid"));
            Price.setText(parkingOffer.getString("price"));
            Start_dt.setText(parkingOffer.getString("start_dt"));
            End_dt.setText(parkingOffer.getString("end_dt"));
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return builder.create();
    }

    private void createParkingTrade(){
        SharedPreferences sharedPref = getContext().getSharedPreferences("key", Context.MODE_PRIVATE);
        String po = getArguments().getString("parkingOffer");
        try {
            JSONObject parkingOffer = new JSONObject(po);
            NetworkUtilities.createParkingTrade(sharedPref.getString("auth_token", "default"), parkingOffer.getInt("parkingspaceid"), parkingOffer.getInt("price"), parkingOffer.getString("start_dt"), parkingOffer.getString("end_dt"), parkingOffer.getInt("userid"), getContext(), new ServerCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    Toast.makeText(context, "You have booked a parking space", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, ContractsActivity.class);
                    context.startActivity(intent);
                }

                @Override
                public void onFailure(VolleyError volleyError) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

}
