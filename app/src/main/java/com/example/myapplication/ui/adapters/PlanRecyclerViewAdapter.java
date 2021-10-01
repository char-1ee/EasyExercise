package com.example.myapplication.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.WorkoutRecord;

import java.util.List;

public class PlanRecyclerViewAdapter extends RecyclerView.Adapter<PlanRecyclerViewAdapter.MyViewHolder> {
    private List<WorkoutRecord> mPlanList;
    private int lastSelectedPosition = -1;

    public PlanRecyclerViewAdapter(List<WorkoutRecord> planList) {
        mPlanList = planList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final WorkoutRecord item = mPlanList.get(position);
        //holder.facilityView.setText(item.getFacility().getName());
        if(item.getLocation().isFacility()== true){
            Facility f= (Facility)item.getLocation();
            holder.locationView.setText(f.getName());
        }
        else{
            holder.locationView.setText("what should I put here?");
        }
        holder.sportView.setText(item.getSport().getName());
        //holder.dateView.setText(item.getDate().toString());
        holder.imageView.setImageResource(item.getSport().getImage());
        holder.planType.setText(item.getStatus().toString());
        // TODO: 2021/10/1 for public/join plans, need to show their happening time(need additional info in WorkoutPlan)
    }


    @Override
    public int getItemCount() {
        return mPlanList == null ? 0 : mPlanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView sportView;
        private TextView locationView;
        private TextView dateView;
        private ImageView imageView;
        private CardView cardView;
        private TextView planType;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            planType = (TextView) itemView.findViewById(R.id.plan_type);
            sportView = (TextView) itemView.findViewById(R.id.plan_sport_name);
            locationView = (TextView) itemView.findViewById(R.id.plan_location_name);
            dateView = (TextView) itemView.findViewById(R.id.plan_date);
            imageView = (ImageView) itemView.findViewById(R.id.plan_sport_image);
            cardView = (CardView) itemView.findViewById(R.id.history_card);
        }
    }
}