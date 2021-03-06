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

import com.example.society.Bean.Eassy;
import com.example.society.Bean.Forum;
import com.example.society.R;
import com.example.society.adapter.ForumAdapter;
import com.example.society.database.SQLiteHelper;

import java.util.List;

public class ForumActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    List<Forum> list;
    SQLiteHelper sQliteHelper;
    SQLiteHelper.Forum forum = new SQLiteHelper.Forum();
    ForumAdapter adapter;
    private TextView oName;
    ImageView back;
    String name;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);



        oName = findViewById(R.id.order_name);
        Intent intent = getIntent();
        name = intent.getStringExtra("username");

        listView = (ListView) findViewById(R.id.order_listview);

        showQueryData();


        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.im_q).setOnClickListener(this);
        findViewById(R.id.im_f).setOnClickListener(this);
        findViewById(R.id.im_o).setOnClickListener(this);
        findViewById(R.id.im_b).setOnClickListener(this);
        initData();

        ImageView add = (ImageView) findViewById(R.id.cea);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForumActivity.this,CreateActivity.class);
                intent.putExtra("username",name);
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
                Forum forum1 = list.get(position);
                Intent intent = new Intent(ForumActivity.this, ForumDatilActivity.class);
                intent.putExtra("forumname",forum1.getForumname());
                intent.putExtra("username",name);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int position, long id) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(ForumActivity.this)
                        .setMessage("????????????????????????")
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Forum forum1 = list.get(position);
                                if(forum.deleteData(forum1.getId()))
                                {
                                    list.remove(position);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(ForumActivity.this,"????????????",Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("??????", new DialogInterface.OnClickListener() {
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
        list = forum.query();
        adapter = new ForumAdapter(this,list);
        listView.setAdapter( adapter);

    }
    public void showToast(String message)
    {
        Toast.makeText(ForumActivity.this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                Intent intent = new Intent(ForumActivity.this,LoginActivity.class);
                intent.putExtra("username",name);
                startActivity(intent);

                break;
            case R.id.im_f:
                Intent intentf = new Intent(ForumActivity.this,EassyActivity.class);
                intentf.putExtra("username",name);
                startActivity(intentf);
                break;
            case R.id.im_q:
                Intent intentq = new Intent(ForumActivity.this,ForumActivity.class);
                intentq.putExtra("username",name);
                startActivity(intentq);
                break;
            case R.id.im_b:
                Intent intentb = new Intent(ForumActivity.this,EaxmStyleActivity.class);
                intentb.putExtra("username",name);
                startActivity(intentb);

                break;
            case R.id.im_o:
                Intent intento = new Intent(ForumActivity.this,OwnActivity.class);
                intento.putExtra("username",name);
                startActivity(intento);
                break;


        }
    }
}