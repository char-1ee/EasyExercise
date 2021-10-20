package com.example.myapplication.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.sportsImage.SportsImage;

import java.util.List;

public class CheckInSportAdapter extends RecyclerView.Adapter<CheckInSportAdapter.MyViewHolder> {
    public Sport finalChoice;
    private Context context;
    private List<Sport> secondList;
    private int index = -1;
    private SportsImage sm;

    public CheckInSportAdapter(Context context, List<Sport> secondList) {
        this.context = context;
        this.secondList = secondList;
        this.sm = new SportsImage();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_radio, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Sport item = secondList.get(position);
        holder.tv_question_item.setText(item.getName());
        holder.iv_question_item.setImageResource(sm.SportsToImage(item));
        holder.rb_question_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    index = position;
                    notifyDataSetChanged();
                }
            }
        });
        if (index == position) {
            holder.rb_question_item.setChecked(true);
            finalChoice = secondList.get(index);
        } else {
            holder.rb_question_item.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return secondList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RadioButton rb_question_item;
        TextView tv_question_item;
        ImageView iv_question_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            rb_question_item = itemView.findViewById(R.id.check_in_sport_radio_button);
            tv_question_item = itemView.findViewById(R.id.check_in_sport_name);
            iv_question_item = itemView.findViewById(R.id.check_in_sport_image);
        }
    }

}

