package com.example.android_video_player;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class VideoAdapter extends BaseAdapter {

    ArrayList<File> objects;

    Context context;

    LayoutInflater inflater;

    VideoAdapter(Context cont, ArrayList<File> obj){
        objects = obj;
        context = cont;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.list_item,null);
        TextView videoName = v.findViewById(R.id.text_video_name);
        //songTitle.setSelected(true);
        //songTitle.setTypeface(Typeface.DEFAULT);
        videoName.setText(objects.get(i).getName());

        return v;
    }

    public int getPosition(File f){
        return objects.indexOf(f);
    }

    public ArrayList<File> getList(){
        return objects;
    }
}
