package com.example.shared_parking.activities.parkingoffer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.shared_parking.R;
import com.example.shared_parking.activities.main.MainActivity;
import com.example.shared_parking.networking.NetworkUtilities;
import com.example.shared_parking.networking.ServerCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class OffersListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private OffersAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final String TAG = "OffersListFragment";
    final List<JSONObject> parkingOffers = new LinkedList<JSONObject>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_offers);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new OffersAdapter(parkingOffers,
                new OffersAdapter.OffersChangeListener() {
                    @Override
                    public void onClick(View view, final int offerId) {
                        switch (view.getId()){
                            case R.id.ib_offers_edit:
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.main_coordinator, OffersAddFragment.newInstance(parkingOffers.get(offerId))).addToBackStack(null);
                                ft.commit();
                                //Toast.makeText(view.getContext(), "Du hast Edit beim Eintrag Nummer " + getAdapterPosition() + " gedrückt", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.ib_offers_delete:
                                SharedPreferences sharedPref = getContext().getSharedPreferences("key", Context.MODE_PRIVATE);
                                try {
                                    int ID = parkingOffers.get(offerId).getInt("ID");
                                    NetworkUtilities.deleteParkingOffer(ID, sharedPref.getString("auth_token", "default"), getContext(), new ServerCallback() {
                                        @Override
                                        public void onSuccess(JSONObject result) {
                                            Toast.makeText(getContext(), "Eintrag Nummer " + offerId + " gelöscht", Toast.LENGTH_SHORT).show();
                                            updateParkingOffers();
                                        }
                                        @Override
                                        public void onFailure(VolleyError volleyError) {
                                            Log.e(TAG, "Error with volley while deleting parkingoffer" + volleyError);
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                break;
                        }
                    }
                }
        );
        mRecyclerView.setAdapter(mAdapter);

        updateParkingOffers();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_offers);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "Click on Button fb");
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_coordinator, OffersAddFragment.newInstance(null)).addToBackStack(null);
                ft.commit();
            }
        });
        Log.e(TAG, "onCreateView end");
        return view;
    }

    public void updateParkingOffers(){
        parkingOffers.clear();
        SharedPreferences sharedPref = getContext().getSharedPreferences("key", Context.MODE_PRIVATE);
        NetworkUtilities.getParkingOffersbyUser(sharedPref.getString("auth_token", "default"), getContext(), new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    JSONArray resultArray = result.getJSONArray("result");
                    for(int i = 0; i < resultArray.length(); i++){
                        parkingOffers.add((JSONObject)resultArray.get(i));
                    }
                    mAdapter.setItems(parkingOffers);
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(VolleyError volleyError) {
                Log.e(TAG, "Error with volley while get parkingoffer" + volleyError);
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
