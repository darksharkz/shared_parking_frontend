package com.example.shared_parking.activities.contracts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.example.shared_parking.R;
import com.example.shared_parking.activities.main.MainActivity;
import com.example.shared_parking.activities.parkingoffer.OffersAddFragment;
import com.example.shared_parking.networking.NetworkUtilities;
import com.example.shared_parking.networking.ServerCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class ContractsAsTListFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private ContractsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final String TAG = "ContractsAsTListFrgment";
    final List<JSONObject> parkingTrades = new LinkedList<JSONObject>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contracts, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_contracts);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ContractsAdapter(parkingTrades,
                new ContractsAdapter.ContractsChangeListener() {
                    @Override
                    public void onClick(View view, final int offerId) {
                        switch (view.getId()){
                            case R.id.ib_contracts_ratextend:

                                break;
                        }
                    }
                }
        );
        mRecyclerView.setAdapter(mAdapter);

        updateParkingTrades();

        return view;
    }

    public void updateParkingTrades(){
        parkingTrades.clear();
        SharedPreferences sharedPref = getContext().getSharedPreferences("key", Context.MODE_PRIVATE);
        NetworkUtilities.getParkingTradesAsTenant(sharedPref.getString("auth_token", "default"), getContext(), new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    JSONArray resultArray = result.getJSONArray("result");
                    for(int i = 0; i < resultArray.length(); i++){
                        parkingTrades.add((JSONObject)resultArray.get(i));
                    }
                    mAdapter.setItems(parkingTrades);
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(VolleyError volleyError) {
                Log.e(TAG, "Error with volley while get parkingtradesaslandlord" + volleyError);
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
