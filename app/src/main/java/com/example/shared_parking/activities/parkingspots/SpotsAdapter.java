package com.example.shared_parking.activities.parkingspots;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.shared_parking.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

class SpotsAdapter extends RecyclerView.Adapter<SpotsAdapter.ViewHolder> {
    private List<JSONObject> parkingSpaces;
    private SpotsChangeListener spotsChangeListener;
    private static final String TAG = "SpotsAdapter";

    public interface SpotsChangeListener {
        void onClick(View view, int spotId);
    }

    public SpotsAdapter(List<JSONObject> parkingSpaces, SpotsChangeListener spotsChangeListener) {
        this.parkingSpaces = parkingSpaces;
        this.spotsChangeListener = spotsChangeListener;
        //this.listener = listener;
    }

    public void setItems(List<JSONObject> parkingSpaces){
        this.parkingSpaces = parkingSpaces;
        Log.e(TAG, "parkingSpaces after update in adapter" + parkingSpaces);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SpotsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_spots, parent, false);
        Log.e(TAG, "onCreateViewHolder");
        return new SpotsAdapter.ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JSONObject parkingSpace = null;
        try {
            parkingSpace = parkingSpaces.get(position);
            holder.postCode.setText(parkingSpace.getString("postcode"));
            holder.city.setText(parkingSpace.getString("city"));
            holder.street.setText(parkingSpace.getString("street"));
            holder.number.setText(parkingSpace.getString("number"));
            holder.lat.setText(parkingSpace.getString("lat"));
            holder.lng.setText(parkingSpace.getString("lng"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.spotId = position;
        holder.spotsChangeListener = spotsChangeListener;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return parkingSpaces.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView postCode, city, street, number, lat, lng;
        public ImageButton edit, delete;
        public int spotId;
        private SpotsChangeListener spotsChangeListener;

        public ViewHolder(View view) {
            super(view);
            Log.e(TAG, "create ViewHolder");
            postCode = (TextView) view.findViewById(R.id.list_spots_parkingspaceid_text);
            city = (TextView) view.findViewById(R.id.list_spots_city);
            street = (TextView) view.findViewById(R.id.list_spots_startdt_text);
            number = (TextView) view.findViewById(R.id.list_spots_number);
            lat = (TextView) view.findViewById(R.id.list_spots_enddt);
            lng = (TextView) view.findViewById(R.id.list_spots_lng);
            edit = (ImageButton) view.findViewById(R.id.ib_spots_edit);
            delete = (ImageButton) view.findViewById(R.id.ib_spots_delete);

            edit.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            spotsChangeListener.onClick(view, spotId);
            /**
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
             */
        }
    }
}
