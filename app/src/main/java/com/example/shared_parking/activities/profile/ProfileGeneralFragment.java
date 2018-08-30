package com.example.shared_parking.activities.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.shared_parking.R;
import com.example.shared_parking.networking.NetworkUtilities;
import com.example.shared_parking.networking.ServerCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileGeneralFragment extends Fragment {
    private TextView Mail, Firstname, Lastname, Balance;
    private static final String TAG = "ProfileGeneralFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "on create view");
        View view = inflater.inflate(R.layout.fragment_profile_general, container, false);

        Mail = (TextView) view.findViewById(R.id.tv_profile_mail);
        Firstname = (TextView) view.findViewById(R.id.tv_profile_firstname);
        Lastname = (TextView) view.findViewById(R.id.tv_profile_lastname);
        Balance = (TextView) view.findViewById(R.id.tv_profile_balance);

        SharedPreferences sharedPref = getContext().getSharedPreferences("key", Context.MODE_PRIVATE);
        NetworkUtilities.getUser(sharedPref.getString("auth_token", "default"), getContext(), new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    JSONObject userjson = result.getJSONObject("result");
                    Firstname.setText(userjson.getString("firstname"));
                    Lastname.setText(userjson.getString("lastname"));
                    Mail.setText(userjson.getString("mail"));
                    Balance.setText(Double.toString((double)((double)userjson.getInt("balance")/100)) + " €");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(VolleyError volleyError) {
                Log.e(TAG, "Error with volley while get parkingoffer" + volleyError);
            }
        });


        return view;
    }
}
