package com.ghen61.lenseye;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.data.LineData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyPageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    addLense addlense;


    public ImageButton setting;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbRef;
    public LenseAdapter adapter;
    ArrayList<String> list = new ArrayList<>();


    // private OnFragmentInteractionListener mListener;

    public MyPageFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_my_page, container, false);




        addlense = new addLense(view.getContext());
        addlense.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        final ImageButton settingB = (ImageButton) view.findViewById(R.id.settingB);





        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

             /*   Intent intent = new Intent(MyPageFragment.this,SettingActivity.class);
                startActivity(intent);
*/
            Intent intent = new Intent(getActivity(),SettingActivity.class);
            startActivity(intent);

            }
        };

        settingB.setOnClickListener(listener);


       final ListView listView = (ListView) view.findViewById(R.id.listView);
        // 기본 Text를 담을 수 있는 simple_list_item_1을 사용해서 ArrayAdapter를 만들고 listview에 설정
        adapter =  new LenseAdapter();
        listView.setAdapter(adapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReference();
        DatabaseReference lenseRef = dbRef.child("User").child("Lense").child("ghen601").child("LenseInfo");

        lenseRef.addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Lense lense = dataSnapshot.getValue(Lense.class);  // chatData를 가져오고
                adapter.addItem(lense.getName() ,lense.getDate().toString(),lense.getDisuse().toString());  // adapter에 추가합니다.
                listView.setAdapter(adapter);


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





        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
