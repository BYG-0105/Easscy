package com.example.society.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteQuery;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.society.R;
import com.example.society.database.DBinformation;
import com.example.society.database.SQLiteHelper;

import java.io.File;

public class ZyActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    Button btncal,btnzy,btnsel;
    TextView tname;
    String name;
    String picPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sczy);

        Intent intent = getIntent();
        name = intent.getStringExtra("username");

        imageView = findViewById(R.id.img_zy);
        tname = findViewById(R.id.zyname);
        btncal = findViewById(R.id.btn_cal);
        btnzy = findViewById(R.id.bt_bc);
        btnsel = findViewById(R.id.btn_sel);

        btncal.setOnClickListener(this);
        btnzy.setOnClickListener(this);
        btnsel.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_cal:
                Intent intentq = new Intent(ZyActivity.this,OwnActivity.class);
                intentq.putExtra("username",name);
                startActivity(intentq);
                break;
            case R.id.btn_sel:
                Intent intents = new Intent();
                intents.setType("image/*");
                intents.setAction(Intent.ACTION_PICK);
                startActivityForResult(intents, 1);
                break;
            case R.id.bt_bc:
                SQLiteHelper.File file = new SQLiteHelper.File();
                if(file.insertData(tname.getText().toString(),name,picPath, DBinformation.getTime()))
                {

                    showToast("上传成功");
                    Intent intentx = new Intent(ZyActivity.this,FileMangeActivity.class);
                    intentx.putExtra("username",name);
                    startActivity(intentx);
                }
                else
                {
                    showToast("上传失败");
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
                        imageView.setImageBitmap(bitmap);
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
        Toast.makeText(ZyActivity.this,message,Toast.LENGTH_LONG).show();
    }



}