package com.example.society.Activity;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.society.R;
import com.example.society.database.DBinformation;
import com.example.society.database.SQLiteHelper;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView note_back;
    TextView note_time;
    EditText content;
    ImageView delete;
    ImageView note_save;
    SQLiteHelper mSQLiteHelper;
    TextView noteName;
    ImageView change;
    ImageView imgeassy,imgadd;
    String id;
    LinearLayout record;
    private int[] icons = {R.drawable.sun,R.drawable.blue,R.drawable.green,R.drawable.check,R.drawable.white,R.drawable.black};
    private int[] text = {20,25,30,35,40,45};
    int iconbackground = 1;
    int textSize = 0;
    SQLiteHelper.Eassy eassy = new SQLiteHelper.Eassy();
    String name;
    String picPath ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        change = (ImageView)findViewById(R.id.size);
        record = (LinearLayout)findViewById(R.id.record);
        note_back = (ImageView) findViewById(R.id.note_back);
        note_time = (TextView) findViewById(R.id.tv_time);
        content = (EditText) findViewById(R.id.note_content);
        delete = (ImageView) findViewById(R.id.delete);
        note_save = (ImageView) findViewById(R.id.note_save);
        imgeassy = (ImageView) findViewById(R.id.img_eassy);
        imgadd = (ImageView) findViewById(R.id.imgadd);
        noteName = (TextView) findViewById(R.id.note_name);
        change.setOnClickListener(this);
        note_back.setOnClickListener(this);
        delete.setOnClickListener(this);
        note_save.setOnClickListener(this);
        imgadd.setOnClickListener(this);

        initData();

    }
    protected void initData()
    {
        mSQLiteHelper = new SQLiteHelper(this);
        noteName.setText("????????????");
        Intent intent = getIntent();
        if(intent != null)
        {
            id = intent.getStringExtra("id");
            if(id !=  null)
            {
                noteName.setText("????????????");
                content.setText(intent.getStringExtra("content"));
                note_time.setText(intent.getStringExtra("time"));
                note_time.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.note_back:
                 Intent intent = new Intent(RecordActivity.this,EassyActivity.class);
                intent.putExtra("username",name);
                 startActivity(intent);
                break;
            case R.id.imgadd:
                Intent intentimg = new Intent();
                intentimg.setType("image/*");
                intentimg.setAction(Intent.ACTION_PICK);
                startActivityForResult(intentimg, 1);
                break;
            case R.id.delete:
                content.setText("");
                break;
            case R.id.note_save:
                String noteContent = content.getText().toString().trim();
                if(id != null)//????????????
                {
                    if(noteContent.length()>0)
                    {
                        if(eassy.updateData(id,name,picPath,noteContent, DBinformation.getTime()))
                        {

                            showToast("????????????");
                            Intent intentx = new Intent(RecordActivity.this,EassyActivity.class);
                            intentx.putExtra("username",name);
                            startActivity(intentx);

                        }
                        else
                        {
                            showToast("????????????");
                        }
                    }
                    else
                    {
                        showToast("???????????????????????????");
                    }
                }
                else //????????????
                {
                    if(noteContent.length()>0)
                    {
                        if(eassy.insertData(name,picPath,noteContent,DBinformation.getTime()))
                        {

                            showToast("????????????");
                            Intent intentx = new Intent(RecordActivity.this,EassyActivity.class);
                            intentx.putExtra("username",name);
                            startActivity(intentx);

                        }
                        else
                        {
                            showToast("????????????");
                        }
                    }
                    else
                    {
                        showToast("????????????????????????");
                    }
                }
                break;
            case R.id.size:
                AlertDialog dialog;
                AlertDialog.Builder builder=new AlertDialog.Builder(this)
                        .setTitle("??????")
                        .setIcon(R.drawable.sz)
                        .setPositiveButton("??????????????????", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AlertDialog dialogb;
                                        AlertDialog.Builder builder=new AlertDialog.Builder(RecordActivity.this)
                                                .setTitle("??????????????????")
                                                .setIcon(R.drawable.xg)
                                                .setSingleChoiceItems(new String[]{"??????","??????","??????","??????","??????","??????"},
                                                        iconbackground, new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                iconbackground=which;
                                                            }
                                                        })
                                                .setPositiveButton("??????", new
                                                        DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                record.setBackgroundResource(icons[iconbackground]);
                                                                dialog.dismiss();
                                                            }
                                                        })
                                                .setNegativeButton("??????", new
                                                        DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                        dialogb=builder.create();
                                        dialogb.show();
                                    }
                                })
                        .setNegativeButton("??????????????????", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AlertDialog dialogb;
                                        AlertDialog.Builder builder=new AlertDialog.Builder(RecordActivity.this)
                                                .setTitle("??????????????????")
                                                .setIcon(R.drawable.xg)
                                                .setSingleChoiceItems(new String[]{"20???","25???","30???","35???","40???","45???"},
                                                        textSize, new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                textSize=which;
                                                            }
                                                        })
                                                .setPositiveButton("??????", new
                                                        DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                content.setTextSize(text[textSize]);
                                                                dialog.dismiss();
                                                            }
                                                        })
                                                .setNegativeButton("??????", new
                                                        DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                        dialogb=builder.create();
                                        dialogb.show();
                                    }
                                });
                dialog=builder.create();
                dialog.show();
        }
    }

    public void showToast(String message)
    {
        Toast.makeText(RecordActivity.this,message,Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK)
        {
            /**
             * ???????????????????????????????????????????????????????????????
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
                     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                     * ?????????????????????????????????????????????
                     */
                    if(path.endsWith("jpg")||path.endsWith("png"))
                    {
                        picPath = path;
                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        imgeassy.setImageBitmap(bitmap);
                    }else{alert();}
                }else{alert();}

            } catch (Exception e) {
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private void alert()
    {
        Dialog dialog = new android.app.AlertDialog.Builder(this)
                .setTitle("??????")
                .setMessage("?????????????????????????????????")
                .setPositiveButton("??????",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                picPath = null;
                            }
                        })
                .create();
        dialog.show();
    }
}