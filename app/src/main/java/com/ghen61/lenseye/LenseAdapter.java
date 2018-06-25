package com.ghen61.lenseye;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LenseAdapter extends BaseAdapter{

    private ArrayList<Lense> listViewItemList = new ArrayList<Lense>();


    public LenseAdapter(){


    }



    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();


        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_lense, parent, false);

        }
            TextView name = (TextView) convertView.findViewById(R.id.lenseName);
            TextView start = (TextView) convertView.findViewById(R.id.lenseStart);
            TextView end = (TextView) convertView.findViewById(R.id.lenseEnd);
            Lense lenseItem = listViewItemList.get(position);

            name.setText(lenseItem.getName());
            start.setText(lenseItem.getDate());
            end.setText(lenseItem.getDisuse());

            return convertView;

    }

    public void addItem(String name, String start, String end){

        Lense lense = new Lense();

        lense.setName(name);
        lense.setDate(start);
        lense.setDisuse(end);
        listViewItemList.add(lense);

    }

}
