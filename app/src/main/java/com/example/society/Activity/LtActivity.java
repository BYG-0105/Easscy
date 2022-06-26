package com.example.society.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.society.Bean.Loginuser;
import com.example.society.R;
import com.example.society.database.DBinformation;
import com.example.society.database.SQLiteHelper;

public class LtActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ltimg,ltcal;
    TextView tname,tcon;
    Button button;
    String name;
    String picPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lt);

        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        ltcal = findViewById(R.id.lt_cal);
        ltimg = findViewById(R.id.lt_img);
        tname = findViewById(R.id.lt_name);
        tcon = findViewById(R.id.lt_con);
        button = findViewById(R.id.lt_btn);
        button.setOnClickListener(this);
        ltcal.setOnClickListener(this);
        ltimg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lt_cal:
                Intent intento = new Intent(LtActivity.this,ForumActivity.class);
                intento.putExtra("username",name);
                startActivity(intento);
                break;
            case R.id.lt_img:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, 1);
                break;
            case R.id.lt_btn:
                String namefor = tname.getText().toString();
                String con = tcon.getText().toString();
                SQLiteHelper.Forum forum = new SQLiteHelper.Forum();
                SQLiteHelper.Comment comment = new SQLiteHelper.Comment();
                if(forum.userquery(namefor) == null)
                {
                    if(forum.insertData(name,namefor,con,picPath, DBinformation.getTime()))
                    {

                        showToast("创建成功");
                        Intent intentx = new Intent(LtActivity.this,ForumActivity.class);
                        intentx.putExtra("username",name);
                        startActivity(intentx);
                    }
                    else
                    {
                        showToast("创建失败");
                    }
                }
                else
                {
                    showToast("论坛名称已存在，请重新输入论坛名称！！！");
                    tname.setText("");
                }


                break;

        }
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
                        ltimg.setImageBitmap(bitmap);
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
    public void showToast(String message)
    {
        Toast.makeText(LtActivity.this,message,Toast.LENGTH_LONG).show();
    }

}