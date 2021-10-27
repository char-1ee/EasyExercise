package com.example.myapplication.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        List<Integer> options1Items = new ArrayList();
        options1Items.add(1);
        options1Items.add(3);
        List<List> options2Items;
        List<List<List>> options3Items;
        OptionsPickerView pvOptions = new OptionsPickerBuilder(EditProfileActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 , View v) {
                String tx = options1Items.get(options1).toString();
                Toast.makeText(EditProfileActivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        }).build();
        pvOptions.setPicker(options1Items);
        pvOptions.show();
    }
}