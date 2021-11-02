package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities.authentication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText inputEmail;
    private Button resetButton, backButton;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        inputEmail = findViewById(R.id.reset_email);
        resetButton = findViewById(R.id.reset_password_button);
        backButton = findViewById(R.id.back_button);
        progressBar = findViewById(R.id.progressBar_reset);

        auth = FirebaseAuth.getInstance();

        backButton.setOnClickListener(v -> finish());

        resetButton.setOnClickListener(v -> {

            String email = inputEmail.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ResetPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.GONE);
            });
        });
    }

}