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
import com.example.society.Bean.File;
import com.example.society.R;
import com.example.society.adapter.EassyAdapter;
import com.example.society.adapter.FileAdapter;
import com.example.society.database.SQLiteHelper;

import java.util.List;

public class FileMangeActivity extends AppCompatActivity implements View.OnClickListener{
    ListView listView;
    List<File> list;
    SQLiteHelper sQliteHelper;
    SQLiteHelper.File file = new SQLiteHelper.File();
    FileAdapter adapter;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_mange);

        Intent intent = getIntent();
        name = intent.getStringExtra("username");


        listView = (ListView) findViewById(R.id.fmange_listview);
        showQueryData();



        findViewById(R.id.fmangeback).setOnClickListener(this);
        findViewById(R.id.fmangeadd).setOnClickListener(this);

        initData();



    }
    protected void initData()
    {
        sQliteHelper = new SQLiteHelper(this);
        showQueryData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                File file1 = list.get(position);
                Intent intent = new Intent(FileMangeActivity.this, FileGrazeActivity.class);
                intent.putExtra("username",file1.getName());
                intent.putExtra("time",file1.getTime());
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int position, long id) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(FileMangeActivity.this)
                        .setMessage("是否删除此事件？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                File file1 = list.get(position);
                                if(file.deleteData(file1.getId()))
                                {
                                    list.remove(position);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(FileMangeActivity.this,"删除成功",Toast.LENGTH_LONG).show();
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
        list = file.query();
        adapter = new FileAdapter(this,list);
        listView.setAdapter(adapter);

    }
    public void showToast(String message)
    {
        Toast.makeText(FileMangeActivity.this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fmangeback:
                Intent intent = new Intent(FileMangeActivity.this,OwnActivity.class);
                intent.putExtra("username",name);
                startActivity(intent);
                finish();
                break;
            case R.id.fmangeadd:
                Intent intentq = new Intent(FileMangeActivity.this,ZyActivity.class);
                intentq.putExtra("username",name);
                startActivity(intentq);
                break;


        }
    }
}