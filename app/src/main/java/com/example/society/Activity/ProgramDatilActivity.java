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

import com.example.society.Bean.Programming;
import com.example.society.R;
import com.example.society.database.DBinformation;
import com.example.society.database.SQLiteHelper;

public class ProgramDatilActivity extends AppCompatActivity implements View.OnClickListener{

    String name;
    String proname;
    ImageView note_back;
    EditText content;
    ImageView delete;
    ImageView note_save;
    ImageView change;

    TextView pname,pcon;
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
        setContentView(R.layout.activity_program_datil);


        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        proname = intent.getStringExtra("proname");


        record = (LinearLayout)findViewById(R.id.record);
        note_back = (ImageView) findViewById(R.id.note_back);
        delete = (ImageView) findViewById(R.id.delete);
        note_save = (ImageView) findViewById(R.id.note_save);
        pname = findViewById(R.id.pronameda);
        pcon = findViewById(R.id.pcons);
        content = findViewById(R.id.note_content);


        note_back.setOnClickListener(this);
        delete.setOnClickListener(this);
        note_save.setOnClickListener(this);

        SQLiteHelper.Programming programming = new SQLiteHelper.Programming();
        Programming programming1 = programming.userquery(proname);
        pname.setText(proname);
        pcon.setText(programming1.getContent());



    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.note_back:
                Intent intent = new Intent(ProgramDatilActivity.this,ProgrammingActivity.class);
                intent.putExtra("username",name);
                startActivity(intent);
                break;
            case R.id.delete:
                content.setText("");
                break;
            case R.id.note_save:
                //添加数据
                String p1 = content.getText().toString();

                if(p1.length()>0)
                {
                  showToast("保存成功");
                  Intent intentx = new Intent(ProgramDatilActivity.this,ProgrammingActivity.class);
                  intentx.putExtra("username",name);
                   startActivity(intentx);

                }
                    else
                    {
                        showToast("未输入内容！请重新输入！！！");

                    }

                break;

        }
    }
    public void showToast(String message)
    {
        Toast.makeText(ProgramDatilActivity.this,message,Toast.LENGTH_LONG).show();
    }
}