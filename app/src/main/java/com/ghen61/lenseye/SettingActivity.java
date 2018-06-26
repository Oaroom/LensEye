package com.ghen61.lenseye;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SettingActivity extends AppCompatActivity {


    public LenseAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbRef;
    private AlertDialog.Builder alert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);

       final ListView listview = (ListView) findViewById(R.id.listView);

       adapter = new LenseAdapter();
       listview.setAdapter(adapter);

       firebaseDatabase = FirebaseDatabase.getInstance();
       dbRef = firebaseDatabase.getReference();
       final DatabaseReference lenseRef = dbRef.child("User").child("Lense").child("ghen601").child("LenseInfo");


       lenseRef.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {

               Lense lense = dataSnapshot.getValue(Lense.class);  // chatData를 가져오고
               adapter.addItem(lense.getName() ,lense.getDate().toString(),lense.getDisuse().toString());  // adapter에 추가합니다.
               listview.setAdapter(adapter);


           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });



       listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

              // String name;

                Object ob = (Object)adapterView.getAdapter().getItem(i);




               //렌즈 생성,삭제를 위한 다이얼로그
                alert = new AlertDialog.Builder(SettingActivity.this);

                alert.setTitle("렌즈 페기");
                alert.setMessage(" 렌즈를 폐기하시겠습니까?");


                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        String url = adapter.getItem(i).toString();

                        Toast.makeText(SettingActivity.this, url, Toast.LENGTH_SHORT).show();

                      //  lenseRef.child("/"+url).removeValue();

                        dialog.dismiss();
                       // Toast.makeText(SettingActivity.this, "렌즈가 폐기 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });


               alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int i) {

                       dialog.dismiss();

                   }
               });

            alert.show();
           }
       });

    }
}
