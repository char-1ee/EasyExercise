package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.MyViewHolder> {

    private List<History> mHistoryList;
    private int lastSelectedPosition = -1;

    public HistoryRecyclerViewAdapter(List<History> historyList) {
        mHistoryList = historyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final History history= mHistoryList.get(position);
        holder.facilityView.setText(history.getFacility());
        holder.sportView.setText(history.getSport());
        holder.durationView.setText(Double.toString(history.getDuration()));
        holder.imageView.setImageResource(history.getImage());
    }


    @Override
    public int getItemCount() {
        return mHistoryList == null ? 0 : mHistoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView sportView;
        private TextView facilityView;
        private TextView durationView;
        private ImageView imageView;
        private CardView cardView;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            sportView = (TextView) itemView.findViewById(R.id.select_facility_name2);
            facilityView=(TextView) itemView.findViewById(R.id.history_facility);
            durationView=(TextView)itemView.findViewById(R.id.select_facility_distance2);
            imageView= (ImageView)itemView.findViewById(R.id.select_facility_image2);
            cardView= (CardView)itemView.findViewById(R.id.history_card);
        }
    }
}