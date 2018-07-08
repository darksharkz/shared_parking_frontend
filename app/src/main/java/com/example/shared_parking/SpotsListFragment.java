package com.example.shared_parking;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SpotsListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spots, container, false);

        /**
        ArrayList<HashMap<String, String>> testingList = new ArrayList<HashMap<String, String>>();
        for(int i = 0; i < 2; i++){
            HashMap<String, String> testingMap = new HashMap<String, String>();
            testingMap.put("first_item", "Map " + i + " Item 1");
            testingMap.put("second_item", "Map " + i + " Item 2");
            testingList.add(testingMap);
        }
        String fromArray[]={"first_item","second_item"};
        int to[]={R.id.text1,R.id.text2};

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), testingList, R.layout.list_spots, fromArray, to);
        setListAdapter(adapter);
         */


        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SpotsAdapter(MainActivity.appDatabase.parkingSpaceDao().getAll(),
                new SpotsAdapter.SpotsChangeListener() {
                    @Override
                    public void onClick(View view, int spotId) {
                        switch (view.getId()){
                            case R.id.ib_spots_edit:
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.main_coordinator, SpotsAddFragment.newInstance(spotId)).addToBackStack(null);
                                ft.commit();
                                //Toast.makeText(view.getContext(), "Du hast Edit beim Eintrag Nummer " + getAdapterPosition() + " gedrückt", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.ib_spots_delete:
                                //Toast.makeText(view.getContext(), "Du hast Delete beim Eintrag Nummer " + getAdapterPosition() + " gedrückt", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
        );
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_coordinator, new SpotsAddFragment()).addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }

}
