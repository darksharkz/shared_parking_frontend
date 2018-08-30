package com.example.shared_parking.activities.profile;

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

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> {
    private List<JSONObject> cars;
    private CarsAdapter.CarsChangeListener carsChangeListener;
    private static final String TAG = "CarsAdapter";

    public interface CarsChangeListener {
        void onClick(View view, int spotId);
    }

    public CarsAdapter(List<JSONObject> cars, CarsAdapter.CarsChangeListener carsChangeListener) {
        this.cars = cars;
        this.carsChangeListener = carsChangeListener;
    }

    public void setItems(List<JSONObject> cars){
        this.cars = cars;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CarsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cars, parent, false);
        return new CarsAdapter.ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CarsAdapter.ViewHolder holder, int position) {
        JSONObject car = null;
        try {
            car = cars.get(position);
            holder.licenseplate.setText(car.getString("licenseplate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.carId = position;
        holder.carsChangeListener = carsChangeListener;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView licenseplate;
        public ImageButton edit, delete;
        public int carId;
        private CarsAdapter.CarsChangeListener carsChangeListener;

        public ViewHolder(View view) {
            super(view);
            Log.e(TAG, "create ViewHolder");
            licenseplate = (TextView) view.findViewById(R.id.list_cars_licenseplate);
            edit = (ImageButton) view.findViewById(R.id.ib_cars_edit);
            delete = (ImageButton) view.findViewById(R.id.ib_cars_delete);

            edit.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            carsChangeListener.onClick(view, carId);
        }
    }
}
