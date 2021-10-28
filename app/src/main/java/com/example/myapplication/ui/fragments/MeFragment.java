package com.example.myapplication.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.ui.activities.SignInActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

/**
 * The fragment class for showing all information of the user.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */

public class MeFragment extends Fragment {
    TextView userNameView, emailView;
    ImageView imageView;
    View view;
    Button mEditButton, logoutBtn;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.fragment_me, container, false);
        imageView= view.findViewById(R.id.Profile_photo);
        emailView= view.findViewById(R.id.email_view);
        userNameView= view.findViewById(R.id.imageView);
        mEditButton = view.findViewById(R.id.edit_profile_button);
        logoutBtn= view.findViewById(R.id.sign_out_button);
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

}
