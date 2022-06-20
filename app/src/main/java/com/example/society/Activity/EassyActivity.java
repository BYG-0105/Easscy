package com.example.society.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.society.Bean.OrderBean;
import com.example.society.R;
import com.example.society.adapter.EassyAdapter;
import com.example.society.database.SQLiteHelper;

import java.util.List;

public class EassyActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    List<OrderBean> list;
    SQLiteHelper sQliteHelper;
    SQLiteHelper.Eassy order = new SQLiteHelper.Eassy();
    EassyAdapter adapter;
    private TextView oName;
    ImageView back;
    String name;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easssy);



        oName = findViewById(R.id.order_name);
        Intent intent = getIntent();
        name = intent.getStringExtra("username");


        listView = (ListView) findViewById(R.id.order_listview);




        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.im_q).setOnClickListener(this);
        findViewById(R.id.im_f).setOnClickListener(this);
        findViewById(R.id.im_o).setOnClickListener(this);
        findViewById(R.id.im_b).setOnClickListener(this);
        initData();

        ImageView add = (ImageView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EassyActivity.this,RecordActivity.class);
                startActivityForResult(intent,1);
            }
        });

    }
    protected void initData()
    {
        sQliteHelper = new SQLiteHelper(this);
        showQueryData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                OrderBean orderBean = list.get(position);
                Intent intent = new Intent(EassyActivity.this, Datil_mes_Activity.class);
                intent.putExtra("content",orderBean.getContent());
                intent.putExtra("time",orderBean.getTime());
                intent.putExtra("username",name);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int position, long id) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(EassyActivity.this)
                        .setMessage("是否删除此事件？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                OrderBean orderBean = list.get(position);
                                if(order.deleteData(orderBean.getId()))
                                {
                                    list.remove(position);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(EassyActivity.this,"删除成功",Toast.LENGTH_LONG).show();
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
        list = order.query();
        adapter = new EassyAdapter(this,list);
        listView.setAdapter(adapter);

    }
    public void showToast(String message)
    {
        Toast.makeText(EassyActivity.this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                Intent intent = new Intent(EassyActivity.this,LoginActivity.class);
                intent.putExtra("username",name);
                startActivity(intent);
                finish();
                break;
            case R.id.im_q:
                Intent intentq = new Intent(EassyActivity.this,ForumActivity.class);
                intentq.putExtra("username",name);
                startActivity(intentq);
                break;
            case R.id.im_b:
                Intent intentb = new Intent(EassyActivity.this,ExercisesDetailActivity.class);
                intentb.putExtra("username",name);
                startActivity(intentb);

                break;
            case R.id.im_o:
                Intent intento = new Intent(EassyActivity.this,OwnActivity.class);
                intento.putExtra("username",name);
                startActivity(intento);
                break;


        }
    }
}