package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.db.SportDB;
import com.example.myapplication.interfaces.SportDao;
import com.example.myapplication.models.Sport;

import java.util.List;

public class DBDemo extends AppCompatActivity implements View.OnClickListener{

    private Button insert;
    private Button delete;
    private Button select;
    private Button update;
    private Button deleteAll;
    private TextView demoText;
    private SportDB sportDB;
    private SportDao sportDao;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbdemo);
        initView();
        sportDB = Room.databaseBuilder(this, SportDB.class,"word_database") //new a database
                .allowMainThreadQueries()
                .build(); //建立数据库

        sportDao = sportDB.getSportDao(); //获取Dao
        List<Sport> list = sportDao.getSportList(); //获取数据库中全部信息
        String text = "";
        for(int i=0;i<list.size();i++){   //fetch all data from database
            Sport item = list.get(i);
            text += item.get_id() + ":" + item.getSportName() + "\n";
        }
        demoText.setText(text);
    }

    private void initView() {
        insert = findViewById(R.id.insert);
        delete = findViewById(R.id.delete);
        select = findViewById(R.id.select);
        update = findViewById(R.id.update);
        deleteAll = findViewById(R.id.deleteAll);
        demoText = findViewById(R.id.demo_text);

        insert.setOnClickListener(this);
        delete.setOnClickListener(this);
        select.setOnClickListener(this);
        update.setOnClickListener(this);
        deleteAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.deleteAll:
                sportDB.getSportDao().deleteAllSport();
                id = 0;
                break;//清空数据库
            case R.id.insert:
                id++;
                byte[] ax = new byte[1];
                ax[0] = 0;
                Sport a = new Sport(id,"Swim", ax , 0, false, false);
                sportDB.getSportDao().insertSport(a); //插入一个值
                break;
            case R.id.delete:
                byte[] bx = new byte[1];
                bx[0] = 0;
                Sport b  = new Sport("Swim", bx , 0, false, false);
                b.set_id(1);
                sportDB.getSportDao().deleteSport(b); // 删除id为1的对象
                break;
            case R.id.select:
                List<Sport> list = sportDao.getSportList(); //获取数据库中全部信息
                String text = "";  //define a value to store data from database
                for(int i=0;i<list.size();i++){   //fetch all data from database
                    Sport item = list.get(i);
                    text += item.get_id() + ":" + item.getSportName() + "\n";
                }
                demoText.setText(text); //更新显示【其实就是把数据库信息都导进来】
                break;
            case R.id.update:
                byte[] cx = new byte[1];
                cx[0] = 0;
                Sport c  = new Sport("Dance", cx , 0, false, false);
                c.set_id(5);
                sportDB.getSportDao().updateSport(c); //更新id为5的对象的值
                break;
        }
    }
}