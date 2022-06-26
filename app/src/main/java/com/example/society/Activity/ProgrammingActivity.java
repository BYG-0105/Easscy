package com.example.society.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.society.Bean.Eassy;
import com.example.society.Bean.Programming;
import com.example.society.R;
import com.example.society.adapter.EassyAdapter;
import com.example.society.adapter.ProgramAdapter;
import com.example.society.database.SQLiteHelper;

import java.util.List;

public class ProgrammingActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listView;
    List<Programming> list;
    SQLiteHelper sQliteHelper;
    SQLiteHelper.Programming programmingsql = new SQLiteHelper.Programming();
    ProgramAdapter adapter;
    String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programming);

        Intent intent = getIntent();
        name = intent.getStringExtra("username");


        listView = (ListView) findViewById(R.id.program_listview);
        showQueryData();



        findViewById(R.id.programback).setOnClickListener(this);
        findViewById(R.id.proadd).setOnClickListener(this);

        initData();


    }

    protected void initData()
    {
        sQliteHelper = new SQLiteHelper(this);
        showQueryData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Programming programming = list.get(position);
                Intent intent = new Intent(ProgrammingActivity.this, ProgramDatilActivity.class);
                intent.putExtra("proname",programming.getProname());
                intent.putExtra("username",name);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int position, long id) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(ProgrammingActivity.this)
                        .setMessage("是否删除此事件？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Programming programming = list.get(position);
                                if(name.equals(programming.getAuthorpro()))
                                {
                                    if(programmingsql.deleteData(programming.getId()))
                                    {
                                        list.remove(position);
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(ProgrammingActivity.this,"删除成功",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else
                                    {
                                    Toast.makeText(ProgrammingActivity.this,"删除失败！！！无删除权限！！！",Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }
    private void showQueryData()
    {
        if(list != null)
        {
            list.clear();

        }
        list = programmingsql.query();
        adapter = new ProgramAdapter(this,list);
        listView.setAdapter(adapter);

    }
    public void showToast(String message)
    {
        Toast.makeText(ProgrammingActivity.this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.programback:
                Intent intent = new Intent(ProgrammingActivity.this,EassyActivity.class);
                intent.putExtra("username",name);
                startActivity(intent);
                finish();
                break;
            case R.id.proadd:
                Intent intentq = new Intent(ProgrammingActivity.this,ProgramaddActivity.class);
                intentq.putExtra("username",name);
                startActivity(intentq);
                break;

        }
    }
}
