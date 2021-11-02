package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities.CheckInNormalActivity;

import java.io.Serializable;
import java.util.List;

public class FacilityRecyclerViewAdapterCheckIn extends RecyclerView.Adapter<FacilityRecyclerViewAdapterCheckIn.MyViewHolder> {
    private Facility ChosenFacility;
    private final List<Facility> mFacilityList;
    private final Context mContext;
    private final Coordinates mCurrentCoordinates;

    public FacilityRecyclerViewAdapterCheckIn(Context context, List<Facility> facilityList, Coordinates currentCoordinates) {
        mFacilityList = facilityList;
        mContext = context;
        mCurrentCoordinates= currentCoordinates;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facility_item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Facility facility = mFacilityList.get(position);
        holder.mSelectFacilityName.setText(facility.getName());
        holder.mSelectFacilityDistance.setText(Math.round(facility.getCoordinates().getDistance(mCurrentCoordinates) * 100.0) / 100.0 + "km");
        holder.view.setOnClickListener(view -> {
            notifyDataSetChanged();
            ChosenFacility = mFacilityList.get(position);
            Intent intent = new Intent(mContext, CheckInNormalActivity.class);
            intent.putExtra("ClosestFacility", ChosenFacility);
            intent.putExtra("FacilityByDistance",(Serializable)mFacilityList);
            intent.putExtra("latitude1", mCurrentCoordinates.getLatitude());
            intent.putExtra("longitude1", mCurrentCoordinates.getLongitude());
            mContext.startActivity(intent);
            ((Activity)mContext).finish();
        });


    }

    @Override
    public int getItemCount() {
        return mFacilityList == null ? 0 : mFacilityList.size();
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

