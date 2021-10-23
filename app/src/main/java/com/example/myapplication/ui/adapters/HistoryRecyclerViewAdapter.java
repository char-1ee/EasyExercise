package com.example.myapplication.ui.adapters;

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
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Location;
import com.example.myapplication.beans.WorkoutRecord;
import com.example.myapplication.sportsImage.SportsImage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.MyViewHolder> {

    private final List<WorkoutRecord> mWorkOutHistoryList;
    private final SportsImage sm;

    public HistoryRecyclerViewAdapter(List<WorkoutRecord> workOutHistoryList) {
        mWorkOutHistoryList = workOutHistoryList;
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
        final WorkoutRecord item = mWorkOutHistoryList.get(position);
        if (item.getLocation().getType() == Location.LocationType.FACILITY) {
            Facility f = (Facility) item.getLocation();
            holder.locationView.setText(f.getName());
        } else {
            holder.locationView.setText(R.string.customized_location);
        }
        holder.sportView.setText(item.getSport().getName());
        holder.dateView.setText(getTime(item.getStartTime()));
        holder.imageView.setImageResource(sm.SportsToImage(item.getSport()));
        holder.imageView.setClipToOutline(true);
        holder.planType.setText(item.getStatus().toString());
    }


    @Override
    public int getItemCount() {
        return mWorkOutHistoryList == null ? 0 : mWorkOutHistoryList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView sportView;
        private final TextView locationView;
        private final TextView dateView;
        private final ImageView imageView;
        private final CardView cardView;
        private final TextView planType;


        private MyViewHolder(View itemView) {
            super(itemView);
            planType = itemView.findViewById(R.id.plan_type);
            sportView = itemView.findViewById(R.id.plan_sport_name);
            locationView = itemView.findViewById(R.id.plan_location_name);
            dateView = itemView.findViewById(R.id.plan_date);
            imageView = itemView.findViewById(R.id.plan_sport_image);
            cardView = itemView.findViewById(R.id.history_card);
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}