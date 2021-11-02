package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities.AddPlanActivity;

public class FacilityRecyclerViewAdapterPlan extends RecyclerView.Adapter<FacilityRecyclerViewAdapterPlan.MyViewHolder> {

    private final List<Facility> mFacilityList;
    private final Context mContext;
    private final Coordinates mCurrentCoordinates;
    private Facility chosenFacility;

    public FacilityRecyclerViewAdapterPlan(Context context, List<Facility> facilityList, Coordinates currentCoordinates) {
        mFacilityList = facilityList;
        mContext = context;
        mCurrentCoordinates = currentCoordinates;
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

        holder.mSelectFacilityDistance.setText(String.valueOf(Math.round(facility.getCoordinates().getDistance(mCurrentCoordinates) * 100.0) / 100.0 + "km"));
        holder.view.setOnClickListener(view -> {
            chosenFacility = facility;
            Intent intent = new Intent(mContext, AddPlanActivity.class);
            intent.putExtra("ChosenFacility", chosenFacility);
            mContext.startActivity(intent);
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
