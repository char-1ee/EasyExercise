package com.example.myapplication.ui.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.myapplication.beans.Location.LocationType;
import com.example.myapplication.beans.WorkoutPlan;
import com.example.myapplication.ui.activities.ViewPlanActivity;

import java.util.List;

public class PlanRecyclerViewAdapter extends RecyclerView.Adapter<PlanRecyclerViewAdapter.MyViewHolder> {
    private List<WorkoutPlan> mPlanList;
    private int lastSelectedPosition = -1;
    private WorkoutPlan chosenPlan;
    private Context mContext;

    public PlanRecyclerViewAdapter(Context context, List<WorkoutPlan> planList) {
        mPlanList = planList;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WorkoutPlan item = mPlanList.get(position);
        if (item.getLocation().getType() == LocationType.FACILITY) {
            Facility f = (Facility) item.getLocation();
            holder.locationView.setText(f.getName());
        } else {
            holder.locationView.setText("Customized Location");
        }
        holder.sportView.setText(item.getSport().getName());
        // TODO: image is no longer an attribute of a facility
        // TODO: 2021/10/1 for public/join plans, need to show their happening time(need additional info in WorkoutPlan)
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenPlan = item;
                Intent intent = new Intent(mContext, ViewPlanActivity.class);
                intent.putExtra("ChosenPlan", chosenPlan);
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mPlanList == null ? 0 : mPlanList.size();
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