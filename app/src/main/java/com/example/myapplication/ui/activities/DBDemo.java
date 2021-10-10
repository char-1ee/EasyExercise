//package com.example.myapplication.ui.activities;
//
//
///**
// * This is a demonstration class for developer use on database usage.<br/>
// * It tells how to instantiated a Room database and how to perform CRUD operations on instantiated database.<br/>
// * See <a href="https://developer.android.com/training/data-storage/room#usage">Usage</a>
// */
//
//
//public class DBDemo extends AppCompatActivity implements View.OnClickListener{
//
//    private Button insert;
//    private Button delete;
//    private Button select;
//    private Button update;
//    private Button deleteAll;
//    private TextView demoText;
//    private SportDB sportDB;
//    private SportDao sportDao;
//    private int id = 0;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dbdemo);
//        initView();
//
//        /*
//        //建立数据库
//        sportDB = Room.databaseBuilder(this, SportDB.class,"word_database") //new a database
//                .allowMainThreadQueries()
//                .build();
//        //获取Dao
//        sportDao = sportDB.getSportDao();
//
//        //获取数据库中全部信息
//        List<Sport> list = sportDao.getSportList();
//        StringBuilder text = new StringBuilder();
//
//        //fetch all data from database
//        for(int i=0;i<list.size();i++){
//            Sport item = list.get(i);
//            text.append(item.sportId).append(":").append(item.sportName).append("\n");
//        }
//        demoText.setText(text.toString());
//    }
//
//    private void initView() {
//        insert = findViewById(R.id.insert);
//        delete = findViewById(R.id.delete);
//        select = findViewById(R.id.select);
//        update = findViewById(R.id.update);
//        deleteAll = findViewById(R.id.deleteAll);
//        demoText = findViewById(R.id.demo_text);
//
//        insert.setOnClickListener(this);
//        delete.setOnClickListener(this);
//        select.setOnClickListener(this);
//        update.setOnClickListener(this);
//        deleteAll.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.deleteAll:
//                sportDB.getSportDao().deleteAllSport();
//                id = 0;
//                break;//清空数据库
//            case R.id.insert:
//                id++;
//                byte[] ax = new byte[1];
//                ax[0] = 0;
//                Sport a = new Sport();
//                sportDB.getSportDao().insertSport(a); //插入一个值
//                break;
//            case R.id.delete:
//                byte[] bx = new byte[1];
//                bx[0] = 0;
//                Sport b  = new Sport();
//                b.sportId = 1;
//                sportDB.getSportDao().deleteSport(b); // 删除id为1的对象
//                break;
//            case R.id.select:
//                List<Sport> list = sportDao.getSportList(); //获取数据库中全部信息
//                StringBuilder text = new StringBuilder();  //define a value to store data from database
//                for(int i=0;i<list.size();i++){   //fetch all data from database
//                    Sport item = list.get(i);
//                    text.append(item.sportId).append(":").append(item.sportName).append("\n");
//                }
//                demoText.setText(text.toString()); //更新显示【其实就是把数据库信息都导进来】
//                break;
//            case R.id.update:
//                byte[] cx = new byte[1];
//                cx[0] = 0;
//                Sport c  = new Sport();
//                c.sportId = 5;
//                sportDB.getSportDao().updateSport(c); //更新id为5的对象的值
//                break;
//        }
//         */
//    }
//}