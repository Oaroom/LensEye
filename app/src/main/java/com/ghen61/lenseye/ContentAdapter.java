package com.ghen61.lenseye;

import android.content.Context;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LG on 2018-06-18.
 */

public class ContentAdapter extends BaseAdapter {

    ArrayList<Content> contentlistitem = new ArrayList<Content>();


    public ContentAdapter(){



    }

    @Override
    public int getCount() {
        return contentlistitem.size();
    }

    @Override
    public Object getItem(int position) {
        return contentlistitem.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_content, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
         TextView descTextView = (TextView) convertView.findViewById(R.id.title) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Content content = contentlistitem.get(position);

        // 아이템 내 각 위젯에 데이터 반영

        descTextView.setText(content.getName());



        return convertView;

    }


    public void addItem(String title, String url) {
        Content item = new Content();

        item.setName(title);
        item.setUrl(url);


        Log.d("~!~!~!~!~!",title);

        contentlistitem.add(item);
    }


}
