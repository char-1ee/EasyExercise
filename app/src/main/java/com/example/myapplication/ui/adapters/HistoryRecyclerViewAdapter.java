package com.example.myapplication.ui.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Location;
import com.example.myapplication.beans.WorkoutRecord;
import com.example.myapplication.sportsImage.SportsImage;
import com.example.myapplication.utils.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private final List<WorkoutRecord> mWorkOutHistoryList;
    private final SportsImage sm;

    public HistoryRecyclerViewAdapter(List<WorkoutRecord> workOutHistoryList, Context context) {
        mWorkOutHistoryList = workOutHistoryList;
        sm = new SportsImage();
        mContext = context;
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
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(mContext);
                TextView sportView, facilityView, durationView, startTimeView, endTimeView;
                ImageView imageView;
                dialog.setContentView(R.layout.dialog_history);
                sportView = dialog.findViewById(R.id.sport_view);
                imageView = dialog.findViewById(R.id.history_sport_image);
                facilityView = dialog.findViewById(R.id.location_view);
                durationView = dialog.findViewById(R.id.duration_view);
                startTimeView = dialog.findViewById(R.id.start_time_view);
                endTimeView = dialog.findViewById(R.id.end_time_view);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                sportView.setText(item.getSport().getName());
                if (item.getLocation().getType() == Location.LocationType.FACILITY) {
                    Facility f = (Facility) item.getLocation();
                    facilityView.setText(f.getName());
                } else {
                    facilityView.setText(R.string.customized_location);
                }
                imageView.setImageResource(sm.SportsToImage(item.getSport()));
                long diff = item.getStartTime().getTime() - item.getEndTime().getTime();
                long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                durationView.setText(String.valueOf(minutes) + "min" + String.valueOf(seconds) + "sec");
                startTimeView.setText(getTime(item.getStartTime()));
                endTimeView.setText(getTime(item.getEndTime()));
                dialog.show();
            }
        });
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
        private TextView planType;


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

    private String getTime(Date date) {
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }


}