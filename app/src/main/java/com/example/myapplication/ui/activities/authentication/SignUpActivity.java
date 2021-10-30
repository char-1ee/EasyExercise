package com.example.myapplication.ui.activities.authentication;

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

import com.example.myapplication.R;
import com.example.myapplication.ui.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputEmail, inputPassword;
    private Button toLoginButton, signUpButton;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
    }

    private void initView() {
        toLoginButton = findViewById(R.id.to_login_button);
        signUpButton = findViewById(R.id.register_button);
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
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Create user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, task -> {
                    Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                Toast.LENGTH_SHORT).show();
                    } else {
<<<<<<< HEAD
                        FirebaseUser currentUser = auth.getCurrentUser();
                        List<String> user = new ArrayList<String>();
                        // String[0] uid;
                        // String[1] username;
                        // String[2] avatar uri;
                        user.add(currentUser.getUid());
                        Log.e("userinfo", user.get(0));
                        user.add(currentUser.getDisplayName());
                        user.add((currentUser.getPhotoUrl() == null) ? null : currentUser.getPhotoUrl().toString());
                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://cz2006-9c928-default-rtdb.asia-southeast1.firebasedatabase.app/");
                        DatabaseReference mDatabase = database.getReference().child("user");
                        mDatabase.child(user.get(0)).setValue(user);
                        mDatabase.child("test").setValue(1);

=======
//                        addUserInfo();  // TODO
>>>>>>> cde1e7c (refactor: refactor MeFragment into UserFragment)
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        finish();
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

    protected void addUserInfo() {
        FirebaseUser currentUser = auth.getCurrentUser();
        String[] user = new String[3];
        // String[0] uid;
        // String[1] username;
        // String[2] avatar uri;
        user[0] = currentUser.getUid();
        user[1] = currentUser.getDisplayName();
        user[2] = currentUser.getPhotoUrl().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ontology-5ae5d-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference mDatabase = database.getReference().child("users");
        mDatabase.child(user[0]).setValue(user);
    }
}
