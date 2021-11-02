package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.recommendation.FacilityRecommendation;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters.SportRecyclerViewAdapter;

/**
 * The activity class for showing all sport for making a workout plan, in the making plan task.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */

public class SelectSportActivity extends AppCompatActivity {
    public static List<Sport> finalChoice;
    public static List<Sport> chosenSportsRecommended;
    public static List<Sport> chosenOtherSports;
    public static Button sportChoicesConfirmButton;
    public static SportRecyclerViewAdapter sportsRecommendedAdapter;
    public static SportRecyclerViewAdapter otherSportsAdapter;
    private Handler handler;
    private Runnable runnable;
    private double latitude = 0;
    private double longitude = 0;
    private List<Facility> facilities;
    private List<Sport> sportsRecommended;
    private List<Sport> otherSports;
    private RecyclerView sportsRecommendedRecyclerView;
    private RecyclerView otherSportsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initAdapter();
        initButton();
    }

    private List<Sport> getSportsRecommended() {
        return (List<Sport>) getIntent().getSerializableExtra("RecommendedSports");
    }

    private List<Sport> getOtherSports() {
        return (List<Sport>) getIntent().getSerializableExtra("OtherSports");
    }

    private void initView() {
        finalChoice = new ArrayList<>();
        setContentView(R.layout.activity_select_sport);
        sportChoicesConfirmButton = findViewById(R.id.sport_choices_confirm_button);
        sportsRecommendedRecyclerView = findViewById(R.id.recycler_view);
        sportsRecommended = getSportsRecommended();
        sportChoicesConfirmButton.setEnabled(false);
        otherSports = getOtherSports();
        latitude = getLatitude();
        longitude = getLongitude();
        initHandler();
        handler.post(runnable);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Initialize adapter for recyclerview.
     */
    private void initAdapter() {
        sportsRecommendedAdapter = new SportRecyclerViewAdapter(sportsRecommended);
        LinearLayoutManager sportsRecommendedManager =
                new GridLayoutManager(SelectSportActivity.this, 2);
        sportsRecommendedRecyclerView.setHasFixedSize(true);
        sportsRecommendedRecyclerView.setLayoutManager(sportsRecommendedManager);
        sportsRecommendedRecyclerView.setAdapter(sportsRecommendedAdapter);

        otherSportsRecyclerView = findViewById(R.id.recycler_view2);
        otherSportsAdapter = new SportRecyclerViewAdapter(otherSports);
        LinearLayoutManager otherSportsManager =
                new GridLayoutManager(SelectSportActivity.this, 2);
        otherSportsRecyclerView.setHasFixedSize(true);
        otherSportsRecyclerView.setLayoutManager(otherSportsManager);
        otherSportsRecyclerView.setAdapter(otherSportsAdapter);
        chosenSportsRecommended = sportsRecommendedAdapter.chosenSportList;
        chosenOtherSports = otherSportsAdapter.chosenSportList;
        finalChoice.clear();
        finalChoice.addAll(chosenSportsRecommended);
        finalChoice.addAll(chosenOtherSports);
    }

    private void initButton() {
        sportChoicesConfirmButton.setOnClickListener(view -> {
            Context context = SelectSportActivity.this;
            facilities = FacilityRecommendation.getFacilitiesBySports(SelectSportActivity.this, finalChoice, new Coordinates(latitude, longitude), 20);
            Intent intent = new Intent(context, SelectFacilityPlanActivity.class);
            intent.putExtra("FacilityQualified", (Serializable) facilities);
            intent.putExtra("longitude", (Serializable) longitude);
            intent.putExtra("latitude", (Serializable) latitude);
            startActivity(intent);
        });
    }

    private void initHandler() {
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (latitude == 0) {
                    Toast.makeText(SelectSportActivity.this, "not yet", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(this, 1000);
                }
            }
        };
    }

    public double getLatitude() {
        return (double) getIntent().getSerializableExtra("latitude1");
    }

    public double getLongitude() {
        return (double) getIntent().getSerializableExtra("longitude1");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
