package com.example.society.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
    String id;
    LinearLayout record;
    private int[] icons = {R.drawable.sun,R.drawable.blue,R.drawable.green,R.drawable.check,R.drawable.white,R.drawable.black};
    private int[] text = {20,25,30,35,40,45};
    int iconbackground = 1;
    int textSize = 0;
    SQLiteHelper.Eassy eassy = new SQLiteHelper.Eassy();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);


        change = (ImageView)findViewById(R.id.size);
        record = (LinearLayout)findViewById(R.id.record);
        note_back = (ImageView) findViewById(R.id.note_back);
        note_time = (TextView) findViewById(R.id.tv_time);
        content = (EditText) findViewById(R.id.note_content);
        delete = (ImageView) findViewById(R.id.delete);
        note_save = (ImageView) findViewById(R.id.note_save);
        noteName = (TextView) findViewById(R.id.note_name);
        change.setOnClickListener(this);
        note_back.setOnClickListener(this);
        delete.setOnClickListener(this);
        note_save.setOnClickListener(this);
        initData();

    }
    protected void initData()
    {
        mSQLiteHelper = new SQLiteHelper(this);
        noteName.setText("添加记录");
        Intent intent = getIntent();
        if(intent != null)
        {
            id = intent.getStringExtra("id");
            if(id !=  null)
            {
                noteName.setText("修改记录");
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
                 startActivity(intent);
                break;
            case R.id.delete:
                content.setText("");
                break;
            case R.id.note_save:
                String noteContent = content.getText().toString().trim();
                if(id != null)//修改数据
                {
                    if(noteContent.length()>0)
                    {
                        if(eassy.updateData(id,noteContent, DBinformation.getTime()))
                        {
                            showToast("修改成功");
                            setResult(2);
                            finish();
                        }
                        else
                        {
                            showToast("修改失败");
                        }
                    }
                    else
                    {
                        showToast("修改内容不能为空！");
                    }
                }
                else //添加数据
                {
                    if(noteContent.length()>0)
                    {
                        if(eassy.insertData(noteContent,DBinformation.getTime()))
                        {
                            showToast("保存成功");
                            setResult(2);
                            finish();
                        }
                        else
                        {
                            showToast("保存失败");
                        }
                    }
                    else
                    {
                        showToast("修改内容不能为空");
                    }
                }
                break;
            case R.id.size:
                AlertDialog dialog;
                AlertDialog.Builder builder=new AlertDialog.Builder(this)
                        .setTitle("设置")
                        .setIcon(R.drawable.sz)
                        .setPositiveButton("修改背景风格", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AlertDialog dialogb;
                                        AlertDialog.Builder builder=new AlertDialog.Builder(RecordActivity.this)
                                                .setTitle("设置背景风格")
                                                .setIcon(R.drawable.xg)
                                                .setSingleChoiceItems(new String[]{"阳光","蓝天","户外","方格","花瓣","深夜"},
                                                        iconbackground, new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                iconbackground=which;
                                                            }
                                                        })
                                                .setPositiveButton("确定", new
                                                        DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                record.setBackgroundResource(icons[iconbackground]);
                                                                dialog.dismiss();
                                                            }
                                                        })
                                                .setNegativeButton("取消", new
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
                        .setNegativeButton("修改字体大小", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AlertDialog dialogb;
                                        AlertDialog.Builder builder=new AlertDialog.Builder(RecordActivity.this)
                                                .setTitle("设置字体大小")
                                                .setIcon(R.drawable.xg)
                                                .setSingleChoiceItems(new String[]{"20号","25号","30号","35号","40号","45号"},
                                                        textSize, new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                textSize=which;
                                                            }
                                                        })
                                                .setPositiveButton("确定", new
                                                        DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                content.setTextSize(text[textSize]);
                                                                dialog.dismiss();
                                                            }
                                                        })
                                                .setNegativeButton("取消", new
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
}