package com.example.myapplication.ui.AddPlan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.classes.Sport;

import java.util.ArrayList;

public class AddPlan extends AppCompatActivity {
    private ImageView imageView;
    private Button button;
    private RecyclerView rv_test;
    private ArrayList<Sport> secondList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        secondList.add(new Sport("swimming", R.drawable.swimming, true));
        secondList.add(new Sport("swimming", R.drawable.swimming, true));
        secondList.add(new Sport("swimming", R.drawable.swimming, true));
        rv_test = findViewById(R.id.check_in_sport_recycler_view);
        button = findViewById(R.id.add_plan_button);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.swimming);

        // RecyclerView adapter
        rv_test.setLayoutManager(new LinearLayoutManager(AddPlan.this, LinearLayoutManager.VERTICAL, false));
        AddPlanAdapter firstAdapter = new AddPlanAdapter(AddPlan.this, secondList);
        rv_test.setAdapter(firstAdapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddPlan.this, "Option" + firstAdapter.finalChoice.getText() + "selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}