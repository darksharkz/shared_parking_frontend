package com.example.shared_parking.activities.parkingoffer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.VolleyError;
import com.example.shared_parking.R;
import com.example.shared_parking.networking.NetworkUtilities;
import com.example.shared_parking.networking.ServerCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class OffersAddFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {
    private EditText StartDate, StartTime, EndDate, EndTime, ParkingSpaceID, Price;
    private Button Add;
    private ImageButton BtnChangeStartDate, BtnChangeStartTime, BtnChangeEndDate, BtnChangeEndTime;
    String parkingOffer;
    private int ID = 0;

    private static final String TAG = "OffersAddFragment";

    //Set spotId < 0, if this should be a new Parking Spot
    public static OffersAddFragment newInstance(JSONObject parkingOffer){
        OffersAddFragment offersAddFragment = new OffersAddFragment();
        Bundle args = new Bundle();
        if(parkingOffer == null){
            args.putString("parkingOffer", null);
        }
        else{
            args.putString("parkingOffer", parkingOffer.toString());
        }
        offersAddFragment.setArguments(args);

        return offersAddFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers_add, container, false);

        StartDate = (EditText) view.findViewById(R.id.etOffersAddStartDate);
        StartDate.setInputType(InputType.TYPE_NULL);
        StartDate.setOnFocusChangeListener(this);
        StartDate.setOnClickListener(this);
        StartTime = (EditText) view.findViewById(R.id.etOffersAddStartTime);
        StartTime.setInputType(InputType.TYPE_NULL);
        StartTime.setOnFocusChangeListener(this);
        StartTime.setOnClickListener(this);
        EndDate = (EditText) view.findViewById(R.id.etOffersAddEndDate);
        EndDate.setInputType(InputType.TYPE_NULL);
        EndDate.setOnFocusChangeListener(this);
        EndDate.setOnClickListener(this);
        EndTime = (EditText) view.findViewById(R.id.etOffersAddEndTime);
        EndTime.setInputType(InputType.TYPE_NULL);
        EndTime.setOnFocusChangeListener(this);
        EndTime.setOnClickListener(this);
        ParkingSpaceID = (EditText) view.findViewById(R.id.etOffersAddParkingSpaceID);
        Price = (EditText) view.findViewById(R.id.etOffersAddPrice);
        BtnChangeStartDate = (ImageButton) view.findViewById(R.id.ibOffersAddStartDate);
        BtnChangeStartDate.setOnClickListener(this);
        BtnChangeStartTime = (ImageButton) view.findViewById(R.id.ibOffersAddStartTime);
        BtnChangeStartTime.setOnClickListener(this);
        BtnChangeEndDate = (ImageButton) view.findViewById(R.id.ibOffersAddEndDate);
        BtnChangeEndDate.setOnClickListener(this);
        BtnChangeEndTime = (ImageButton) view.findViewById(R.id.ibOffersAddEndTime);
        BtnChangeEndTime.setOnClickListener(this);
        Add = (Button) view.findViewById(R.id.btnOffersAdd);
        Add.setOnClickListener(this);

        parkingOffer = getArguments().getString("parkingOffer", null);
        if (parkingOffer != null){
            try {
                JSONObject psjson = new JSONObject(parkingOffer);
                ID = psjson.getInt("ID");
                Price.setText(psjson.getString("price"));
                ParkingSpaceID.setText(psjson.getString("parkingspaceid"));
                StartDate.setText(psjson.getString("start_dt").split(" ")[0].trim());
                StartTime.setText(psjson.getString("start_dt").split(" ")[1].trim());
                EndDate.setText(psjson.getString("end_dt").split(" ")[0].trim());
                EndTime.setText(psjson.getString("end_dt").split(" ")[1].trim());
                Add.setText("Change Information");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    @Override
    public void onClick(View view) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        switch (view.getId()) {
            case R.id.btnOffersAdd:
                SharedPreferences sharedPref = getContext().getSharedPreferences("key", Context.MODE_PRIVATE);

                String start_dt = StartDate.getText().toString() + " " + StartTime.getText().toString();
                String end_dt = EndDate.getText().toString() + " " + EndTime.getText().toString();
                if(parkingOffer == null){
                    NetworkUtilities.createParkingOffer(sharedPref.getString("auth_token", "default"), Integer.parseInt(ParkingSpaceID.getText().toString()), Integer.parseInt(Price.getText().toString()), start_dt, end_dt, getContext(), new ServerCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            Intent intent = new Intent(getContext(), OffersActivity.class);
                            startActivity(intent);
                        }
                        @Override
                        public void onFailure(VolleyError volleyError) {
                            Log.e(TAG, "Error with volley while create parkingoffer" + volleyError);
                        }
                    });
                }
                else {
                    NetworkUtilities.editParkingOffer(ID, sharedPref.getString("auth_token", "default"), Integer.parseInt(ParkingSpaceID.getText().toString()), Integer.parseInt(Price.getText().toString()), start_dt, end_dt, getContext(), new ServerCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            Intent intent = new Intent(getContext(), OffersActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(VolleyError volleyError) {
                            Log.e(TAG, "Error with volley while editing parkingoffer" + volleyError);
                        }
                    });
                }

                break;
            case R.id.etOffersAddStartDate:
                DatePickerDialog datePickerDialogStart = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        StartDate.setText(year + "-" + String.format("%02d", monthOfYear) + "-" + String.format("%02d", dayOfMonth));
                    }
                }, year, month, day);
                datePickerDialogStart.show();
                break;
            case R.id.etOffersAddStartTime:
                TimePickerDialog timePickerDialogStart = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        StartTime.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute) + ":01");
                    }
                }, hour, minute, DateFormat.is24HourFormat(getActivity()));
                timePickerDialogStart.show();
                break;
            case R.id.etOffersAddEndDate:
                DatePickerDialog datePickerDialogEnd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        EndDate.setText(year + "-" + String.format("%02d", monthOfYear) + "-" + String.format("%02d", dayOfMonth));
                    }
                }, year, month, day);
                datePickerDialogEnd.show();
                break;
            case R.id.etOffersAddEndTime:
                TimePickerDialog timePickerDialogEnd = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        EndTime.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute) + ":00");
                    }
                }, hour, minute, DateFormat.is24HourFormat(getActivity()));
                timePickerDialogEnd.show();
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if(!b) return;

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        switch (view.getId()) {
            case R.id.etOffersAddStartDate:
                DatePickerDialog datePickerDialogStart = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        StartDate.setText(year + "-" + String.format("%02d", monthOfYear+1) + "-" + String.format("%02d", dayOfMonth));
                    }
                }, year, month, day);
                datePickerDialogStart.show();
                break;
            case R.id.etOffersAddStartTime:
                TimePickerDialog timePickerDialogStart = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        StartTime.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute) + ":01");
                    }
                }, hour, minute, DateFormat.is24HourFormat(getActivity()));
                timePickerDialogStart.show();
                break;
            case R.id.etOffersAddEndDate:
                DatePickerDialog datePickerDialogEnd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        EndDate.setText(year + "-" + String.format("%02d", monthOfYear+1) + "-" + String.format("%02d", dayOfMonth));
                    }
                }, year, month, day);
                datePickerDialogEnd.show();
                break;
            case R.id.etOffersAddEndTime:
                TimePickerDialog timePickerDialogEnd = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        EndTime.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute) + ":00");
                    }
                }, hour, minute, DateFormat.is24HourFormat(getActivity()));
                timePickerDialogEnd.show();
                break;
        }
    }
}