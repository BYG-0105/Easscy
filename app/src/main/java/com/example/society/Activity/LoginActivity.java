package com.example.society.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.society.Bean.Loginuser;
import com.example.society.R;
import com.example.society.database.MD5Utils;
import com.example.society.database.SQLiteHelper;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{
    private EditText name;
    private EditText pwd;
    private Button btnlogin;
    private Button btnzc;
    private Button btnmm;
    private Button  finish;
    SQLiteHelper sQliteHelper;
    SQLiteHelper.User userSql = new SQLiteHelper.User();
    private CheckBox mima,auto;
    private MD5Utils md5Utils;
    String autname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        initview();


        Intent intent = getIntent();
        String flag = intent.getStringExtra("callogin");

        //临时存放密码SharedPreferences
        SharedPreferences sp = getSharedPreferences("userlogin",MODE_PRIVATE);
        String nameinfo = sp.getString("username","");
        boolean bmima = sp.getBoolean("usermima",false);
        boolean bauto = sp.getBoolean("userauto",false);
        /*getBoolean()参数:key检索，defValue：存在值就返回该值否则返回defValue，如果第一次使用getBoolean()通过key检索不到，直接返回 defValue。*/
        String pwdinfo = sp.getString("userpwd","");
        if(bmima)
        {
            mima.setChecked(true);
            if(!nameinfo.isEmpty() && !pwdinfo.isEmpty())
            {
                name.setText(nameinfo);
                pwd.setText(pwdinfo);
                autname = nameinfo;
            }
            else
            {
                showToast("记住密码失败，请重新输入！！！");
            }
        }
        if(bauto && flag == null)
        {
            auto.setChecked(true);
            if(!nameinfo.isEmpty() && !pwdinfo.isEmpty())
            {
                name.setText(nameinfo);
                pwd.setText(pwdinfo);
                autname = nameinfo;
                Intent intent1 = new Intent(LoginActivity.this, EassyActivity.class);
                intent1.putExtra("username", autname);
                startActivity(intent1);
            }
            else
            {
                showToast("自动登录失败，请重新输入！！！");
            }
        }



    }

    private void initview() {

        sQliteHelper = new SQLiteHelper(this);
        name = (EditText)findViewById(R.id.edit_name);
        pwd = (EditText) findViewById(R.id.edit_pwd);
        btnlogin = findViewById(R.id.btn_login);
        btnzc = (Button)findViewById(R.id.btn_zc);
        btnmm = (Button)findViewById(R.id.btn_mm);
        mima =(CheckBox)findViewById(R.id.c_mima);
        auto = (CheckBox)findViewById(R.id.c_auto);
        finish = findViewById(R.id.of);
        btnlogin.setOnClickListener(this);
        finish.setOnClickListener(this);
        btnzc.setOnClickListener(this);
        btnmm.setOnClickListener(this);
    }

    public void showToast(String message)
    {
        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String username = name.getText().toString();
                String userpwd = pwd.getText().toString();
                boolean bmima = mima.isChecked();
                boolean bauto = auto.isChecked();
                //showToast(md5Utils.md5Password(userpwd));
                Loginuser loginuser = userSql.userquery(username);
                //showToast(loginuser.toString());
                if (loginuser == null) {
                    showToast("该用户名不存在！！！请先注册！");
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if (loginuser != null && username.equals(loginuser.getName() ) && md5Utils.md5Password(userpwd).equals(loginuser.getPwd()))
                {
                    if (bmima) {
                        SharedPreferences.Editor editor = getSharedPreferences("userlogin", MODE_PRIVATE).edit();
                        editor.putString("username", username);
                        editor.putString("userpwd", userpwd);
                        editor.putBoolean("usermima", bmima);
                        editor.putBoolean("userauto", bauto);
                        editor.apply();
                    }
                    else
                    {
                        SharedPreferences.Editor editor = getSharedPreferences("userlogin", MODE_PRIVATE).edit();
                        editor.putString("username", "");
                        editor.putString("userpwd", "");
                        editor.putBoolean("usermima", false);
                        editor.putBoolean("userauto", false);
                        editor.apply();
                    }


                    showToast("登录成功");

                    Intent intent = new Intent(LoginActivity.this, EassyActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                } else {
                    showToast("账号或密码错误");
                }
                break;
            case R.id.btn_zc:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                startActivity(intent);
                break;
            case R.id.btn_mm:
                Intent intent1 = new Intent(LoginActivity.this, ChangePwdActivity.class);
                intent1.putExtra("username", autname);
                startActivity(intent1);
                break;
            case R.id.of:
                finish();
                break;

        }
    }
}