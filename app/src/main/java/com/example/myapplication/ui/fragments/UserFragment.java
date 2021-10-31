package com.example.myapplication.ui.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.ui.activities.authentication.LoginActivity;
import com.example.myapplication.ui.activities.authentication.UserActivity;
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

import java.util.ArrayList;
import java.util.List;


/**
 * The fragment class for showing all information of the user.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 * @author Li Xingjian
 */
public class UserFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ImageView profilePhoto;
    private Button signOutButton, authButton, editButton;
    private TextView emailText, usernameText, genderText, heightText, weightText, BMIText;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mSignInClient;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    private List<Integer> weightRange = new ArrayList<>();
    private List<Integer> heightRange = new ArrayList<>();
    private List<String> genderChoice = new ArrayList<>();
    private String gender;
    private int weight;
    private int height;
    private OptionsPickerView pvOptions1, pvOptions2, pvOptions3; // TODO: Raw use of parameterized class


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_user, container, false);
        initGoogleClient();
        initView();

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = firebaseAuth -> {
            FirebaseUser user1 = firebaseAuth.getCurrentUser();
            if (user1 == null) {

                // user auth state is changed - user is null then launch login activity
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        };
        // TODO: pickers should not appear when first enter fragment, thus I made every textView(height, weight, BMI) clickable
//        getUserInfo();
//        if (weight == 0) {
//            initPicker();
//            pvOptions1.show();
//            pvOptions2.show();
//            pvOptions3.show();
//            Handler handler = new Handler();
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//                    if (weight == 0) {
//                        handler.postDelayed(this, 1000);
//                    } else {
//                        saveUserInfo();
//                        weightText.setText(String.valueOf(weight));
//                        heightText.setText(String.valueOf(height));
//                        genderText.setText(gender);
//                        BMIText.setText(String.valueOf(weight / (height * height / 10000)));
//                    }
//                }
//            };
//            handler.post(runnable);
//        } else {
//            weightText.setText(String.valueOf(weight));
//            heightText.setText(String.valueOf(height));
//            genderText.setText(gender);
//            BMIText.setText(String.valueOf(weight / (height * height / 10000)));
//        }

        return view;
    }

    private void initView() {
        profilePhoto = view.findViewById(R.id.profile_photo);
        emailText = view.findViewById(R.id.text_email);
        usernameText = view.findViewById(R.id.profile_name);
        BMIText = view.findViewById(R.id.text_bmi);
        heightText = view.findViewById(R.id.text_height);
        weightText = view.findViewById(R.id.text_weight);
        genderText = view.findViewById(R.id.text_gender);
        signOutButton = view.findViewById(R.id.new_sign_out_button);
        editButton = view.findViewById(R.id.edit_button);
        authButton = view.findViewById(R.id.edit_login_info_button);

        signOutButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
        authButton.setOnClickListener(this);
        BMIText.setOnClickListener(this);
        heightText.setOnClickListener(this);
        weightText.setOnClickListener(this);
        genderText.setOnClickListener(this);
    }

    private void initGoogleClient() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(requireContext())
                .enableAutoManage(requireActivity(), connectionResult -> {
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_sign_out_button:
                signOut();
                break;

            case R.id.edit_login_info_button:
                startActivity(new Intent(getActivity(), UserActivity.class));
                break;

            case R.id.edit_button:
                clearUserInfo();
                initPicker();
                pvOptions1.show();
                pvOptions2.show();
                pvOptions3.show();
                weight = 0;
                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (weight == 0) {
                            handler.postDelayed(this, 1000);
                        } else {
                            Toast.makeText(getContext(), String.valueOf(height), Toast.LENGTH_SHORT).show();
                            saveUserInfo();
                            weightText.setText(String.valueOf(weight));
                            heightText.setText(String.valueOf(height));
                            genderText.setText(gender);
                            BMIText.setText(String.valueOf(weight / (height * height / 10000)));
                        }
                    }
                };
                handler.post(runnable);
                break;

            case R.id.text_height:
                // TODO: here you code
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(this::handleSignInResult);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(requireActivity());
        googleApiClient.disconnect();
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            assert account != null;
            usernameText.setText(account.getDisplayName());
            emailText.setText(account.getEmail());
            try {
                Glide.with(this)
                        .load(account.getPhotoUrl())
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(profilePhoto);
            } catch (NullPointerException e) {
                Toast.makeText(getContext(), "Image not found", Toast.LENGTH_LONG).show();
            }
        } else {
            gotoLoginActivity();
        }
    }

        private void signOut() {
        auth.signOut();
        mSignInClient.signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    private void gotoLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    private void saveUserInfo() {
        SharedPreferences userInfo = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.putString("gender", gender);
        editor.putInt("weight", weight);
        editor.putInt("height", height);
        editor.apply();
    }

    private void getUserInfo() {
        SharedPreferences userInfo = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        gender = userInfo.getString("gender", null);
        weight = userInfo.getInt("weight", 0);
        height = userInfo.getInt("height", 0);
    }


    private void clearUserInfo() {
        SharedPreferences userInfo = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.clear();
        editor.apply();
    }


    private void initPicker() {
        for (int i = 40; i <= 100; i += 2) {
            weightRange.add(i);
        }
        pvOptions1 = new OptionsPickerBuilder(getContext(), (options1, option2, options3, v) -> weight = weightRange.get(options1))
                .setSubmitText("Confirm")
                .setCancelText("Cancel")
                .setTitleText("Weight")
                .setSubCalSize(18)
                .setTitleSize(20)
                .setContentTextSize(18)
                .isCenterLabel(false)
                .setCyclic(true, false, false)
                .setSelectOptions(1, 1, 1)
                .setOutSideCancelable(false)
                .isDialog(false)
                .isRestoreItem(true)
                .build();
        pvOptions1.setPicker(weightRange);

        for (int i = 140; i <= 200; i += 2) {
            heightRange.add(i);
        }
        pvOptions2 = new OptionsPickerBuilder(getContext(), (options1, option2, options3, v) -> height = heightRange.get(options1))
                .setSubmitText("Confirm")
                .setCancelText("Cancel")
                .setTitleText("Height")
                .setSubCalSize(18)
                .setTitleSize(20)
                .setContentTextSize(18)
                .isCenterLabel(false)
                .setCyclic(true, false, false)
                .setSelectOptions(1, 1, 1)
                .setOutSideCancelable(false)
                .isDialog(false)
                .isRestoreItem(true)
                .build();
        pvOptions2.setPicker(heightRange);

        genderChoice.add("Female");
        genderChoice.add("Male");
        genderChoice.add("Prefer not to disclose");
        pvOptions3 = new OptionsPickerBuilder(getContext(), (options1, option2, options3, v) -> gender = genderChoice.get(options1))
                .setSubmitText("Confirm")
                .setCancelText("Cancel")
                .setTitleText("Gender")
                .setSubCalSize(18)
                .setTitleSize(20)
                .setContentTextSize(18)
                .isCenterLabel(false)
                .setCyclic(false, false, false)
                .setSelectOptions(1, 1, 1)
                .setOutSideCancelable(false)
                .isDialog(false)
                .isRestoreItem(true)
                .build();
        pvOptions3.setPicker(genderChoice);
    }

}
