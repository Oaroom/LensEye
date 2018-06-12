package com.ghen61.lenseye;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by oarum on 2017-11-21.
 */

public class addLense extends Dialog implements View.OnClickListener {


    private Context context;
    private Button closeBt;
    private Button submitBt;
    private EditText lenseName;
    private Spinner lenseTerm;

    private String name;
    private String userId = "ghen601";
    private boolean use;
    private String term;
    private String disuse;
    private int days;
    private String getTime;

    private int cntMonth;
    private int nowMonth;


    private LineChart chart;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    String Label[] = {"m01", "m02", "m03", "m04", "m05", "m06", "m07", "m08",
            "m09", "m10", "m11", "m12"};


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbRef;
    public ArrayAdapter adapter;
    ArrayList<String> lenseList = new ArrayList<>();



    public addLense(Context context) {
        super(context); // context 객체를 받는 생성자가 반드시 필요
    }

    public addLense(Context context, String name) {

        super(context);
        this.context = context;
        this.name = name;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_addlense);

        closeBt = (Button) findViewById(R.id.closeBt);
        submitBt = (Button) findViewById(R.id.submitBt);
        lenseName = (EditText) findViewById(R.id.lenseName);
        lenseTerm = (Spinner) findViewById(R.id.lenseTime);

        closeBt.setOnClickListener(this);
        submitBt.setOnClickListener(this);

        final ListView listview = (ListView) findViewById(R.id.lenseList);
        adapter = new ArrayAdapter<>(this.getContext(),android.R.layout.simple_list_item_1, android.R.id.text1);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReference();
        DatabaseReference lenseRef = dbRef.child("User").child("Lense").child("ghen601").child("LenseInfo");

        lenseRef.addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Lense lense = dataSnapshot.getValue(Lense.class);  // chatData를 가져오고
                adapter.add( " [  " +lense.getName() + " ]  " +lense.getDate()+" ~ "+lense.getDisuse());  // adapter에 추가합니다.
                listview.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                if(true){

                    Toast.makeText(getContext(), "렌즈착용", Toast.LENGTH_SHORT).show();
                    writeLense();
                    sendData(userId);
                    cancel();

                } else {



                }
            }
        });


        submitBt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                writeLense();
                sendData(userId);
                cancel();
            }
        });


        closeBt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                cancel();
            }
        });


    }


    public void getInfo(String userId, int cntMonth, int nowMonth){

        this.userId = userId;
        this.cntMonth = cntMonth;
        this.nowMonth = nowMonth;

    }


    public void writeLense(){

            mDatabase.child("User").child("Lense").child("ghen601").child("Month").child(Label[nowMonth - 1]).setValue(cntMonth + 1);

    }



    //렌즈 업데이트
    public void sendData(String userId) {

        DatabaseReference mLense = mDatabase.child("User").child("Lense").child("ghen601").child("LenseInfo");
        mLense.addValueEventListener(lenseListener);


        name = lenseName.getText().toString();
        term = lenseTerm.getSelectedItem().toString();


        //해야할 일, term 을 받아오는데 그 받아온거를 숫자로 바꿔서 데이트에 더해서 디비에 업데이트 해야하고
        //그래프 모양 바꿔야 하고
        //리스트 뿌려주면 끝

        //여기서 cnt 증가

        switch (term){

            case "원데이": days=1; break;
            case "1주": days=7; break;
            case "2주": days=14; break;
            case "한달": days=30; break;
            case "1년" : days=365; break;

        }

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        getTime = sdf.format(date);

//        Calendar currentCal = Calendar.getInstance();
//        currentCal.add(Calendar.HOUR,12);

        setLenseDate();

        String use = "true";

        String key = mLense.push().getKey();


        Map<String, String> lenseValues = new HashMap<>();
        lenseValues.put("name",name);
        lenseValues.put("term",term);
        lenseValues.put("date",getTime);
        lenseValues.put("disuse",disuse);
        lenseValues.put("use", use);

        DatabaseReference keyRef = mLense.child(key);
        keyRef .setValue(lenseValues);


    }

    private void setLenseDate() {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try{

            Date date = df.parse(getTime);

            //날짜 더하기
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE,days);
            disuse = df.format(cal.getTime());

        }catch(ParseException e){

            e.printStackTrace();
        }


    }


    ValueEventListener lenseListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            //datas.clear();

            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                String key = snapshot.getKey();
                Lense lense = snapshot.getValue(Lense.class);
                lense.key = key;
                //  datas.add(lense);

            }

           // adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    @Override
    public void onClick(View view) {

    }
}

