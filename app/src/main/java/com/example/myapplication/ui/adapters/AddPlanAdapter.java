package com.example.myapplication.ui.adapters;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;

public class AddPlanAdapter extends RecyclerView.Adapter<AddPlanAdapter.MyViewHolder> {
    public Sport finalChoice;
    private Context context;
    private ArrayList<Sport> secondList;
    private int index = -1;

    public AddPlanAdapter(Context context, ArrayList<Sport> secondList) {
        this.context = context;
        this.secondList = secondList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_radio, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.tv_question_item.setText(secondList.get(position).getName());
        holder.iv_question_item.setImageResource(secondList.get(position).getImage());
        holder.rb_question_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    Toast.makeText(context, "Option " + secondList.get(position) + " selected", Toast.LENGTH_SHORT).show();
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
