package com.example.android_video_player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int ALL_PERMISSIONS_REQUEST_CODE = 1234;

    public static VideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        setContentView(R.layout.activity_main);

        requestPermissions();

        GridView gv = findViewById(R.id.video_list);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                callPlayer((File) videoAdapter.getItem((int)l));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MainActivity.ALL_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    displayContent();
                } else {
                    requestPermissions();
                }
                return;
        }
    }

    public void requestPermissions(){
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED)
        {
            displayContent();
        }
        else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    MainActivity.ALL_PERMISSIONS_REQUEST_CODE);
        }
    }

    public ArrayList<File> searchVideo(){

        ArrayList<File> list = new ArrayList<>();

        ContentResolver cr = getContentResolver();

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Video.Media.TITLE + "!= 0";

        Cursor cur = cr.query(uri, null, selection, null,
                MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        int count = 0;

        if(cur != null)
        {
            count = cur.getCount();
            if(count > 0)
            {

                while(cur.moveToNext())
                {
                    int index = cur.getColumnIndex(MediaStore.Audio.Media.DATA);
                    File f = new File(cur.getString(index));
                    //Log.e("", f.getAbsolutePath());
                    list.add(f);
                }
            }
            cur.close();
        }

        return list;
    }

    private void displayContent(){
        GridView gv = findViewById(R.id.video_list);
        ArrayList<File> videoFiles = searchVideo();
        MainActivity.videoAdapter = new VideoAdapter(this, videoFiles);
        gv.setAdapter(MainActivity.videoAdapter);
    }

    private void callPlayer(File f){
        startActivity(new Intent(MainActivity.this,VideoPlayerActivity.class)
                .putExtra("videoFullPath",f.getAbsolutePath())
                .putExtra("videoFileName",f.getName()));
    }

}