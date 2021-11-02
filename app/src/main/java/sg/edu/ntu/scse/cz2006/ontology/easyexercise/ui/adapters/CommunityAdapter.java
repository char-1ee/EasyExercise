package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.databases.SportAndFacilityDBHelper;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.databases.WorkoutDatabaseManager;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.sportsImage.SportsImage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder> {

    private final List<WorkoutDatabaseManager.FirebasePublicPlan> myPlanList;
    private final SportsImage image;

    public interface OnRecyclerItemClickListener{
         void onRecyclerItemClick(WorkoutDatabaseManager.FirebasePublicPlan firebasePublicPlan);
    }

    private OnRecyclerItemClickListener myListener;

    public void setRecyclerItemClickListener(OnRecyclerItemClickListener listener){
        myListener = listener;
    }

    public CommunityAdapter(List<WorkoutDatabaseManager.FirebasePublicPlan> planList, OnRecyclerItemClickListener listener){
        myPlanList = planList;
        myListener = listener;
        image = new SportsImage();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView facility;
        ImageView sportImage;
        TextView limit;

        public ViewHolder(View view)
        {
            super(view);
            date = view.findViewById(R.id.planDate);
            facility = view.findViewById(R.id.planLocation);
            sportImage = view.findViewById(R.id.planPic);
            limit = view.findViewById(R.id.planLimit);

        }

        public void bind(WorkoutDatabaseManager.FirebasePublicPlan firebasePublicPlan, OnRecyclerItemClickListener myListener) {
            itemView.setOnClickListener(v -> myListener.onRecyclerItemClick(firebasePublicPlan));
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
        final WorkoutDatabaseManager.FirebasePublicPlan firebasePublicPlan = myPlanList.get(position);
        SportAndFacilityDBHelper manager = new SportAndFacilityDBHelper(holder.sportImage.getContext());
        manager.openDatabase();
        Sport sport = manager.getSportById(firebasePublicPlan.getSport());
        Facility facility = manager.getFacilityById(firebasePublicPlan.getFacility());
        manager.closeDatabase();
        holder.sportImage.setImageResource(image.SportsToImage(sport));
        holder.date.setText(getTime(new Date(firebasePublicPlan.getPlanStart())));
        holder.facility.setText(facility.getName());
        holder.limit.setText( "Limit: " + firebasePublicPlan.getMembers().size() + "/" + firebasePublicPlan.getPlanLimit());

        holder.bind(myPlanList.get(position), myListener);
    }


    @Override
    public int getItemCount(){
        return myPlanList.size();
    }

    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}
