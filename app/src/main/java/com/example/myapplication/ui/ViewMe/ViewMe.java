package com.example.myapplication.ui.ViewMe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class ViewMe extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HistoryPagerAdapter historyPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_me);

        tabLayout= (TabLayout) findViewById(R.id.tab_layout);
        viewPager=(ViewPager) findViewById(R.id.view_pager);
        historyPagerAdapter= new HistoryPagerAdapter(getSupportFragmentManager());
        historyPagerAdapter.AddFragment(new FragmentMe(), "Me");
        historyPagerAdapter.AddFragment(new FragmentHistory(),"History");

        viewPager.setAdapter(historyPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}