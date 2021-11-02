package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.CustomizedLocation;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.database.SportAndFacilityDBHelper;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters.CheckInSportAdapter;

/**
 * The activity class for checking in at a customized location in the checking in task, when there's no facility around.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */

public class CheckInCustomizedActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private SportAndFacilityDBHelper dbHelper;
    private Button checkInSportButton;
    private RecyclerView recyclerView;
    private CustomizedLocation customizedLocation;
    private Sport ChosenSport;
    private CheckInSportAdapter checkInSportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        customizedLocation = getCustomizedLocation();
        super.onCreate(savedInstanceState);
        initView();
        initAdapter();
        initButton();

    }

    private CustomizedLocation getCustomizedLocation() {
        return (CustomizedLocation) getIntent().getSerializableExtra("CustomizedLocation");
    }

    private List<Sport> testSelectSportAll() {
        dbHelper.openDatabase();
        List<Sport> sports = dbHelper.getSports();
        dbHelper.closeDatabase();
        return sports;
    }

    private void initView() {
        setContentView(R.layout.activity_check_in_customized);
        recyclerView = findViewById(R.id.check_in_sport_recycler);
        checkInSportButton = findViewById(R.id.check_in_sport_button);
        ImageView imageView = findViewById(R.id.imageView5);
        imageView.setImageResource(R.drawable.panorama);
        TextView locationView = findViewById(R.id.location_view);
        locationView.setText(getString(R.string.customized_location));
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        dbHelper = new SportAndFacilityDBHelper(this);

    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(CheckInCustomizedActivity.this, LinearLayoutManager.VERTICAL, false));
        checkInSportAdapter = new CheckInSportAdapter(CheckInCustomizedActivity.this, testSelectSportAll());
        recyclerView.setAdapter(checkInSportAdapter);
        checkInSportAdapter.setOnItemClickListener(this);
    }

    private void initButton() {
        checkInSportButton.setOnClickListener(view -> {
            if (ChosenSport == null) {
                Toast.makeText(
                        CheckInCustomizedActivity.this,
                        "Please select a sport.",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                Intent intent = new Intent(CheckInCustomizedActivity.this, ExerciseActivity.class);
                intent.putExtra("ChosenSport", ChosenSport);
                intent.putExtra("ChosenLocation", customizedLocation);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ChosenSport = checkInSportAdapter.finalChoice;
        Toast.makeText(CheckInCustomizedActivity.this, String.valueOf(ChosenSport.getName()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
