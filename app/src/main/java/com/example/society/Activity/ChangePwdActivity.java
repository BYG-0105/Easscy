package com.example.society.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.society.Bean.Loginuser;
import com.example.society.R;
import com.example.society.database.MD5Utils;
import com.example.society.database.SQLiteHelper;

import java.io.File;

public class ChangePwdActivity extends AppCompatActivity {

    private EditText name,pwd,num;
    private Button btnxg,btnfinish;
    private SQLiteHelper sqLiteHelper;
    SQLiteHelper.User userSql = new SQLiteHelper.User();
    private ImageView back,authorimg;
    String namess;
    private MD5Utils md5Utils ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        sqLiteHelper = new SQLiteHelper(this);
        name = (EditText) findViewById(R.id.e_namesc);
        pwd = (EditText) findViewById(R.id.e_pwdsc);
        btnxg = (Button)findViewById(R.id.btn_c);
        btnfinish = (Button) findViewById(R.id.btn_f);
        back = (ImageView)findViewById(R.id.back);
        authorimg = (ImageView)findViewById(R.id.img_aut);
        num = (EditText)findViewById(R.id.e_lxfs);

        Intent intent = getIntent();
        namess = intent.getStringExtra("username");

        if(userSql.userquery(namess) != null)
        {
            Loginuser loginuser = userSql.userquery(namess);
            String path = loginuser.getImgpath();
            if(path != null)
            {
                Bitmap bitmap = getImgFromDesc(path);
                authorimg.setImageBitmap(bitmap);
            }
            else
            {
                authorimg.setImageResource(R.drawable.bb);
            }

        }

        btnxg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String names = name.getText().toString();
                String pwds = md5Utils.md5Password(pwd.getText().toString());
                String nums = num.getText().toString();
               if(names.length() <= 0 )
               {
                   showToast("账号为空！！！请重新输入");
               }
               if(pwds.length() <= 0 )
               {
                   showToast("密码为空！！！请重新输入");
               }
               if(nums.isEmpty())
               {
                   showToast("联系方式为空！！！请重新输入");
               }
               else if(userSql.userquery(names) == null)
               {
                   showToast("账号不存在！！！检查账号是否正确！");
               }
               else if(userSql.userquery(names) != null)
               {
                   Loginuser loginuser = userSql.userquery(names);
                   String id = loginuser.getId();
                   if(userSql.updateData(id,names,pwds) && loginuser.getNum().equals(nums))
                   {
                       showToast("密码修改成功！！！");
                       if(namess == null)
                       {
                           Intent intent = new Intent(ChangePwdActivity.this,LoginActivity.class);
                           finish();
                           startActivity(intent);
                       }
                       else
                       {
                           Intent intent = new Intent(ChangePwdActivity.this,OwnActivity.class);
                           intent.putExtra("username",names);
                           finish();
                           startActivity(intent);
                       }
                   }
                   else if(loginuser.getNum() !=  nums)
                   {
                      showToast("输入联系方式错误！！！请重新输入！！！");
                   }
               }
            }
        });

        btnfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(namess == null)
                {
                    Intent intent = new Intent(ChangePwdActivity.this,LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(ChangePwdActivity.this,OwnActivity.class);
                    finish();
                    intent.putExtra("username",namess);
                    startActivity(intent);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(namess == null)
                {
                    Intent intent = new Intent(ChangePwdActivity.this,LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(ChangePwdActivity.this,OwnActivity.class);
                    finish();
                    intent.putExtra("username",namess);
                    startActivity(intent);
                }
            }
        });
    }
    public void showToast(String message)
    {
        Toast.makeText(ChangePwdActivity.this,message,Toast.LENGTH_LONG).show();
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

            Toast.makeText(ChangePwdActivity.this,"该图片不存在！",Toast.LENGTH_LONG).show();
            Log.d("ImgActivity ", "getImgFromDesc: 该图片不存在！");
        }
        return bm;
    }
}