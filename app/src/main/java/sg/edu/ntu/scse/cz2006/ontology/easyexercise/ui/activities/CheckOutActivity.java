package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Location;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.WorkoutRecord;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.database.WorkoutDatabaseManager;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities.authentication.LoginActivity;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.util.DateUtil;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.util.SportsImageMatcher;

/**
 * The activity class for checking out from a workout in the checking in task.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */
public class CheckOutActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button exitButton;
    private GoogleApiClient googleApiClient;
    private GoogleSignInClient mSignInClient;
    private Sport sport;
    private Location location;
    private Date start;
    private Date end;
    private ImageView profileView;
    private TextView userView, checkOutText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(CheckOutActivity.this)
                .enableAutoManage(CheckOutActivity.this, connectionResult -> {
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mSignInClient = GoogleSignIn.getClient(CheckOutActivity.this, gso);
        initView();
        auth = FirebaseAuth.getInstance();

        // get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // user auth state is changed - user is null then launch login activity
        FirebaseAuth.AuthStateListener authListener = firebaseAuth -> {
            FirebaseUser user1 = firebaseAuth.getCurrentUser();
        };
        initButton();
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(this::handleSignInResult);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            assert account != null;
            userView.setText(account.getDisplayName());
            try {
                Glide.with(this)
                        .load(account.getPhotoUrl())
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(profileView);
            } catch (NullPointerException e) {
                Toast.makeText(CheckOutActivity.this, "Image not found", Toast.LENGTH_LONG).show();
            }
        }
    }

    private Date getStart() {
        return (Date) getIntent().getSerializableExtra("StartDate");
    }

    private Date getEnd() {
        return (Date) getIntent().getSerializableExtra("EndDate");
    }

    private Location getLocation() {
        return (Location) getIntent().getSerializableExtra("LocationExercise");
    }

    private Sport getSport() {
        return (Sport) getIntent().getSerializableExtra("SportExercise");
    }

    private String getTimeDuration() {
        return (String) getIntent().getSerializableExtra("timeDuration");
    }

    private void initView() {
        setContentView(R.layout.activity_check_out);
        sport = getSport();
        location = getLocation();
        start = getStart();
        end = getEnd();
        TextView sportNameView = findViewById(R.id.checkoutSport);
        TextView placeView = findViewById(R.id.checkoutPlace);
        ImageView sportView = findViewById(R.id.checkoutPic);
        exitButton = findViewById(R.id.exitButton);
        profileView = findViewById(R.id.checkoutProfile);
        profileView.setClipToOutline(true);
        userView= findViewById(R.id.checkoutUser);
        checkOutText = findViewById(R.id.checkoutTime);
        TextView timeDuration = findViewById(R.id.time_duration);
        sportView.setImageResource(SportsImageMatcher.getImage(sport));
        Date currTime = Calendar.getInstance().getTime();
        checkOutText.setText(DateUtil.convertDateToString(currTime, "yyyy-MM-dd HH:mm"));
        placeView.setText(location.getName());
        timeDuration.setText(getTimeDuration());
        sportNameView.setText(sport.getName());
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        updateRecord();
    }

    private void updateRecord() {
        FirebaseDatabase database = FirebaseDatabase.getInstance(getString(R.string.firebase_database));
        DatabaseReference user =
                database.getReference()
                        .child("user")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        WorkoutRecord workoutRecord;
        String postId = user.push().getKey();
        workoutRecord = new WorkoutRecord(sport, location, postId, start, end, getTimeDuration());
        WorkoutDatabaseManager.FirebaseWorkoutRecord firebaseWorkoutRecord =
                new WorkoutDatabaseManager.FirebaseWorkoutRecord(workoutRecord);
        assert postId != null;
        user.child("WorkoutRecord").child(postId).setValue(firebaseWorkoutRecord);
    }

    private void initButton() {
        exitButton.setOnClickListener(view -> {
            Intent intent = new Intent(CheckOutActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        Intent intent = new Intent(CheckOutActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        moveTaskToBack(true);
        Intent intent = new Intent(CheckOutActivity.this, MainActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
