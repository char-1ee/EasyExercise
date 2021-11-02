package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.database.SportAndFacilityDBHelper;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.database.WorkoutDatabaseManager;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.util.SportsImageMatcher;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder> {

    private final List<WorkoutDatabaseManager.FirebasePublicWorkoutPlan> myPlanList;

    public interface OnRecyclerItemClickListener{
         void onRecyclerItemClick(WorkoutDatabaseManager.FirebasePublicWorkoutPlan firebasePublicWorkoutPlan);
    }

    private OnRecyclerItemClickListener myListener;

    public void setRecyclerItemClickListener(OnRecyclerItemClickListener listener){
        myListener = listener;
    }

    public CommunityAdapter(List<WorkoutDatabaseManager.FirebasePublicWorkoutPlan> planList, OnRecyclerItemClickListener listener){
        myPlanList = planList;
        myListener = listener;
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

        public void bind(WorkoutDatabaseManager.FirebasePublicWorkoutPlan firebasePublicWorkoutPlan, OnRecyclerItemClickListener myListener) {
            itemView.setOnClickListener(v -> myListener.onRecyclerItemClick(firebasePublicWorkoutPlan));
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
        final WorkoutDatabaseManager.FirebasePublicWorkoutPlan firebasePublicWorkoutPlan = myPlanList.get(position);
        SportAndFacilityDBHelper manager = new SportAndFacilityDBHelper(holder.sportImage.getContext());
        manager.openDatabase();
        Sport sport = manager.getSportById(firebasePublicWorkoutPlan.getSportID());
        Facility facility = manager.getFacilityById(firebasePublicWorkoutPlan.getFacilityID());
        manager.closeDatabase();
        holder.sportImage.setImageResource(SportsImageMatcher.getImage(sport));
        holder.date.setText(getTime(new Date(firebasePublicWorkoutPlan.getPlanStart())));
        holder.facility.setText(facility.getName());
        holder.limit.setText( "Limit: " + firebasePublicWorkoutPlan.getMembers().size() + "/" + firebasePublicWorkoutPlan.getPlanLimit());

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
