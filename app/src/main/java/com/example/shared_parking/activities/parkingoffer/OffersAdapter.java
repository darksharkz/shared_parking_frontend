package com.example.shared_parking.activities.parkingoffer;

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

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {
    private List<JSONObject> parkingOffers;
    private OffersAdapter.OffersChangeListener offersChangeListener;
    private static final String TAG = "SpotsAdapter";

    public interface OffersChangeListener {
        void onClick(View view, int spotId);
    }

    public OffersAdapter(List<JSONObject> parkingOffers, OffersAdapter.OffersChangeListener offersChangeListener) {
        this.parkingOffers = parkingOffers;
        this.offersChangeListener = offersChangeListener;
    }

    public void setItems(List<JSONObject> parkingOffers){
        this.parkingOffers = parkingOffers;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OffersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_offers, parent, false);
        return new OffersAdapter.ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(OffersAdapter.ViewHolder holder, int position) {
        JSONObject parkingOffer = null;
        try {
            parkingOffer = parkingOffers.get(position);
            holder.parkingspaceid.setText(parkingOffer.getString("parkingspaceid"));
            holder.price.setText(parkingOffer.getString("price"));
            holder.start_dt.setText(parkingOffer.getString("start_dt"));
            holder.end_dt.setText(parkingOffer.getString("end_dt"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.offerId = position;
        holder.offersChangeListener = offersChangeListener;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return parkingOffers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView parkingspaceid, price, start_dt, end_dt;
        public ImageButton edit, delete;
        public int offerId;
        private OffersAdapter.OffersChangeListener offersChangeListener;

        public ViewHolder(View view) {
            super(view);
            Log.e(TAG, "create ViewHolder");
            parkingspaceid = (TextView) view.findViewById(R.id.list_offers_parkingspaceid);
            price = (TextView) view.findViewById(R.id.list_offers_price);
            start_dt = (TextView) view.findViewById(R.id.list_offers_startdt);
            end_dt = (TextView) view.findViewById(R.id.list_offers_enddt);
            edit = (ImageButton) view.findViewById(R.id.ib_offers_edit);
            delete = (ImageButton) view.findViewById(R.id.ib_offers_delete);

            edit.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            offersChangeListener.onClick(view, offerId);
        }
    }
}
