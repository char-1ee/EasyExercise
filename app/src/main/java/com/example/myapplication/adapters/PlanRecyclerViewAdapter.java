package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.WorkoutHistoryItem;

import java.util.List;

public class PlanRecyclerViewAdapter extends RecyclerView.Adapter<PlanRecyclerViewAdapter.MyViewHolder> {
    private List<WorkoutHistoryItem> mPlanList;
    private int lastSelectedPosition = -1;

    public PlanRecyclerViewAdapter(List<WorkoutHistoryItem> planList) {
        mPlanList = planList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final WorkoutHistoryItem workOutHistory = mPlanList.get(position);
        holder.facilityView.setText(workOutHistory.getFacility().getName());
        holder.sportView.setText(workOutHistory.getSport().getText());
        holder.dateView.setText(workOutHistory.getDate().toString());
        holder.imageView.setImageResource(workOutHistory.getSport().getImage());
        holder.planType.setText(String.valueOf(workOutHistory.isPublic()));
    }


    @Override
    public int getItemCount() {
        return mPlanList == null ? 0 : mPlanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView sportView;
        private TextView facilityView;
        private TextView dateView;
        private ImageView imageView;
        private CardView cardView;
        private TextView planType;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            planType = (TextView) itemView.findViewById(R.id.plan_type);
            sportView = (TextView) itemView.findViewById(R.id.plan_sport_name);
            facilityView = (TextView) itemView.findViewById(R.id.plan_facility_name);
            dateView = (TextView) itemView.findViewById(R.id.plan_date);
            imageView = (ImageView) itemView.findViewById(R.id.plan_sport_image);
            cardView = (CardView) itemView.findViewById(R.id.history_card);
        }
    }
}