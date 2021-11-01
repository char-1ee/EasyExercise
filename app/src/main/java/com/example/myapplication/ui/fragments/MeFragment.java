package com.example.myapplication.ui.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.ui.activities.MainActivity;
import com.example.myapplication.ui.activities.SignInActivity;
import com.example.myapplication.ui.activities.ViewPlanActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The fragment class for showing all information of the user.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 * @author Li Xingjian
 * @deprecated
 */

public class MeFragment extends Fragment {
    OptionsPickerView pvOptions1, pvOptions2, pvOptions3;
    String gender;
    int weight;
    int height;
    TextView userNameView, emailView, genderView, heightView, weightView, BMIView;
    ImageView imageView;
    View view;
    Button mEditButton, logoutBtn;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    List<Integer> weightRange;
    List<Integer> heightRange;
    List<String> genderChoice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_me, container, false);
        imageView= view.findViewById(R.id.Profile_photo);
        emailView= view.findViewById(R.id.email_view);
        userNameView= view.findViewById(R.id.imageView);
        BMIView= view.findViewById(R.id.BMI_view);
        mEditButton = view.findViewById(R.id.edit_profile_button);
        heightView= view.findViewById(R.id.height_view);
        weightView= view.findViewById(R.id.weight_view);
        genderView= view.findViewById(R.id.gender_view);
        logoutBtn= view.findViewById(R.id.sign_out_button);
        getUserInfo();
        if(weight== 0){
            initPicker();
            pvOptions1.show();
            pvOptions2.show();
            pvOptions3.show();
            Handler handler = new Handler();
            Runnable runnable = new Runnable(){
                @Override
                public void run() {
                    if(weight == 0){
                        handler.postDelayed(this, 1000);
                    }
                    else{
                        saveUserInfo();
                        weightView.setText(String.valueOf(weight));
                        heightView.setText(String.valueOf(height));
                        genderView.setText(gender);
                        BMIView.setText(String.valueOf(weight/(height*height/10000)));
                    }
                }
            };
            handler.post(runnable);
        }
        else{
            weightView.setText(String.valueOf(weight));
            heightView.setText(String.valueOf(height));
            genderView.setText(gender);
            BMIView.setText(String.valueOf(weight/(height*height/10000)));
        }




        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(requireContext())
                .enableAutoManage(requireActivity(), connectionResult -> {

                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        logoutBtn.setOnClickListener(view -> Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                status -> {
                    if (status.isSuccess()) {
                        gotoSignInActivity();
                    } else {
                        Toast.makeText(getContext(), "Session not close", Toast.LENGTH_LONG).show();
                    }
                }));

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearUserInfo();
                initPicker();
                pvOptions1.show();
                pvOptions2.show();
                pvOptions3.show();
                weight= 0;
                Handler handler = new Handler();
                Runnable runnable = new Runnable(){
                    @Override
                    public void run() {
                        if(weight == 0){
                            handler.postDelayed(this, 1000);
                        }
                        else{
                            Toast.makeText(getContext(), String.valueOf(height), Toast.LENGTH_SHORT).show();
                            saveUserInfo();
                            weightView.setText(String.valueOf(weight));
                            heightView.setText(String.valueOf(height));
                            genderView.setText(gender);
                            BMIView.setText(String.valueOf(weight/(height*height/10000)));
                        }
                    }
                };
                handler.post(runnable);
            }
        });

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result=opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(this::handleSignInResult);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(requireActivity());
        googleApiClient.disconnect();
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            assert account != null;
            userNameView.setText(account.getDisplayName());
            emailView.setText(account.getEmail());
            try{
                Glide
                        .with(this)
                        .load(account.getPhotoUrl())
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageView);
            }catch (NullPointerException e){
                Toast.makeText(getContext(),"image not found",Toast.LENGTH_LONG).show();
            }

        }else{
            gotoSignInActivity();
        }
    }

    private void gotoSignInActivity(){
        Intent intent=new Intent(getActivity(), SignInActivity.class);
        startActivity(intent);
    }

    private void saveUserInfo(){
        SharedPreferences userInfo = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.putString("gender", gender);
        editor.putInt("weight", weight);
        editor.putInt("height", height);
        editor.commit();
    }

    private void getUserInfo(){
        SharedPreferences userInfo = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        gender = userInfo.getString("gender", null);
        weight = userInfo.getInt("weight", 0);
        height= userInfo.getInt("height", 0);
    }


    private void clearUserInfo(){
        SharedPreferences userInfo = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.clear();
        editor.commit();
    }


    private void initPicker(){
        weightRange= new ArrayList();
        for(int i= 40; i<= 100; i+=2){
            weightRange.add(i);
        }
        pvOptions1 = new OptionsPickerBuilder(getContext(), (options1, option2, options3, v) -> weight= weightRange.get(options1))
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
        heightRange= new ArrayList();
        for(int i= 140; i<= 200; i+=2){
            heightRange.add(i);
        }
        pvOptions2 = new OptionsPickerBuilder(getContext(), (options1, option2, options3, v) -> height= heightRange.get(options1))
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
        genderChoice= new ArrayList<>();
        genderChoice.add("Female");
        genderChoice.add("Male");
        genderChoice.add("Prefer not to disclose");
        pvOptions3 = new OptionsPickerBuilder(getContext(), (options1, option2, options3, v) -> gender= genderChoice.get(options1))
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
