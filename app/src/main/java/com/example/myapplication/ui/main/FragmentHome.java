package com.example.myapplication.ui.main;

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
import com.example.myapplication.ui.addPlan.AddPlan;
import com.example.myapplication.ui.checkin.CheckIn;

public class FragmentHome extends Fragment {
    View v;
    Button mMakePlanButton;
    Button mCheckInButton;

    public FragmentHome() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        mMakePlanButton= v.findViewById(R.id.home_plan_button);
        mCheckInButton= v.findViewById(R.id.home_checkin_button);

        mMakePlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), AddPlan.class);
                startActivity(intent);
            }
        });
        mCheckInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), CheckIn.class);
                startActivity(intent);
            }
        });

        return v;
    }

}
