package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.activities.AddPlanActivity;
import com.example.myapplication.activities.CheckInActivity;

public class HomeFragment extends Fragment {
    View view;
    Button mMakePlanButton;
    Button mCheckInButton;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mMakePlanButton = view.findViewById(R.id.home_plan_button);
        mCheckInButton = view.findViewById(R.id.home_checkin_button);

        mMakePlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddPlanActivity.class);
                startActivity(intent);
            }
        });
        mCheckInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckInActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
