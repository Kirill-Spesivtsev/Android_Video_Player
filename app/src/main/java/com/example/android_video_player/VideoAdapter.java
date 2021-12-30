package com.example.android_video_player;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
        ImageView thumbnailImage = v.findViewById(R.id.img_grid_cell_thumbnail);

        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(objects.get(i).getAbsolutePath(),
                MediaStore.Images.Thumbnails.MINI_KIND);

        thumbnailImage.setImageBitmap(thumb);

        videoName.setSelected(true);
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
