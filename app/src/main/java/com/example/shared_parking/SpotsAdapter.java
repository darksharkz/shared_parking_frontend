package com.example.shared_parking;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

class SpotsAdapter extends RecyclerView.Adapter<SpotsAdapter.ViewHolder> {
    private List<ParkingSpace> parkingSpaces;
    private SpotsChangeListener spotsChangeListener;

    public interface SpotsChangeListener {
        void onClick(View view, int spotId);
    }

    public SpotsAdapter(List<ParkingSpace> parkingSpaces, SpotsChangeListener spotsChangeListener) {
        this.parkingSpaces = parkingSpaces;
        this.spotsChangeListener = spotsChangeListener;
        //this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SpotsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_spots, parent, false);

        return new SpotsAdapter.ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ParkingSpace parkingSpace = parkingSpaces.get(position);
        holder.postCode.setText(String.valueOf(parkingSpace.getAddress().getPostCode()));
        holder.city.setText(parkingSpace.getAddress().getCity());
        holder.street.setText(parkingSpace.getAddress().getStreet());
        holder.number.setText(String.valueOf(parkingSpace.getAddress().getNumber()));
        holder.lat.setText(String.valueOf(parkingSpace.getLat()));
        holder.lng.setText(String.valueOf(parkingSpace.getLng()));
        holder.spotId = parkingSpace.getId();
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
            postCode = (TextView) view.findViewById(R.id.list_spots_postCode);
            city = (TextView) view.findViewById(R.id.list_spots_city);
            street = (TextView) view.findViewById(R.id.list_spots_street);
            number = (TextView) view.findViewById(R.id.list_spots_number);
            lat = (TextView) view.findViewById(R.id.list_spots_lat);
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
