package com.example.society.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.society.Bean.Loginuser;
import com.example.society.R;
import com.example.society.database.SQLiteHelper;

import java.io.File;

public class FileGrazeActivity extends AppCompatActivity {
    TextView author,zyname,time;
    ImageView cal,authorimg,zyimg;
    String name;
    String timeintent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_graze);
        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        timeintent = intent.getStringExtra("time");

        author = findViewById(R.id.author_name);
        zyname = findViewById(R.id.t_zyname);
        time = findViewById(R.id.zytimes);
        cal = findViewById(R.id.zyscal);
        authorimg = findViewById(R.id.author_img);
        zyimg = findViewById(R.id.zysimg);

        SQLiteHelper.User user = new SQLiteHelper.User();
        Loginuser loginuser = user.userquery(name);
        author.setText(loginuser.getName());
        if(loginuser.getImgpath() != null)
        {
            Bitmap bitmap = getImgFromDesc(loginuser.getImgpath());
            authorimg.setImageBitmap(bitmap);
        }
       else
        {
            authorimg.setImageResource(R.drawable.bb);
        }

        time.setText(timeintent);
        SQLiteHelper.File file = new SQLiteHelper.File();
        com.example.society.Bean.File file1 = file.userquery(timeintent);
        zyname.setText(file1.getName());
        if(file1.getPath() != null)
        {
            Bitmap bitmap1 = getImgFromDesc(file1.getPath());
            zyimg.setImageBitmap(bitmap1);
        }
        else
        {
            zyimg.setImageResource(R.drawable.filower);
        }

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentq = new Intent(FileGrazeActivity.this,FileMangeActivity.class);
                intentq.putExtra("username",name);
                startActivity(intentq);
            }
        });
    }


    //根据路径获取图片
    private Bitmap getImgFromDesc(String path) {
        Bitmap bm = null;
        File file = new File(path);
        // 动态申请权限
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        final int REQUEST_CODE = 10001;

        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取

            for (String permission : permissions) {
                //  GRANTED---授权  DINIED---拒绝
                if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
                }
            }
        }

        boolean permission_readStorage = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        boolean permission_camera = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
        Log.d("ImgActivity:", "getImageFromDesc: \n");
        Log.d("ImgActivity: ", "readPermission: " + permission_readStorage + "\n");
        Log.d("ImgActivity： ", "cameraPermission: " + permission_camera + "\n");

        if(file.exists()) {
            bm = BitmapFactory.decodeFile(path);
        } else {

            Toast.makeText(FileGrazeActivity.this,"该图片不存在！",Toast.LENGTH_LONG).show();
            Log.d("ImgActivity ", "getImgFromDesc: 该图片不存在！");
        }
        return bm;
    }
}