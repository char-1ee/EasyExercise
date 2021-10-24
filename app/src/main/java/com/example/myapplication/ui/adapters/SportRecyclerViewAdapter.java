package com.example.myapplication.ui.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.sportsImage.SportsImage;
import com.example.myapplication.utils.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class SportRecyclerViewAdapter extends RecyclerView.Adapter<SportRecyclerViewAdapter.MyViewHolder> {
    private final List<Sport> mSportList;
    public List<Sport> chosenSportList;
    private final SportsImage sm;
    private List<MenuItem<Sport>> sportMenuItem;

    public SportRecyclerViewAdapter(List<Sport> sportList) {
        chosenSportList = new ArrayList<>();
        mSportList = sportList;
        sportMenuItem = new ArrayList<>();
        for (Sport s : mSportList) {
            MenuItem<Sport> item = new MenuItem<>(s);
            sportMenuItem.add(item);
        }
        sm = new SportsImage();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MenuItem<Sport> item = sportMenuItem.get(position);
        //final Sport sport = mSportList.get(position);
        Sport sport = item.get();
        holder.textView.setText(sport.getName());
        holder.imageView.setImageResource(sm.SportsToImage(sport));
        holder.imageView.setClipToOutline(true);
        holder.cardView.setBackgroundColor(item.isSelected() ? Color.CYAN : Color.WHITE);
        holder.view.setOnClickListener(view -> {
            item.setSelected(!item.isSelected());
            holder.cardView.setBackgroundColor(item.isSelected() ? Color.parseColor("#b2dfdb") : Color.WHITE);
            if (item.isSelected()) {
                chosenSportList.add(sport);
            } else {
                boolean e = chosenSportList.remove(sport);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSportList == null ? 0 : mSportList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView textView;
        private final ImageView imageView;
        private final CardView cardView;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            cardView = itemView.findViewById(R.id.sportView);
            textView = itemView.findViewById(R.id.sport_title);
            imageView = itemView.findViewById(R.id.sport_image);
        }
    }
}
