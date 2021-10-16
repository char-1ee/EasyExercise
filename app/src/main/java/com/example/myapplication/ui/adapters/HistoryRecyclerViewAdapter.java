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
import com.example.myapplication.beans.WorkoutRecord;

import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.MyViewHolder> {

    private List<WorkoutRecord> mWorkOutHistoryList;
    private int lastSelectedPosition = -1;

    public HistoryRecyclerViewAdapter(List<WorkoutRecord> workOutHistoryList) {
        mWorkOutHistoryList = workOutHistoryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final WorkoutRecord item = mWorkOutHistoryList.get(position);
        holder.locationView.setText("null");
        holder.sportView.setText(item.getSport().getName());
        holder.dateView.setText(item.getStartTime().toString());
//        holder.imageView.setImageResource(item.getSport().getImage());
        //holder.planType.setText(String.valueOf(item.isPublic()));
        //should pass in the status of WorkoutPlan instead of WorkoutHistoryItem isPublic(which has been deleted)?
    }


    @Override
    public int getItemCount() {
        return mWorkOutHistoryList == null ? 0 : mWorkOutHistoryList.size();
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