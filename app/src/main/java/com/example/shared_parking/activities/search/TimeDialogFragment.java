package com.example.shared_parking.activities.search;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.shared_parking.R;

import java.util.Calendar;

public class TimeDialogFragment extends DialogFragment implements View.OnClickListener{

    public interface TimeDialogListener {
        public void onDialogPositiveClick(TimeDialogFragment dialog);
        public void onDialogNegativeClick(TimeDialogFragment dialog);
    }

    private TextView StartDate, StartTime, EndDate, EndTime;
    public String startDate, startTime, endDate, endTime;
    TimeDialogListener mListener;

    static TimeDialogFragment newInstance(String startDate, String startTime, String endDate, String endTime){
        TimeDialogFragment fragment = new TimeDialogFragment();

        Bundle args = new Bundle();
        args.putString("startDate", startDate);
        args.putString("startTime", startTime);
        args.putString("endDate", endDate);
        args.putString("endTime", endTime);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            mListener = (TimeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_time, null);

        builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(TimeDialogFragment.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TimeDialogFragment.this.getDialog().cancel();
                    }
                });

        StartDate = (TextView) dialogView.findViewById(R.id.tvDialogTimeStartDate);
        StartTime = (TextView) dialogView.findViewById(R.id.tvDialogTimeStartTime);
        EndDate = (TextView) dialogView.findViewById(R.id.tvDialogTimeEndDate);
        EndTime = (TextView) dialogView.findViewById(R.id.tvDialogTimeEndTime);
        startDate = getArguments().getString("startDate");
        startTime = getArguments().getString("startTime");
        endDate = getArguments().getString("endDate");
        endTime = getArguments().getString("endTime");
        StartDate.setText("StartDate " + startDate);
        StartTime.setText("StartTime " + startTime);
        EndDate.setText("EndDate " + endDate);
        EndTime.setText("EndTime " + endTime);
        StartDate.setOnClickListener(this);
        StartTime.setOnClickListener(this);
        EndDate.setOnClickListener(this);
        EndTime.setOnClickListener(this);

        return builder.create();
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
            case R.id.tvDialogTimeStartDate:
                DatePickerDialog datePickerDialogStart = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        startDate = year + "-" + String.format("%02d", monthOfYear+1) + "-" + String.format("%02d", dayOfMonth);
                        StartDate.setText("StartDate " + startDate);
                    }
                }, year, month, day);
                datePickerDialogStart.show();
                break;
            case R.id.tvDialogTimeStartTime:
                TimePickerDialog timePickerDialogStart = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        startTime = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute) + ":01";
                        StartTime.setText("StartTime " + startTime);
                    }
                }, hour, minute, DateFormat.is24HourFormat(getActivity()));
                timePickerDialogStart.show();
                break;
            case R.id.tvDialogTimeEndDate:
                DatePickerDialog datePickerDialogEnd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        endDate = year + "-" + String.format("%02d", monthOfYear+1) + "-" + String.format("%02d", dayOfMonth);
                        EndDate.setText("EndDate " + endDate);
                    }
                }, year, month, day);
                datePickerDialogEnd.show();
                break;
            case R.id.tvDialogTimeEndTime:
                TimePickerDialog timePickerDialogEnd = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        endTime = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute) + ":00";
                        EndTime.setText("EndTime " + endTime);
                    }
                }, hour, minute, DateFormat.is24HourFormat(getActivity()));
                timePickerDialogEnd.show();
                break;
        }
    }
}
