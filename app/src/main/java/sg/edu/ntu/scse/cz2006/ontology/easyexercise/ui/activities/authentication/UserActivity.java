package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;

/**
 * The activity class to change user password and usernames.
 *
 * @author Li Xingjian
 */
public class UserActivity extends AppCompatActivity {
    private Button changeEmail;
    private Button changePassword;
    private Button sendEmail;
    private Button remove;

    private EditText oldEmail;
    private EditText newEmail;
    private EditText password;
    private EditText newPassword;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // get firebase auth instance
        auth = FirebaseAuth.getInstance();

        // get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = firebaseAuth -> {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if (currentUser == null) {
                // user auth state is changed - user is null then launch login activity
                startActivity(new Intent(UserActivity.this, LoginActivity.class));
                finish();
            }
        };

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button btnChangeEmail = findViewById(R.id.change_email_button);
        Button btnChangePassword = findViewById(R.id.change_password_button);
        Button btnSendResetEmail = findViewById(R.id.sending_pass_reset_button);
        Button btnRemoveUser = findViewById(R.id.remove_user_button);
        changeEmail = findViewById(R.id.changeEmail);
        changePassword = findViewById(R.id.changePass);
        sendEmail = findViewById(R.id.send);
        remove = findViewById(R.id.remove);

        oldEmail = findViewById(R.id.old_email);
        newEmail = findViewById(R.id.new_email);
        password = findViewById(R.id.password);
        newPassword = findViewById(R.id.newPassword);

        oldEmail.setVisibility(View.GONE);
        newEmail.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);
        changeEmail.setVisibility(View.GONE);
        changePassword.setVisibility(View.GONE);
        sendEmail.setVisibility(View.GONE);
        remove.setVisibility(View.GONE);

        progressBar = findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        btnChangeEmail.setOnClickListener(v -> {
            oldEmail.setVisibility(View.GONE);
            newEmail.setVisibility(View.VISIBLE);
            password.setVisibility(View.GONE);
            newPassword.setVisibility(View.GONE);
            changeEmail.setVisibility(View.VISIBLE);
            changePassword.setVisibility(View.GONE);
            sendEmail.setVisibility(View.GONE);
            remove.setVisibility(View.GONE);
        });

        changeEmail.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            if (user != null && !newEmail.getText().toString().trim().equals("")) {
                user.updateEmail(newEmail.getText().toString().trim())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(
                                        UserActivity.this,
                                        "Email address is updated. Please sign in with the new email.",
                                        Toast.LENGTH_LONG
                                ).show();
                                signOut();
                            } else {
                                Toast.makeText(
                                        UserActivity.this,
                                        "Failed to update email.",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        });
            } else if (newEmail.getText().toString().trim().isEmpty()) {
                newEmail.setError("Please enter an email.");
                progressBar.setVisibility(View.GONE);
            }
        });

        btnChangePassword.setOnClickListener(v -> {
            oldEmail.setVisibility(View.GONE);
            newEmail.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
            newPassword.setVisibility(View.VISIBLE);
            changeEmail.setVisibility(View.GONE);
            changePassword.setVisibility(View.VISIBLE);
            sendEmail.setVisibility(View.GONE);
            remove.setVisibility(View.GONE);
        });

        changePassword.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            if (user != null && !newPassword.getText().toString().trim().equals("")) {
                if (newPassword.getText().toString().trim().length() < 6) {
                    newPassword.setError("Password is too short. Please enter a minimum of 6 characters.");
                    progressBar.setVisibility(View.GONE);
                } else {
                    user.updatePassword(newPassword.getText().toString().trim())
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(
                                            UserActivity.this,
                                            "Password is updated. Please sign in with the new password.",
                                            Toast.LENGTH_LONG
                                    ).show();
                                    signOut();
                                } else {
                                    Toast.makeText(
                                            UserActivity.this,
                                            "Failed to update password.",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            });
                }
            } else if (newPassword.getText().toString().trim().isEmpty()) {
                newPassword.setError("Please enter a password.");
                progressBar.setVisibility(View.GONE);
            }
        });

        btnSendResetEmail.setOnClickListener(v -> {
            oldEmail.setVisibility(View.VISIBLE);
            newEmail.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
            newPassword.setVisibility(View.GONE);
            changeEmail.setVisibility(View.GONE);
            changePassword.setVisibility(View.GONE);
            sendEmail.setVisibility(View.VISIBLE);
            remove.setVisibility(View.GONE);
        });

        sendEmail.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            if (!oldEmail.getText().toString().trim().isEmpty()) {
                auth.sendPasswordResetEmail(oldEmail.getText().toString().trim())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(
                                        UserActivity.this,
                                        "Password reset email is successfully sent.",
                                        Toast.LENGTH_SHORT
                                ).show();
                            } else {
                                Toast.makeText(
                                        UserActivity.this,
                                        "Failed to send password reset email.",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        });
            } else {
                oldEmail.setError("Please enter an email address.");
                progressBar.setVisibility(View.GONE);
            }
        });

        btnRemoveUser.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            if (user != null) {
                FirebaseUser currentUser = auth.getCurrentUser();
                String uid = currentUser.getUid();
                user.delete()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance(getString(R.string.firebase_database));
                                DatabaseReference mDatabase = database.getReference().child("users");
                                mDatabase.child(uid).removeValue();
                                Toast.makeText(
                                        UserActivity.this,
                                        "Your profile is deleted. Create a new account now!",
                                        Toast.LENGTH_LONG
                                ).show();
                                startActivity(new Intent(UserActivity.this, SignUpActivity.class));
                                finish();
                            } else {
                                Toast.makeText(
                                        UserActivity.this,
                                        "Account deletion failed.",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        });
            }
        });
    }

    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
