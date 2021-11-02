package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities.CheckInNormalActivity;

public class FacilityRecyclerViewAdapterCheckIn extends RecyclerView.Adapter<FacilityRecyclerViewAdapterCheckIn.MyViewHolder> {
    private final List<Facility> facilities;
    private final Context context;
    private final Coordinates currentCoordinates;
    private Facility facilityChosen;

    public FacilityRecyclerViewAdapterCheckIn(Context context, List<Facility> facilityList, Coordinates currentCoordinates) {
        facilities = facilityList;
        this.context = context;
        this.currentCoordinates = currentCoordinates;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.facility_item_row, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Facility facility = facilities.get(position);
        holder.mSelectFacilityName.setText(facility.getName());
        holder.mSelectFacilityDistance.setText(
                String.format("%.2fkm", facility.getCoordinates().getDistance(currentCoordinates)));
        holder.view.setOnClickListener(view -> {
            notifyDataSetChanged();
            facilityChosen = facilities.get(position);
            Intent intent = new Intent(context, CheckInNormalActivity.class);
            intent.putExtra("ClosestFacility", facilityChosen);
            intent.putExtra("FacilityByDistance", (Serializable) facilities);
            intent.putExtra("latitude1", currentCoordinates.getLatitude());
            intent.putExtra("longitude1", currentCoordinates.getLongitude());
            context.startActivity(intent);
            ((Activity) context).finish();
        });
    }

    @Override
    public int getItemCount() {
        return facilities == null ? 0 : facilities.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView mSelectFacilityName;
        private final TextView mSelectFacilityDistance;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            mSelectFacilityDistance = itemView.findViewById(R.id.plan_date);
            mSelectFacilityName = itemView.findViewById(R.id.plan_sport_name);
        }
    }
}
