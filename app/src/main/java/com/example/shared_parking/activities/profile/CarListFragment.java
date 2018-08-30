package com.example.shared_parking.activities.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
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
import com.example.shared_parking.activities.parkingoffer.OffersAdapter;
import com.example.shared_parking.activities.parkingoffer.OffersAddFragment;
import com.example.shared_parking.networking.NetworkUtilities;
import com.example.shared_parking.networking.ServerCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class CarListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private CarsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final String TAG = "CarListFragment";
    final List<JSONObject> cars = new LinkedList<JSONObject>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cars, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_cars);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CarsAdapter(cars,
                new CarsAdapter.CarsChangeListener() {
                    @Override
                    public void onClick(View view, final int carId) {
                        switch (view.getId()){
                            case R.id.ib_cars_edit:
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.main_coordinator, CarsAddFragment.newInstance(cars.get(carId))).addToBackStack(null);
                                ft.commit();
                                //Toast.makeText(view.getContext(), "Du hast Edit beim Eintrag Nummer " + getAdapterPosition() + " gedrückt", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.ib_cars_delete:
                                SharedPreferences sharedPref = getContext().getSharedPreferences("key", Context.MODE_PRIVATE);
                                try {
                                    int ID = cars.get(carId).getInt("ID");
                                    NetworkUtilities.deleteCar(ID, sharedPref.getString("auth_token", "default"), getContext(), new ServerCallback() {
                                        @Override
                                        public void onSuccess(JSONObject result) {
                                            Toast.makeText(getContext(), "Eintrag Nummer " + carId + " gelöscht", Toast.LENGTH_SHORT).show();
                                            updateCars();
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

        updateCars();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_cars);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "Click on Button fb");
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_coordinator, CarsAddFragment.newInstance(null)).addToBackStack(null).commit();
            }
        });
        Log.e(TAG, "onCreateView end");
        return view;
    }

    public void updateCars(){
        cars.clear();
        SharedPreferences sharedPref = getContext().getSharedPreferences("key", Context.MODE_PRIVATE);
        NetworkUtilities.getCar(sharedPref.getString("auth_token", "default"), getContext(), new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    JSONArray resultArray = result.getJSONArray("result");
                    for(int i = 0; i < resultArray.length(); i++){
                        cars.add((JSONObject)resultArray.get(i));
                    }
                    mAdapter.setItems(cars);
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
