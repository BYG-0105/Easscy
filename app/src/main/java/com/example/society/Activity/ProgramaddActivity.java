package com.example.society.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.society.R;
import com.example.society.database.DBinformation;
import com.example.society.database.SQLiteHelper;

public class ProgramaddActivity extends AppCompatActivity implements View.OnClickListener{

    String name;
    ImageView note_back;
    EditText content;
    ImageView delete;
    ImageView note_save;
    ImageView change;

    TextView proname,procontent;
    LinearLayout record;
    private int[] icons = {R.drawable.sun,R.drawable.blue,R.drawable.green,R.drawable.check,R.drawable.white,R.drawable.black};
    private int[] text = {20,25,30,35,40,45};
    int iconbackground = 1;
    int textSize = 0;
    SQLiteHelper.Programming programming = new SQLiteHelper.Programming();
    String picPath ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programadd);


        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        change = (ImageView)findViewById(R.id.size);
        record = (LinearLayout)findViewById(R.id.record);
        note_back = (ImageView) findViewById(R.id.note_back);
        delete = (ImageView) findViewById(R.id.delete);
        note_save = (ImageView) findViewById(R.id.note_save);
        proname = findViewById(R.id.edproname);
        procontent = findViewById(R.id.npro_content);

        change.setOnClickListener(this);
        note_back.setOnClickListener(this);
        delete.setOnClickListener(this);
        note_save.setOnClickListener(this);




    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.note_back:
                Intent intent = new Intent(ProgramaddActivity.this,ProgrammingActivity.class);
                intent.putExtra("username",name);
                startActivity(intent);
                break;
            case R.id.delete:
                content.setText("");
                break;
            case R.id.note_save:
                //添加数据
                String pname = proname.getText().toString();
                String pcon = procontent.getText().toString();
                if(pname.length()>0 && pcon.length()>0)
                    {
                        if(programming.userquery(pname) == null)
                        {
                            if(programming.insertData(pname,name,pcon,DBinformation.getTime()))
                            {

                                showToast("保存成功");
                                Intent intentx = new Intent(ProgramaddActivity.this,ProgrammingActivity.class);
                                intentx.putExtra("username",name);
                                startActivity(intentx);

                            }
                            else
                            {
                                showToast("保存失败");
                            }
                        }
                        else
                        {
                            showToast("题目名称已被使用！请重新输入！！！");
                            proname.setText("");
                        }

                    }
                    else if(pname == null)
                    {
                        showToast("题目名称不能为空");
                    }
                    else if(pcon == null)
                    {
                        showToast("题目内容不能为空");
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
                                        AlertDialog.Builder builder=new AlertDialog.Builder(ProgramaddActivity.this)
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
                                        AlertDialog.Builder builder=new AlertDialog.Builder(ProgramaddActivity.this)
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
        Toast.makeText(ProgramaddActivity.this,message,Toast.LENGTH_LONG).show();
    }
}