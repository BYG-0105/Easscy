package com.example.society.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.society.Bean.Loginuser;
import com.example.society.R;
import com.example.society.database.MD5Utils;
import com.example.society.database.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_register;
    private Button btn_cancal;
    private EditText username;
    private EditText userpwd;
    private SQLiteHelper sqLiteHelper;
    private SQLiteHelper.User UsersqLiteHelper = new SQLiteHelper.User();
    private List<String> styleString=new ArrayList<String>();
    private CheckBox stylehy,styleyg,styleom,stylegd;
    private RadioGroup radioGroup;
    String uGender  = "男";
    private TextView city;
    private TextView age;
    private TextView num;
    private ImageView imgAuthor;
    private MD5Utils md5Utils;
    private String picPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sqLiteHelper = new SQLiteHelper(this);

        initview();
        Intent intent = getIntent();
        String name = intent.getStringExtra("username");
        if(name != null)
        {
            initmes(name);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId())
                {
                    case R.id.radioButton_Male:
                        uGender="男";
                        break;
                    case R.id.radioButton_Female:
                        uGender="女";
                        break;
                    default:break;
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();
                String pwd = md5Utils.md5Password(userpwd.getText().toString());
                String citys = city.getText().toString();
                String ages = age.getText().toString();
                String nums = num.getText().toString();
                checkBoxlis();
                if(btn_register.getText().equals("注册"))
                {

                    if(UsersqLiteHelper.userquery(name) != null)
                    {
                        showToast("该账户已存在！！！");

                    }
                    else if(name.length() <= 0 || pwd.length() <= 0 || nums.length() <= 0)
                    {
                        showToast("账号，密码，电话号码为必填项，请重新填写！！！");
                    }
                    else if(UsersqLiteHelper.userquery(name) == null && UsersqLiteHelper.insertData(name,pwd,citys,ages,uGender,styleString.toString(),nums,picPath))
                    {
                        showToast("注册成功！");
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        showToast("注册失败！！！请重新尝试注册");
                    }
                }
                if(btn_register.getText().equals("修改"))
                {
                    Loginuser loginuser = UsersqLiteHelper.userquery(name);
                    String id = loginuser.getId();
                    if(UsersqLiteHelper.updatemesData(id,name,pwd,citys,ages,uGender,styleString.toString(),nums,picPath))
                    {
                        showToast("修改成功！");

                    }
                    else
                    {
                        showToast("修改失败！！！请重新尝试注册");
                    }
                    Intent intent = new Intent(RegisterActivity.this,OwnActivity.class);
                    intent.putExtra("username",name);
                    startActivity(intent);
                    finish();
                }


            }
        });

        btn_cancal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_register.getText().equals("注册"))
                {

                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                if(btn_register.getText().equals("修改"))
                {
                    Intent intent = new Intent(RegisterActivity.this,OwnActivity.class);
                    intent.putExtra("username",name);
                    startActivity(intent);
                    finish();
                }

            }
        });

        imgAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK)
        {
            /**
             * 当选择的图片不为空的话，在获取到图片的途径
             */
            Uri uri = data.getData();
            try {
                String[] pojo = {MediaStore.Images.Media.DATA};

                Cursor cursor = managedQuery(uri, pojo, null, null,null);
                if(cursor!=null)
                {
                    ContentResolver cr = this.getContentResolver();
                    int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                    cursor.moveToFirst();
                    String path = cursor.getString(colunm_index);
                    /***
                     * 这里加这样一个推断主要是为了第三方的软件选择，比方：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，这种话，我们推断文件的后缀名
                     * 假设是图片格式的话，那么才干够
                     */
                    if(path.endsWith("jpg")||path.endsWith("png"))
                    {
                        picPath = path;
                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        imgAuthor.setImageBitmap(bitmap);
                    }else{alert();}
                }else{alert();}

            } catch (Exception e) {
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void alert()
    {
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("您选择的不是有效的图片")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                picPath = null;
                            }
                        })
                .create();
        dialog.show();
    }

    private void initmes(String name) {
        Loginuser loginuser = UsersqLiteHelper.userquery(name);
        username.setText(loginuser.getName());
        userpwd.setText(loginuser.getPwd());
        city.setText(loginuser.getCity());
        age.setText(loginuser.getAge());
        num.setText(loginuser.getNum());
        btn_register.setText("修改");
    }

    public void initview()
    {
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_cancal = (Button)findViewById(R.id.btn_cancal) ;
        username = (EditText) findViewById(R.id.etPersonName);
        userpwd = (EditText) findViewById(R.id.etPwd);
        stylehy = (CheckBox)findViewById(R.id.checkBoxhy);
        styleyg = (CheckBox)findViewById(R.id.checkBoxyg);
        styleom = (CheckBox)findViewById(R.id.checkBoxom);
        stylegd = (CheckBox)findViewById(R.id.checkBoxgd);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        city = (TextView)findViewById(R.id.etCity);
        age = (TextView) findViewById(R.id.etAge);
        num = (TextView) findViewById(R.id.etNum);
        imgAuthor = (ImageView) findViewById(R.id.img_author);

    }
    private void checkBoxlis() {
        if(stylehy.isChecked())
        {
            styleString.add(stylehy.getText().toString());
        }
        else {
            styleString.remove(stylehy.getText().toString());
        }
        if(styleyg.isChecked())
        {
            styleString.add(styleyg.getText().toString());
        }
        else {
            styleString.remove(styleyg.getText().toString());
        }
        if (styleom.isChecked())
        {
            styleString.add(styleom.getText().toString());
        }
        else {
            styleString.remove(styleom.getText().toString());
        }
        if (stylegd.isChecked())
        {
            styleString.add(stylegd.getText().toString());
        }
        else {
            styleString.remove(stylegd.getText().toString());
        }
    }



    public void showToast(String message)
    {
        Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
    }
}