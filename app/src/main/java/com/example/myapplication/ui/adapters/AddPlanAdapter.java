package com.example.myapplication.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.sportsImage.SportsImage;

import java.util.ArrayList;
import java.util.List;

public class AddPlanAdapter extends RecyclerView.Adapter<AddPlanAdapter.MyViewHolder> {
    public Sport finalChoice;
    private final Context context;
    private final List<Sport> sportList;
    private int lastCheckedPos = -1;
    private final Facility facility;
    private final SportsImage sm;

    public AddPlanAdapter(Context context, Facility facility) {
        this.context = context;
        this.facility = facility;
        this.sportList = new ArrayList<>(facility.getSports());
        this.sm = new SportsImage();
    }

    public Sport getFinalChoice() {
        return finalChoice;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_radio, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Sport item = sportList.get(position);
        holder.tv_question_item.setText(item.getName());
        holder.iv_question_item.setImageResource(sm.SportsToImage(item));
        holder.rb_question_item.setChecked(position == lastCheckedPos);
    }

    @Override
    public int getItemCount() {
        return sportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RadioButton rb_question_item;
        TextView tv_question_item;
        ImageView iv_question_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            rb_question_item = itemView.findViewById(R.id.check_in_sport_radio_button);
            tv_question_item = itemView.findViewById(R.id.check_in_sport_name);
            iv_question_item = itemView.findViewById(R.id.check_in_sport_image);

            rb_question_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int copyOfLastCheckedPos = lastCheckedPos;
                    lastCheckedPos = getAdapterPosition();
                    notifyItemChanged(copyOfLastCheckedPos);
                    notifyItemChanged(lastCheckedPos);
                }
            });
        }
    }
}
