package com.example.shared_parking.activities.report;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.shared_parking.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ReportFragment extends Fragment implements View.OnClickListener{
    RadioGroup radioGroup;
    RadioButton ParkingTrade, ParkingPlace;
    EditText ID, Licenseplate;
    CheckBox Exists;
    Button Send;
    boolean pt = false, pp = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        radioGroup = (RadioGroup) view.findViewById(R.id.rg_report);
        ParkingTrade = (RadioButton) view.findViewById(R.id.rb_report_parkingtrade);
        ParkingPlace = (RadioButton) view.findViewById(R.id.rb_report_parkingplace);
        ID = (EditText) view.findViewById(R.id.et_report_id);
        Licenseplate = (EditText) view.findViewById(R.id.et_report_licenseplate);
        Exists = (CheckBox) view.findViewById(R.id.cb_report_exists);
        Send = (Button) view.findViewById(R.id.btn_report_send);

        ParkingTrade.setOnClickListener(this);
        ParkingPlace.setOnClickListener(this);
        Send.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_report_send:
                if(pt){
                    Toast.makeText(getContext(), "You have issued a report for a Parking Trade!", Toast.LENGTH_LONG).show();
                }
                else if(pp){
                    Toast.makeText(getContext(), "You have issued a report for a Parking Place!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(), "Please choose one of the two radio buttons!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.rb_report_parkingtrade:
                pt = true;
                pp = false;
                ID.setHint("Parking Trade ID");
                break;
            case R.id.rb_report_parkingplace:
                pp = true;
                pt = false;
                ID.setHint("Parking Place ID");
                break;

        }

    }
}
