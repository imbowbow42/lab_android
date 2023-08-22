package com.example.exe72;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;

    private RecyclerView rcvVideo;
    private Button btn_get_video;
    private VideoAdapter videoAdapter;
    private List<Video> mListVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvVideo = findViewById(R.id.rcv_video);
        btn_get_video = findViewById(R.id.btn_get_video);
        videoAdapter = new VideoAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvVideo.setLayoutManager(gridLayoutManager);

        btn_get_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkPermission();
            }
        });


    }

    private void checkPermission(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        EXTERNAL_STORAGE_PERMISSION_CODE);
                getAllVideoFromGallery();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };


        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION)
                .check();

    }


    private void getAllVideoFromGallery() {
        mListVideo = new ArrayList<>();

        String absolutePathOfImage = null;
        String thumbnail = null;

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI.normalizeScheme();

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media._ID, MediaStore.Video.Thumbnails.DATA};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        Cursor cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        int columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int thumb= cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);

        while (cursor.moveToNext()){
            absolutePathOfImage = cursor.getString(columnIndexData);
            thumbnail=cursor.getString(thumb);

            Video video = new Video();
            video.setPath(absolutePathOfImage);
            video.setThumb(thumbnail);

            mListVideo.add(video);
            Log.d("path", absolutePathOfImage);

        }
        videoAdapter.setData(mListVideo);
        rcvVideo.setAdapter(videoAdapter);

    }
}