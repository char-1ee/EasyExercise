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
import com.example.myapplication.beans.Location;
import com.example.myapplication.beans.WorkoutRecord;
import com.example.myapplication.sportsImage.SportsImage;

import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.MyViewHolder> {

    private List<WorkoutRecord> mWorkOutHistoryList;
    private SportsImage sm;

    public HistoryRecyclerViewAdapter(List<WorkoutRecord> workOutHistoryList) {
        mWorkOutHistoryList = workOutHistoryList;
        sm = new SportsImage();
    }

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
            holder.locationView.setText("Customized Location");
        }
        holder.sportView.setText(item.getSport().getName());
        holder.dateView.setText(item.getStartTime().toString());
        holder.imageView.setImageResource(sm.SportsToImage(item.getSport()));
        holder.planType.setText(String.valueOf(item.getStatus().toString()));
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