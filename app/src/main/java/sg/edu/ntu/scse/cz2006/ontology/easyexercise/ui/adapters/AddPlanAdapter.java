package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters;

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

import java.util.ArrayList;
import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.util.SportsImageMatcher;

public class AddPlanAdapter extends RecyclerView.Adapter<AddPlanAdapter.MyViewHolder> {
    public final List<Sport> sportList;
    private final Context context;
    public Sport finalChoice;
    public int index = -1;
    private AdapterView.OnItemClickListener onItemClickListener;

    public AddPlanAdapter(Context context, Facility facility) {
        this.context = context;
        this.sportList = new ArrayList<>(facility.getSports());
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
        holder.iv_question_item.setImageResource(SportsImageMatcher.getImage(item));
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
     * When user clicks our itemView, we still invoke {@code onItemClick}.
     *
     * @param holder TODO: What is this?
     */
    public void onItemHolderClick(MyViewHolder holder) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(null, holder.itemView,
                    holder.getAdapterPosition(), holder.getItemId());
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        RadioButton rb_question_item;
        TextView tv_question_item;
        ImageView iv_question_item;
        AddPlanAdapter mAdapter;

        public MyViewHolder(View itemView, AddPlanAdapter mAdapter) {
            super(itemView);
            this.mAdapter = mAdapter;
            rb_question_item = itemView.findViewById(R.id.check_in_sport_radio_button);
            tv_question_item = itemView.findViewById(R.id.check_in_sport_name);
            iv_question_item = itemView.findViewById(R.id.check_in_sport_image);
            itemView.setOnClickListener(this);
            rb_question_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            index = getAdapterPosition();
            finalChoice = sportList.get(index);
            notifyItemRangeChanged(0, sportList.size());
            mAdapter.onItemHolderClick(MyViewHolder.this);
        }
    }
}
