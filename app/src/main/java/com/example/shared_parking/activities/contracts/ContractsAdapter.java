package com.example.shared_parking.activities.contracts;

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

public class ContractsAdapter extends RecyclerView.Adapter<ContractsAdapter.ViewHolder> {
    private List<JSONObject> parkingTrades;
    private ContractsAdapter.ContractsChangeListener contractsChangeListener;
    private static final String TAG = "ContractsAdapter";

    public interface ContractsChangeListener {
        void onClick(View view, int spotId);
    }

    public ContractsAdapter(List<JSONObject> parkingTrades, ContractsAdapter.ContractsChangeListener contractsChangeListener) {
        this.parkingTrades = parkingTrades;
        this.contractsChangeListener = contractsChangeListener;
    }

    public void setItems(List<JSONObject> parkingTrades){
        this.parkingTrades = parkingTrades;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContractsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contracts, parent, false);
        return new ContractsAdapter.ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ContractsAdapter.ViewHolder holder, int position) {
        JSONObject parkingContract = null;
        try {
            parkingContract = parkingTrades.get(position);
            Log.e(TAG, "parkingcontract: " + parkingContract);
            holder.contractpartnerid.setText(parkingContract.getString("contractpartnerid"));
            holder.price.setText(parkingContract.getString("price"));
            holder.start_dt.setText(parkingContract.getString("start_dt"));
            holder.end_dt.setText(parkingContract.getString("end_dt"));
            holder.postCode.setText(parkingContract.getString("postcode"));
            holder.city.setText(parkingContract.getString("city"));
            holder.street.setText(parkingContract.getString("street"));
            holder.number.setText(parkingContract.getString("number"));
            holder.lat.setText(parkingContract.getString("lat"));
            holder.lng.setText(parkingContract.getString("lng"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.contractsId = position;
        holder.contractsChangeListener = contractsChangeListener;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return parkingTrades.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView contractpartnerid, price, start_dt, end_dt, postCode, city, street, number, lat, lng;
        public ImageButton ratextend;
        public int contractsId;
        private ContractsAdapter.ContractsChangeListener contractsChangeListener;

        public ViewHolder(View view) {
            super(view);
            contractpartnerid = (TextView) view.findViewById(R.id.list_contracts_partnerid);
            price = (TextView) view.findViewById(R.id.list_contracts_price);
            start_dt = (TextView) view.findViewById(R.id.list_contracts_startdt);
            end_dt = (TextView) view.findViewById(R.id.list_contracts_enddt);
            postCode = (TextView) view.findViewById(R.id.list_contracts_postcode);
            city = (TextView) view.findViewById(R.id.list_contracts_city);
            street = (TextView) view.findViewById(R.id.list_contracts_street);
            number = (TextView) view.findViewById(R.id.list_contracts_number);
            lat = (TextView) view.findViewById(R.id.list_contracts_lat);
            lng = (TextView) view.findViewById(R.id.list_contracts_lng);
            ratextend = (ImageButton) view.findViewById(R.id.ib_contracts_ratextend);

            ratextend.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            contractsChangeListener.onClick(view, contractsId);
        }
    }
}