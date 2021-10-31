package com.example.myapplication.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Workout;
import com.example.myapplication.beans.WorkoutPlan;
import com.example.myapplication.databases.WorkoutDatabaseManager;
import com.example.myapplication.sportsImage.SportsImage;
import com.example.myapplication.ui.activities.ViewPlanActivity;

import java.util.List;

public class PlanRecyclerViewAdapter extends RecyclerView.Adapter<PlanRecyclerViewAdapter.MyViewHolder> {
    private final List<Workout> mPlanList;
    private Workout chosenPlan;
    private final SportsImage sm;

    public interface OnRecyclerItemClickListener{
        void onRecyclerItemClick(Workout workout);
    }

    private OnRecyclerItemClickListener myListener;


    public void setRecyclerItemClickListener(OnRecyclerItemClickListener listener){
        myListener = listener;
    }

    public PlanRecyclerViewAdapter(List<Workout> planList, OnRecyclerItemClickListener listener) {
        mPlanList = planList;
        myListener = listener;
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
        Workout item = mPlanList.get(position);
        holder.dateView.setText("");
        holder.sportView.setText(item.getSport().getName());
        holder.locationView.setText(item.getLocation().getName());
        if(item.getStatus() == Workout.WorkoutStatus.PRIVATE){
            holder.typeView.setText("PRIVATE");
        }
        else{
            holder.typeView.setText("PUBLIC");
        }
        holder.imageView.setImageResource(sm.SportsToImage(item.getSport()));
        holder.imageView.setClipToOutline(true);
        Log.e("test", item.getLocation().getName());

        holder.bind(item, myListener);
    }

    @Override
    public int getItemCount() {
        return mPlanList == null ? 0 : mPlanList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView sportView;
        private final TextView dateView;
        private final TextView typeView;
        private final TextView locationView;
        private final ImageView imageView;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            sportView = itemView.findViewById(R.id.plan_sport_name);
            locationView = itemView.findViewById(R.id.plan_location_name);
            typeView = itemView.findViewById(R.id.plan_type);
            dateView = itemView.findViewById(R.id.plan_date);
            imageView = itemView.findViewById(R.id.plan_sport_image);
            CardView cardView = itemView.findViewById(R.id.history_card);
        }

        public void bind(Workout workout, PlanRecyclerViewAdapter.OnRecyclerItemClickListener myListener) {
            itemView.setOnClickListener(v -> myListener.onRecyclerItemClick(workout));
        }
    }
}