package com.example.shared_parking;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface ServerCallback {
    void onSuccess(JSONObject result);
    void onFailure(VolleyError volleyError);
}
