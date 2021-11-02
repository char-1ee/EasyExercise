package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities.SelectSportActivity;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.util.Selectable;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.util.SportsImageMatcher;

public class SportRecyclerViewAdapter extends RecyclerView.Adapter<SportRecyclerViewAdapter.MyViewHolder> {
    private final List<Sport> sports;
    public final List<Sport> chosenSportList;
    private final List<Selectable<Sport>> sportSelectable;

    public SportRecyclerViewAdapter(List<Sport> sportList) {
        chosenSportList = new ArrayList<>();
        sports = sportList;
        sportSelectable = new ArrayList<>();
        for (Sport s : sports) {
            Selectable<Sport> item = new Selectable<>(s);
            sportSelectable.add(item);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Selectable<Sport> item = sportSelectable.get(position);
        Sport sport = item.get();
        holder.textView.setText(sport.getName());
        holder.imageView.setImageResource(SportsImageMatcher.getImage(sport));
        holder.imageView.setClipToOutline(true);
        holder.cardView.setBackgroundColor(item.isSelected() ? Color.CYAN : Color.WHITE);
        holder.view.setOnClickListener(view -> {
            item.toggle();
            holder.cardView.setBackgroundColor(item.isSelected() ? Color.parseColor("#b2dfdb") : Color.WHITE);
            if (item.isSelected()) {
                chosenSportList.add(sport);
            } else {
                chosenSportList.remove(sport);
            }
            SelectSportActivity.chosenSportsRecommended = SelectSportActivity.sportsRecommendedAdapter.chosenSportList;
            SelectSportActivity.chosenOtherSports = SelectSportActivity.otherSportsAdapter.chosenSportList;
            SelectSportActivity.finalChoice.clear();
            SelectSportActivity.finalChoice.addAll(SelectSportActivity.chosenSportsRecommended);
            SelectSportActivity.finalChoice.addAll(SelectSportActivity.chosenOtherSports);
            SelectSportActivity.sportChoicesConfirmButton.setEnabled(SelectSportActivity.finalChoice.size() != 0);
        });

    }

    @Override
    public int getItemCount() {
        return sports == null ? 0 : sports.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView textView;
        private final ImageView imageView;
        private final CardView cardView;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            cardView = itemView.findViewById(R.id.sportView);
            textView = itemView.findViewById(R.id.sport_title);
            imageView = itemView.findViewById(R.id.sport_image);
        }
    }
}
