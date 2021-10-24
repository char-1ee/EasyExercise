package com.example.myapplication.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.PublicPlan;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.databases.SportAndFacilityDBHelper;
import com.example.myapplication.sportsImage.SportsImage;

import java.util.Date;
import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder> {

    private final List<PublicPlan> myPlanList;
    private final SportsImage image;

    public interface OnRecyclerItemClickListener{
         void onRecyclerItemClick(PublicPlan publicPlan);
    }

    private OnRecyclerItemClickListener myListener;

    public void setRecyclerItemClickListener(OnRecyclerItemClickListener listener){
        myListener = listener;
    }

    public CommunityAdapter(List<PublicPlan> planList, OnRecyclerItemClickListener listener){
        myPlanList = planList;
        myListener = listener;
        image = new SportsImage();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView facility;
        ImageView sportImage;
        TextView limit;
        Button joinButton;

        public ViewHolder(View view)
        {
            super(view);
            date = view.findViewById(R.id.planDate);
            facility = view.findViewById(R.id.planLocation);
            sportImage = view.findViewById(R.id.planPic);
            limit = view.findViewById(R.id.planLimit);

        }

        public void bind(PublicPlan publicPlan, OnRecyclerItemClickListener myListener) {
            itemView.setOnClickListener(v -> myListener.onRecyclerItemClick(publicPlan));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_plan_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PublicPlan publicPlan = myPlanList.get(position);
        SportAndFacilityDBHelper manager = new SportAndFacilityDBHelper(holder.sportImage.getContext());
        manager.openDatabase();
        Sport sport = manager.getSportById(publicPlan.getSport());
        Facility facility = manager.getFacilityById(publicPlan.getFacility());
        manager.closeDatabase();
        holder.sportImage.setImageResource(image.SportsToImage(sport));
        holder.date.setText(new Date(publicPlan.getPlanStart()).toString());
        holder.facility.setText(facility.getName());
        holder.limit.setText( "Limit: " + publicPlan.getMembers().size() + "/" +publicPlan.getPlanLimit());

        holder.bind(myPlanList.get(position), myListener);
    }


    @Override
    public int getItemCount(){
        return myPlanList.size();
    }
}
