package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters;

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

import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.util.SportsImageMatcher;

public class CheckInSportAdapter extends RecyclerView.Adapter<CheckInSportAdapter.MyViewHolder> {
    public final List<Sport> secondList;
    private final Context context;
    public Sport finalChoice;
    public int lastCheckedPos = -1;
    private AdapterView.OnItemClickListener onItemClickListener;

    public CheckInSportAdapter(Context context, List<Sport> secondList) {
        this.context = context;
        this.secondList = secondList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_radio, parent, false);
        return new MyViewHolder(inflate, this);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Sport item = secondList.get(position);
        holder.tv_question_item.setText(item.getName());
        holder.iv_question_item.setImageResource(SportsImageMatcher.getImage(item));
        holder.iv_question_item.setClipToOutline(true);
        holder.rb_question_item.setChecked(position == lastCheckedPos);
    }

    @Override
    public int getItemCount() {
        return secondList.size();
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RadioButton rb_question_item;
        TextView tv_question_item;
        ImageView iv_question_item;
        CheckInSportAdapter mAdapter;

        public MyViewHolder(View itemView, CheckInSportAdapter mAdapter) {
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
            lastCheckedPos = getAdapterPosition();
            finalChoice = secondList.get(lastCheckedPos);
            notifyItemRangeChanged(0, secondList.size());
            mAdapter.onItemHolderClick(MyViewHolder.this);
        }
    }
}
