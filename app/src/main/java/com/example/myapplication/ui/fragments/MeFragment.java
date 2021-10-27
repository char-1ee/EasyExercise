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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
//import com.example.myapplication.ui.activities.EditProfileActivity;

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

        googleApiClient=new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                if (status.isSuccess()){
                                    gotoSignInActivity();
                                }else{
                                    Toast.makeText(getContext(),"Session not close", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
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
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
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
