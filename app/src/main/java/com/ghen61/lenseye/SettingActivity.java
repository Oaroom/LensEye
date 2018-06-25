package com.ghen61.lenseye;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingActivity extends AppCompatActivity {


    public LenseAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);

       final ListView listview = (ListView) findViewById(R.id.listView);

       adapter = new LenseAdapter();
       listview.setAdapter(adapter);

       firebaseDatabase = FirebaseDatabase.getInstance();
       dbRef = firebaseDatabase.getReference();
       DatabaseReference lenseRef = dbRef.child("User").child("Lense").child("ghen601").child("LenseInfo");


    }
}
