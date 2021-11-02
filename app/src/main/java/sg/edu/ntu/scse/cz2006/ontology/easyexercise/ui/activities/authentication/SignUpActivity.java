package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities.MainActivity;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
    }

    private void initView() {
        Button toLoginButton = findViewById(R.id.to_login_button);
        Button signUpButton = findViewById(R.id.register_button);
        inputEmail = findViewById(R.id.register_email);
        inputPassword = findViewById(R.id.register_password);
        progressBar = findViewById(R.id.register_progressBar);

        auth = FirebaseAuth.getInstance();

        toLoginButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_login_button:
                finish();
                break;
            case R.id.register_button:
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Please enter your email address.",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Please enter your password.",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Password is too short. Please enter a minimum of 6 characters.",
                            Toast.LENGTH_LONG
                    ).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Create user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, task -> {
                    progressBar.setVisibility(View.GONE);
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Toast.makeText(
                                SignUpActivity.this,
                                "Authentication failed." + task.getException(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
