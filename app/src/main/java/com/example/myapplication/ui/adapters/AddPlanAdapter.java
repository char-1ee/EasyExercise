package com.example.myapplication.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.sportsImage.SportsImage;
import com.example.myapplication.ui.activities.AddPlanActivity;

import java.util.ArrayList;
import java.util.List;

public class AddPlanAdapter extends RecyclerView.Adapter<AddPlanAdapter.MyViewHolder> {
    public Sport finalChoice;
    private final Context context;
    public final List<Sport> sportList;
    public int index = -1;
    private final Facility facility;
    private final SportsImage sm;
    private AdapterView.OnItemClickListener onItemClickListener;

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
        return new MyViewHolder(inflate, this);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Sport item = sportList.get(position);
        holder.tv_question_item.setText(item.getName());
        holder.iv_question_item.setImageResource(sm.SportsToImage(item));
        holder.iv_question_item.setClipToOutline(true);
        holder.rb_question_item.setChecked(position == index);
    }

    @Override
    public int getItemCount() {
        return sportList.size();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener
                                               onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * When user clicks our itemView, we still invoke the onItemClick
     * @param holder
     */
    public void onItemHolderClick(MyViewHolder holder) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(null, holder.itemView,
                    holder.getAdapterPosition(), holder.getItemId());
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener{
        RadioButton rb_question_item;
        TextView tv_question_item;
        ImageView iv_question_item;
        AddPlanAdapter mAdapter;

        public MyViewHolder(View itemView, AddPlanAdapter mAdapter) {
            super(itemView);
            this.mAdapter= mAdapter;
            rb_question_item = itemView.findViewById(R.id.check_in_sport_radio_button);
            tv_question_item = itemView.findViewById(R.id.check_in_sport_name);
            iv_question_item = itemView.findViewById(R.id.check_in_sport_image);
            itemView.setOnClickListener(this);
            rb_question_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            index = getAdapterPosition();
            finalChoice= sportList.get(index);
            notifyItemRangeChanged(0, sportList.size());
            mAdapter.onItemHolderClick(MyViewHolder.this);
        }
    }
}
