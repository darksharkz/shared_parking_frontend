package com.example.shared_parking;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class SpotsListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private SpotsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final String TAG = "SpotsListFragment";
    final List<JSONObject> parkingSpaces = new LinkedList<JSONObject>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "Start onCreateView");
        View view = inflater.inflate(R.layout.fragment_spots, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Log.e(TAG, "onCreateView before new SpotsAdapter");
        mAdapter = new SpotsAdapter(parkingSpaces,
                new SpotsAdapter.SpotsChangeListener() {
                    @Override
                    public void onClick(View view, final int spotId) {
                        switch (view.getId()){
                            case R.id.ib_spots_edit:
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                Log.e(TAG, "spotID" + spotId);
                                Log.e(TAG, "psjson" + parkingSpaces);
                                ft.replace(R.id.main_coordinator, SpotsAddFragment.newInstance(parkingSpaces.get(spotId))).addToBackStack(null);
                                ft.commit();
                                //Toast.makeText(view.getContext(), "Du hast Edit beim Eintrag Nummer " + getAdapterPosition() + " gedrückt", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.ib_spots_delete:
                                SharedPreferences sharedPref = getContext().getSharedPreferences("key", Context.MODE_PRIVATE);
                                try {
                                    int ID = parkingSpaces.get(spotId).getInt("ID");
                                    NetworkUtilities.deleteParkingSpace(ID, sharedPref.getString("auth_token", "default"), getContext(), new ServerCallback() {
                                        @Override
                                        public void onSuccess(JSONObject result) {
                                            Toast.makeText(getContext(), "Eintrag Nummer " + spotId + " gelöscht", Toast.LENGTH_SHORT).show();
                                            updateParkingSpaces();
                                        }
                                        @Override
                                        public void onFailure(VolleyError volleyError) {
                                            Log.e(TAG, "Error with volley while create parkingspacing" + volleyError);
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

        updateParkingSpaces();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "Click on Button fb");
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_coordinator, SpotsAddFragment.newInstance(null)).addToBackStack(null);
                ft.commit();
            }
        });
        Log.e(TAG, "onCreateView end");
        return view;
    }

    public void updateParkingSpaces(){
        parkingSpaces.clear();
        SharedPreferences sharedPref = getContext().getSharedPreferences("key", Context.MODE_PRIVATE);
        NetworkUtilities.getParkingSpacebyUser(sharedPref.getString("auth_token", "default"), getContext(), new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    JSONArray resultArray = result.getJSONArray("result");
                    for(int i = 0; i < resultArray.length(); i++){
                        parkingSpaces.add((JSONObject)resultArray.get(i));
                    }
                    Log.e(TAG, "parkingSpaces after update" + parkingSpaces);
                    mAdapter.setItems(parkingSpaces);
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(VolleyError volleyError) {
                Log.e(TAG, "Error with volley while get parkingspacing" + volleyError);
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
