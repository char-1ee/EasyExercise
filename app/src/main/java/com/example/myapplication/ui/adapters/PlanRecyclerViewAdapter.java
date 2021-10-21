package com.example.myapplication.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.WorkoutPlan;
import com.example.myapplication.sportsImage.SportsImage;
import com.example.myapplication.ui.activities.ViewPlanActivity;

import java.util.List;

public class PlanRecyclerViewAdapter extends RecyclerView.Adapter<PlanRecyclerViewAdapter.MyViewHolder> {
    private final List<WorkoutPlan> mPlanList;
    private WorkoutPlan chosenPlan;
    private final Context mContext;
    private final SportsImage sm;

    public PlanRecyclerViewAdapter(Context context, List<WorkoutPlan> planList) {
        mPlanList = planList;
        mContext = context;
        sm = new SportsImage();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WorkoutPlan item = mPlanList.get(position);
        holder.dateView.setText("");
        holder.sportView.setText(item.getSport().getName());
        holder.imageView.setImageResource(sm.SportsToImage(item.getSport()));
        holder.view.setOnClickListener(view -> {
            chosenPlan = item;
            Intent intent = new Intent(mContext, ViewPlanActivity.class);
            intent.putExtra("ChosenPlan", chosenPlan);
            mContext.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return mPlanList == null ? 0 : mPlanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView sportView;
        private final TextView locationView;
        private final TextView dateView;
        private final ImageView imageView;
        private final CardView cardView;
        private final TextView planType;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            planType = itemView.findViewById(R.id.plan_type);
            sportView = itemView.findViewById(R.id.plan_sport_name);
            locationView = itemView.findViewById(R.id.plan_location_name);
            dateView = itemView.findViewById(R.id.plan_date);
            imageView = itemView.findViewById(R.id.plan_sport_image);
            cardView = itemView.findViewById(R.id.history_card);
        }
    }
}