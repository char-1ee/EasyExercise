package com.example.myapplication.ui.checkin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.classes.Sport;
import com.example.myapplication.ui.plan.SelectFacility2;

import java.util.ArrayList;

public class CheckIn extends AppCompatActivity {
    private ImageView imageView;
    private Button button1, button2;
    private RecyclerView rv_test;
    private ArrayList<Sport> secondList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        secondList.add(new Sport("swimming", R.drawable.swimming, true));
        secondList.add(new Sport("swimming", R.drawable.swimming, true));
        secondList.add(new Sport("swimming", R.drawable.swimming, true));
        rv_test = findViewById(R.id.check_in_sport_recycler_view);
        button1 = findViewById(R.id.check_in_sport_choice_button);
        button2 = findViewById(R.id.choose_another_facility_button);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.swimming);

        //RecyclerView适配器
        rv_test.setLayoutManager(new LinearLayoutManager(CheckIn.this, LinearLayoutManager.VERTICAL, false));
        CheckInSportAdapter firstAdapter = new CheckInSportAdapter(CheckIn.this, secondList);
        rv_test.setAdapter(firstAdapter);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CheckIn.this, "你选择的选项是" + firstAdapter.finalChoice.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckIn.this, SelectFacility2.class);
                startActivity(intent);

            }
        });
    }
}
