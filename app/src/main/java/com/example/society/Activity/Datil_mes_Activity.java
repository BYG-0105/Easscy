package com.example.society.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.society.Bean.Eassy;
import com.example.society.Bean.Loginuser;
import com.example.society.R;
import com.example.society.database.SQLiteHelper;

import java.io.File;

public class Datil_mes_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eassy_mes);

        Intent intent = getIntent();
        String name = intent.getStringExtra("username");
        String content = intent.getStringExtra("content");
        String time  = intent.getStringExtra("time");

        TextView tvc = findViewById(R.id.t_con);
        TextView tvt = findViewById(R.id.t_time);
        tvc.setText(content);
        tvt.setText(time);

        TextView author = findViewById(R.id.author_name);
        ImageView img = findViewById(R.id.author_img);
        ImageView imgeassy = findViewById(R.id.img_eassys);

        SQLiteHelper.Eassy eassy = new SQLiteHelper.Eassy();
        if(eassy.userquery(time) != null)
        {
            Eassy eassy1 = eassy.userquery(time);
            author.setText(eassy1.getAuthor());
            SQLiteHelper.User user = new SQLiteHelper.User();
            Loginuser loginuser = user.userquery(eassy1.getAuthor());
            if(loginuser.getImgpath() != null)
            {
                Bitmap bitmap = getImgFromDesc(loginuser.getImgpath());
                img.setImageBitmap(bitmap);
            }
            else
            {
                img.setImageResource(R.drawable.bb);
            }
            Bitmap bitmaps = getImgFromDesc(eassy1.getImgpath());
            imgeassy.setImageBitmap(bitmaps);
        }

        ImageView cal = findViewById(R.id.cal);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Datil_mes_Activity.this,EassyActivity.class);
                intent1.putExtra("username",name);
                startActivity(intent1);
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

            Toast.makeText(Datil_mes_Activity.this,"该图片不存在！",Toast.LENGTH_LONG).show();
            Log.d("ImgActivity ", "getImgFromDesc: 该图片不存在！");
        }
        return bm;
    }
}